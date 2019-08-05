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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ggkjg.R;
import com.ggkjg.common.utils.LogUtil;
import com.ggkjg.dto.ChooseAddressDto;;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.adapter.AddressFilterAdapter;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.functions.Action;

/**
 * 选择自提地址
 * Created by rzb on 2019/3/16.
 */
public class OrderChooseAddressDialog extends Dialog {
    private static final String TAG = OrderChooseAddressDialog.class.getSimpleName();
    private Context mContext;
    @BindView(R.id.recy_choose_address)
    RecyclerView recy_choose_address;
    private ChooseAddressListener chooseAddressListener;
    private LoadingDialog loadingDialog;
    private AddressFilterAdapter addressFilterAdapter;

    public OrderChooseAddressDialog(Context context,ChooseAddressListener listener) {
        super(context, R.style.dialog_with_alpha);
        this.mContext = context;
        setContentView(R.layout.pop_address_choose_layout);
        ButterKnife.bind(this);
        this.chooseAddressListener = listener;
        initView();
        initData();
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
        addressFilterAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ChooseAddressDto  object = (ChooseAddressDto)adapter.getItem(position);
                String addressStr = object.getTempName();
                chooseAddressListener.callbackChooseAddress(addressStr);
                hide();
            }
        });
    }

    private void initData() {
        takeList();
        recy_choose_address.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        recy_choose_address.setFocusable(false);
        addressFilterAdapter = new AddressFilterAdapter(null);
        recy_choose_address.setAdapter(addressFilterAdapter);
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
     * 获取自提地址地址
     */
    private void takeList() {
        DataManager.getInstance().takeList(new DefaultSingleObserver<List<ChooseAddressDto>>() {
            @Override
            public void onSuccess(List<ChooseAddressDto>  objList) {
                LogUtil.i(TAG, "--RxLog-Thread: onSuccess()" + objList.size());
                loadingDialog.cancelDialog();
                if (null != objList && !objList.isEmpty() && objList.size()>0) {
                    addressFilterAdapter.setNewData(objList);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                LogUtil.i(TAG, "--RxLog-Thread: onError() = " + throwable.toString());
                loadingDialog.cancelDialog();
            }
        });
    }

    public interface ChooseAddressListener {
        void callbackChooseAddress(String addressStr);
    }
}
