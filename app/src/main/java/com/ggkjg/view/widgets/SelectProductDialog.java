package com.ggkjg.view.widgets;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.donkingliang.labels.LabelsView;
import com.ggkjg.R;
import com.ggkjg.common.utils.ToastUtil;
import com.ggkjg.dto.CategoryDto;
import com.ggkjg.dto.CategoryListDto;
import com.ggkjg.dto.ColorListDto;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.functions.Action;

/**
 * 商品筛选
 * Created by dahai on 2019/01/25.
 */
public class SelectProductDialog extends Dialog {
    @BindView(R.id.et_min)
    ClearEditText etMin;
    @BindView(R.id.et_max)
    ClearEditText etMax;
    private Context mContext;
    @BindView(R.id.lv_select_product_price)
    LabelsView labelsViewPrice;
    @BindView(R.id.lv_select_product_calss)
    LabelsView labelsViewCalss;
    @BindView(R.id.lv_select_product_color)
    LabelsView labelsViewColor;

    @BindView(R.id.rl_select_product_all_calss)
    RelativeLayout rl_select_product_all_calss;
    @BindView(R.id.iv_select_product_calss)
    ImageView      iv_select_product_calss;
    @BindView(R.id.rl_select_product_all_color)
    RelativeLayout rl_select_product_all_color;
    @BindView(R.id.iv_select_product_color)
    ImageView      iv_select_product_color;


    @BindView(R.id.tv_select_product_reset)
    TextView tv_select_product_reset;
    @BindView(R.id.tv_select_product_submit)
    TextView tv_select_product_submit;
    private SelectProductListener selectProductListener;
    List<String> prices;
    List<String> calss;
    List<String> colors;
    private String min;
    private String max;
    private String selectType;
    private String selectColor;

    public SelectProductDialog(Context context, int categoryId, SelectProductListener listener) {
        super(context, R.style.dialog_with_alpha);
        //        setCanceledOnTouchOutside(false);//设置点击外部不可以取消;
        this.mContext = context;
        setContentView(R.layout.dialog_select_product);
        ButterKnife.bind(this);
        this.selectProductListener = listener;
        initView();
        initData(categoryId);
        initListener();
    }

    private void initView() {
        WindowManager manager = getWindow().getWindowManager();
        Display display = manager.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = (display.getWidth());
        p.height = (display.getHeight());
        Window window = getWindow();
        window.setAttributes(p);
        window.setGravity(Gravity.RIGHT);
        window.setWindowAnimations(R.style.rightAnimStyle);
    }

    private void initListener() {
        labelsViewPrice.setOnLabelClickListener(new LabelsView.OnLabelClickListener() {
            @Override
            public void onLabelClick(TextView label, Object data, int position) {
                //label是被点击的标签，data是标签所对应的数据，position是标签的位置。
                if(position==0){
                    min="0";
                    max="1000";
                    etMin.setText("0");
                    etMax.setText("1000");
                }else  if(position==1){
                    min="1000";
                    max="2000";
                    etMin.setText("1000");
                    etMax.setText("2000");
                }else  if(position==2){
                    min="2000";
                    max="5000";
                    etMin.setText("2000");
                    etMax.setText("5000");
                }
            }
        });
        labelsViewCalss.setOnLabelClickListener(new LabelsView.OnLabelClickListener() {
            @Override
            public void onLabelClick(TextView label, Object data, int position) {
                //label是被点击的标签，data是标签所对应的数据，position是标签的位置。
                selectType = calss.get(position);
            }
        });
        labelsViewColor.setOnLabelClickListener(new LabelsView.OnLabelClickListener() {
            @Override
            public void onLabelClick(TextView label, Object data, int position) {
                //label是被点击的标签，data是标签所对应的数据，position是标签的位置。
                selectColor = colors.get(position);
            }
        });

        bindClickEvent(rl_select_product_all_calss, () -> {
            if (iv_select_product_calss.getTag().equals("open")) {
                iv_select_product_calss.setTag("close");
                iv_select_product_calss.setImageResource(R.mipmap.arrow_up);
                labelsViewCalss.setVisibility(View.GONE);
            } else {
                iv_select_product_calss.setTag("open");
                iv_select_product_calss.setImageResource(R.mipmap.arrow_down);
                labelsViewCalss.setVisibility(View.VISIBLE);
            }
        });
        bindClickEvent(rl_select_product_all_color, () -> {
            if (iv_select_product_color.getTag().equals("open")) {
                iv_select_product_color.setTag("close");
                iv_select_product_color.setImageResource(R.mipmap.arrow_up);
                labelsViewColor.setVisibility(View.GONE);
            } else {
                iv_select_product_color.setTag("open");
                iv_select_product_color.setImageResource(R.mipmap.arrow_down);
                labelsViewColor.setVisibility(View.VISIBLE);
            }
        });

        bindClickEvent(tv_select_product_reset, () -> {
            labelsViewPrice.setSelectType(LabelsView.SelectType.NONE);
            labelsViewCalss.setSelectType(LabelsView.SelectType.NONE);
            labelsViewColor.setSelectType(LabelsView.SelectType.NONE);
            labelsViewPrice.setSelectType(LabelsView.SelectType.SINGLE);
            labelsViewCalss.setSelectType(LabelsView.SelectType.SINGLE);
            labelsViewColor.setSelectType(LabelsView.SelectType.SINGLE);
            min=null;
            max=null;
            etMax.setText("");
            etMin.setText("");
            selectType = null;
            selectColor = null;
        });

        bindClickEvent(tv_select_product_submit, () -> {
            if (null != selectProductListener) {
                if(Double.valueOf(etMax.getText().toString())>=Double.valueOf(etMin.getText().toString())){
                    selectProductListener.callbackSelectProduct(etMin.getText().toString(),etMax.getText().toString(), selectType, selectColor);
                    hide();
                }else {
                    ToastUtil.showToast("最大值不能小于最小值");
                    etMax.setText("");
                }
            }

        });
    }

