package com.ggkjg.view.widgets;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ggkjg.R;
import com.ggkjg.base.BuildConfig;
import com.ggkjg.common.utils.GlideUtils;
import com.ggkjg.common.utils.LogUtil;
import com.ggkjg.db.bean.SearchHistory;
import com.ggkjg.dto.CommodityDetailInfoDto;
import com.ggkjg.dto.GoodsColorAndSpecDto;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.adapter.GoodsAttrAdapter;
import com.jakewharton.rxbinding2.view.RxView;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.functions.Action;

/**
 * 产品类型、规格
 * Created by dahai on 2018/12/18.
 */
public class ShopProductTypeDialog extends Dialog implements GoodsAttrAdapter.GoodsSpecListener {
    private static final String TAG = ShopProductTypeDialog.class.getSimpleName();
    private Context mContext;
    @BindView(R.id.iv_dialog_select_commodity_img)
    ImageView iv_dialog_select_commodity_img;
    @BindView(R.id.tv_dialog_select_commodity_price)
    TextView tv_dialog_select_commodity_price;
    @BindView(R.id.tv_dialog_select_commodity_num)
    TextView tv_dialog_select_commodity_num;
    @BindView(R.id.tv_dialog_select_commodity_stock_total)
    TextView tv_dialog_select_commodity_stock_total;

    @BindView(R.id.but_dialog_select_commodity_submit)
    Button butSubmit;
    @BindView(R.id.iv_dialog_select_commodity_clean)
    ImageView clean;

    @BindView(R.id.iv_dialog_select_commodity_decrease)
    ImageView iv_dialog_select_commodity_decrease;
    @BindView(R.id.tv_dialog_select_commodity_count)
    TextView tv_dialog_select_commodity_count;
    @BindView(R.id.iv_dialog_select_commodity_increase)
    ImageView iv_dialog_select_commodity_increase;

    @BindView(R.id.rv_dialog_select_commodity)
    RecyclerView recyclerView;
    private int goodscount = 1;//数量
    private long specId;//规格id
    private int stockTotal;//库存总数
    private boolean isShoppingCart = false; //是否添加到购物车
    private ShopProductTypeListener shopProductTypeListener;
    private LoadingDialog loadingDialog;
    private GoodsAttrAdapter goodsAttrAdapter;
    private CommodityDetailInfoDto goodsDetailaDto;


    public ShopProductTypeDialog(Context context, CommodityDetailInfoDto object, ShopProductTypeListener listener, boolean isShoppingCart) {
        super(context, R.style.dialog_with_alpha);
//      setCanceledOnTouchOutside(false);//设置点击外部不可以取消;
        this.mContext = context;
        setContentView(R.layout.dialog_shop_product_type);
        ButterKnife.bind(this);
        this.shopProductTypeListener = listener;
        this.isShoppingCart = isShoppingCart;
        goodsDetailaDto = object;
        initView();
        initData(object);
        initListener();
    }

    private void initView() {
        WindowManager manager = getWindow().getWindowManager();
        Display display = manager.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = (display.getWidth());
        Window window = getWindow();
        window.setAttributes(p);
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.bottomAnimStyle);
        loadingDialog = LoadingDialog.show(mContext);
        loadingDialog.setCanceledOnTouchOutside(false);
    }

    private void initListener() {
        bindClickEvent(iv_dialog_select_commodity_decrease, () -> {
            if (goodscount <= 1) {
                goodscount = 1;
            } else {
                --goodscount;
            }
            tv_dialog_select_commodity_count.setText("" + goodscount);
        });
        bindClickEvent(iv_dialog_select_commodity_increase, () -> {
            if (goodscount >= stockTotal) {//不能大于总库存
                goodscount = stockTotal;
            } else {
                ++goodscount;
            }
            tv_dialog_select_commodity_count.setText("" + goodscount);
        });
        bindClickEvent(clean, () -> {
            hide();
        });
        bindClickEvent(butSubmit, () -> {
            if (null != shopProductTypeListener) {
                shopProductTypeListener.callbackSelect(specId,goodscount);
            }
            hide();
        });
        goodsAttrAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SearchHistory object = (SearchHistory) adapter.getItem(position);
            }
        });
    }

    private void initData(CommodityDetailInfoDto object) {
        if (object == null) {
            return;
        }
        String imgUrl = BuildConfig.BASE_IMAGE_URL + object.getGoodsImg();
        GlideUtils.getInstances().loadRoundCornerImgUnCache(mContext, iv_dialog_select_commodity_img, 4, imgUrl);
        tv_dialog_select_commodity_price.setText(object.getGdPrice());
        tv_dialog_select_commodity_num.setText("商品编号:" + object.getGoodsSn());
//        stockTotal = object.getStockTotal();
//        tv_dialog_select_commodity_stock_total.setText("库存:" + stockTotal);
        if (isShoppingCart) {
            butSubmit.setText("加入购物车");
        }
        findGoodsAttr(object.getId());
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        recyclerView.setFocusable(false);
        goodsAttrAdapter = new GoodsAttrAdapter(null,object.getId(),mContext);
        recyclerView.setAdapter(goodsAttrAdapter);
        goodsAttrAdapter.setGoodsSpecListener(this);
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


    /**
     * 获取商品颜色，规格
     *
     * @param goodsId 商品id
     */
    private void findGoodsAttr(long goodsId) {
        DataManager.getInstance().findGoodsAttr(new DefaultSingleObserver<GoodsColorAndSpecDto>() {
            @Override
            public void onSuccess(GoodsColorAndSpecDto object) {
                LogUtil.i(TAG, "--RxLog-Thread: onSuccess()");
                loadingDialog.cancelDialog();
                if (null != object.getAttrMap() && !object.getAttrMap().isEmpty()) {
                    goodsAttrAdapter.setNewData(object.getAttrMap());
                    goodsAttrAdapter.setInitColorIds(object.getAttrMap());
                }
            }

            @Override
            public void onError(Throwable throwable) {
                LogUtil.i(TAG, "--RxLog-Thread: onError() = " + throwable.toString());
                loadingDialog.cancelDialog();
            }
        }, goodsId);
    }

    @Override
    public void callbackGoodsSpec(long specId, int stockTotal) {
        this.specId = specId;
        this.stockTotal = stockTotal;
        LogUtil.i(TAG, "--RxLog-Thread: onSuccess() 擦擦擦商品规格库存=" + stockTotal);
        tv_dialog_select_commodity_stock_total.setText("库存:" + stockTotal);
    }


    public interface ShopProductTypeListener {
        /**
         * 回调
         * @param specId
         * @param cartNum
         */
        void callbackSelect(long specId, int cartNum);
    }

}
