package com.ggkjg.view.mainfragment.order;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.view.adapter.AfterSaleDetailAdapter;
import com.tbruyelle.rxpermissions2.RxPermissions;
import java.util.HashMap;
import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * 个人中心 --> 退款/售后
 * Created by dahai on 2018/12/10.
 */
public class AfterSaleDetailActivity extends BaseActivity {


    @BindView(R.id.recy_return)
    RecyclerView recyclerView;
    @BindView(R.id.tv_des_status)
    TextView tv_des_status;
    @BindView(R.id.tv_des_date)
    TextView tv_des_date;

    @BindView(R.id.tv_return_money_red)
    TextView tv_return_money_red;

    @BindView(R.id.tv_return_info)
    TextView tv_return_info;
    @BindView(R.id.tv_return_money)
    TextView tv_return_money;
    @BindView(R.id.tv_return_data)
    TextView tv_return_data;
    @BindView(R.id.tv_return_no)
    TextView tv_return_no;

    @BindView(R.id.ll_address_detail)
    LinearLayout ll_address_detail;
    @BindView(R.id.tv_express_people)
    TextView tv_express_people;
    @BindView(R.id.tv_express_address)
    TextView tv_express_address;
    @BindView(R.id.tv_express_phone)
    TextView tv_express_phone;

    @BindView(R.id.ll_bottom_layout)
    LinearLayout ll_bottom_layout;
    @BindView(R.id.btn_content_user)
    Button btn_content_user;
    @BindView(R.id.btn_cancel)
    Button btn_cancel;


    AfterSaleDetailAdapter mAdapter;
    private String orderNo;
    String type;

    @Override
    public int getLayoutId() {
        return R.layout.ui_after_sale_detail_layout;
    }

    @Override
    public void initView() {
        actionbar.setImgStatusBar(R.color.my_color_white);
        StatusBarUtils.StatusBarLightMode(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            orderNo = bundle.getString(Constants.INTENT_ORDER_NO);
        }
        initAdapter();
    }

    @Override
    public void initData() {
        getOrderDetail();
    }

    @Override
    public void initListener() {
        bindClickEvent(btn_cancel, () -> {
        });
        bindClickEvent(btn_content_user, () -> {
        });
    }

    private void initAdapter() {

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setFocusable(false);
        mAdapter = new AfterSaleDetailAdapter(null);
        recyclerView.setAdapter(mAdapter);
    }

    private void getOrderDetail() {

    }

    private void setDataView() {

    }

    private void callPhone(String phone) {
        new RxPermissions(this).request(Manifest.permission.CALL_PHONE).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    Uri data = Uri.parse("tel:" + phone);
                    intent.setData(data);
                    startActivity(intent);
                }
            }
        });
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == Constants.INTENT_REQUESTCODE_FILL_EXPRESS) {
            ll_bottom_layout.setVisibility(View.GONE);
            getOrderDetail();
        }
    }
}
