package com.ggkjg.view.mainfragment.order;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.ToastUtil;
import com.ggkjg.dto.LogisticsDataDto;
import com.ggkjg.dto.LogisticsExpressDto;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.response.HttpResult;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.adapter.MyOrderLogisticsAdapter;

import java.io.Serializable;

import butterknife.BindView;


/**
 * 物流信息
 */
public class MyOrderLogisticsActivity extends BaseActivity {
    @BindView(R.id.recy_logistics)
    RecyclerView recyclerView;
    @BindView(R.id.tv_logistics_express_name)
    TextView tv_logistics_express_name;
    @BindView(R.id.tv_logistics_express_number)
    TextView tv_logistics_express_number;

    MyOrderLogisticsAdapter mAdapter;
    String expressId, expressNo;

    @Override
    public void initListener() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.ui_myorder_logistics_layout;
    }

    @Override
    public void initView() {
        initAdapter();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            expressId = bundle.getString(Constants.EXPRESS_ID);
            expressNo = bundle.getString(Constants.EXPRESS_NO);
        }


    }

    @Override
    public void initData() {
        getLogisticsInfo();
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new MyOrderLogisticsAdapter();
        recyclerView.setAdapter(mAdapter);
    }

    private void getLogisticsInfo() {
        showLoadDialog();
        DataManager.getInstance().findExpressInfo(new DefaultSingleObserver<HttpResult<LogisticsDataDto>>() {
            @Override
            public void onSuccess(HttpResult<LogisticsDataDto> httpResult) {
                dissLoadDialog();
                if (httpResult != null && httpResult.getData() != null) {
                    mAdapter.setNewData(httpResult.getData().getContentList());
                    initDataView(httpResult.getData().getExpress());
                }
            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();
            }
        }, expressId, expressNo);

    }

    private void initDataView(LogisticsExpressDto expressDto) {
        if (expressDto != null) {
            tv_logistics_express_name.setText("承运来源:" + expressDto.getExpressName());
            tv_logistics_express_number.setText("订单运号:" + expressNo);
        }
    }

}