    private void initData(int categoryId) {
        findCategoryAndColor(categoryId);
        labelsViewPrice.setSelectType(LabelsView.SelectType.SINGLE);
        prices = new ArrayList<>();

        prices.add("0-1000");
        prices.add("1000-2000");
        prices.add("2000-5000");
        labelsViewPrice.setLabels(prices, new LabelsView.LabelTextProvider<String>() {
            @Override
            public CharSequence getLabelText(TextView label, int position, String data) {
                return data;
            }
        });
    }

    /**
     * 获取二级分类/颜色 (商品筛选)
     *
     * @param categoryId 父级分类ID
     */
    private void findCategoryAndColor(int categoryId) {
        DataManager.getInstance().findCategoryAndColor(categoryId, new DefaultSingleObserver<CategoryDto>() {
            @Override
            public void onSuccess(CategoryDto data) {
                if (data.getCategoryList() != null && !data.getCategoryList().isEmpty()) {
                    labelsViewCalss.setSelectType(LabelsView.SelectType.SINGLE);
                    calss = new ArrayList<>();
                    for (CategoryListDto categoryListDto : data.getCategoryList()) {
                        calss.add(categoryListDto.getCategoryName());
                    }
                    labelsViewCalss.setLabels(calss, new LabelsView.LabelTextProvider<String>() {
                        @Override
                        public CharSequence getLabelText(TextView label, int position, String data) {
                            return data;
                        }
                    });
                }
                if (data.getColorList() != null && !data.getColorList().isEmpty()) {
                    labelsViewColor.setSelectType(LabelsView.SelectType.SINGLE);
                    colors = new ArrayList<>();
                    for (ColorListDto colorListDto : data.getColorList()) {
                        colors.add(colorListDto.getColorName());
                    }
                    labelsViewColor.setLabels(colors, new LabelsView.LabelTextProvider<String>() {
                        @Override
                        public CharSequence getLabelText(TextView label, int position, String data) {
                            return data;
                        }
                    });
                }
            }

            @Override
            public void onError(Throwable throwable) {
            }
        });
    }


    /**
     * 基本点击事件跳转默认节流1000毫秒
     *
     * @param view   绑定的view
     * @param action 跳转的Acticvity
     */
    protected void bindClickEvent(View view, final Action action) {
        bindClickEvent(view, action, 1000);
    }

    protected void bindClickEvent(View view, final Action action, long frequency) {
        Observable<Object> observable = RxView.clicks(view);
        if (frequency > 0) {
            observable.throttleFirst(frequency, TimeUnit.MILLISECONDS);
        }
        observable.subscribe(trigger -> action.run());
    }

    public interface SelectProductListener {
        void callbackSelectProduct(String min,String max, String type, String color);
    }


}
