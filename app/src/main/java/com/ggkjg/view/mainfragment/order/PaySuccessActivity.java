package com.ggkjg.view.mainfragment.order;

import android.app.Activity;
import android.content.Intent;
import android.drm.DrmStore;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.StatusBarUtils;

import butterknife.BindView;

/**
 * Created by lihaoqi on 2019/1/29.
 */

public class PaySuccessActivity extends BaseActivity {
    @BindView(R.id.tv_order_money)
    TextView tv_order_money;
    @BindView(R.id.btn_look_order)
    TextView btn_look_order;

    private String orderMoney, orderId;

    @Override
    public int getLayoutId() {
        return R.layout.ui_pay_success;
    }

    @Override
    public void initView() {
        actionbar.setImgStatusBar(R.color.my_color_white);
        StatusBarUtils.StatusBarLightMode(this);
        actionbar.setTitle("付款成功");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            orderMoney = bundle.getString(Constants.ORDER_MONEY);
            orderId = bundle.getString(Constants.ORDER_ID);
            if (!TextUtils.isEmpty(orderMoney)) {
                tv_order_money.setText(orderMoney);
            }

        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        bindClickEvent(btn_look_order, () -> {
            Intent intent = new Intent(PaySuccessActivity.this, MyOrderDetailActivity.class);
            intent.putExtra(Constants.ORDER_ID, orderId);
            startActivity(intent);
            finish();
        });
    }


}
