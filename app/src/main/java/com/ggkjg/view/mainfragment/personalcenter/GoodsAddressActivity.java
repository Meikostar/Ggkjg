package com.ggkjg.view.mainfragment.personalcenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.LogUtil;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.common.utils.SwipeRefreshLayoutUtil;
import com.ggkjg.common.utils.ToastUtil;
import com.ggkjg.dto.AddreessDataDto;
import com.ggkjg.dto.AddressDto;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.response.HttpResult;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.adapter.GoodAddressAdapter;
import com.ggkjg.view.widgets.SuperSwipeRefreshLayout;
import com.ggkjg.view.widgets.autoview.EmptyView;

import java.util.List;

import butterknife.BindView;


/**
 * 个人中心 --> 收货地址
 */
public class GoodsAddressActivity extends BaseActivity {
    private static final String TAG = GoodsAddressActivity.class.getName();

    @BindView(R.id.common_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.common_refresh)
    SuperSwipeRefreshLayout swipeRefreshLayout;

    SwipeRefreshLayoutUtil swipeRefreshLayoutUtil;

    GoodAddressAdapter mAdapter;
    public static final int INTENT_REQUESTCODE_ADD_ADDRESS = 100;
    private boolean isSelAddress;
    private int currentPage = Constants.PAGE_NUM;


    @Override
    public int getLayoutId() {
        return R.layout.ui_setting_goods_address_layout;
    }

    @Override
    public void initView() {
        actionbar.setImgStatusBar(R.color.my_color_white);
        StatusBarUtils.StatusBarLightMode(this);
        actionbar.setTextAction("添加", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(AddGoodsAddressActivity.class, false, null, INTENT_REQUESTCODE_ADD_ADDRESS);
            }
        });
        actionbar.setTextActionColor(getResources().getColor(R.color.my_color_212121));
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isSelAddress = bundle.getBoolean(Constants.INTENT_SEL_ADDRESS, false);
        }

        initAdapter();
        setListener();
    }

    @Override
    public void initData() {

        initAddressList(true);
    }

    @Override
    public void initListener() {

    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(GoodsAddressActivity.this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new GoodAddressAdapter(null);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                AddressDto addressDto = (AddressDto) adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.tv_good_address_del:
                        delAddress(addressDto.getId() + "");
                        break;
                    case R.id.tv_good_address_edit:
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Constants.INTENT_DATA, addressDto);
                        bundle.putBoolean(Constants.INTENT_ADDRESS_ISEDIT, true);
                        gotoActivity(AddGoodsAddressActivity.class, false, bundle, INTENT_REQUESTCODE_ADD_ADDRESS);
                        break;
                }
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (isSelAddress) {
                    AddressDto addressDto = (AddressDto) adapter.getItem(position);
                    Intent intent = new Intent();
                    intent.putExtra("result", addressDto);
                    GoodsAddressActivity.this.setResult(RESULT_OK, intent);
                    //关闭Activity
                    GoodsAddressActivity.this.onBackPressed();
                }
            }
        });
    }


    private void initAddressList(boolean isLoad) {
        showLoadDialog();
        DataManager.getInstance().getReceiptAddrList(new DefaultSingleObserver<AddreessDataDto>() {
            @Override
            public void onSuccess(AddreessDataDto addreessDataDto) {
                dissLoadDialog();
                if (addreessDataDto != null) {
                    mAdapter.setNewData(addreessDataDto.getRows());
                    if(addreessDataDto.getRows().size() == 0){
                        mAdapter.setEmptyView(new EmptyView(GoodsAddressActivity.this));
                    }
                }

            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();
                ToastUtil.toast(throwable.getMessage());
            }
        }, currentPage);
    }

    private void delAddress(String id) {
        showLoadDialog();
        DataManager.getInstance().delReceiptAddr(new DefaultSingleObserver<HttpResult<Object>>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                dissLoadDialog();
                if (httpResult != null) {
                    if (httpResult.getStatus() == 1) {
                        ToastUtil.showToast("删除成功");
                        currentPage = Constants.PAGE_NUM;
                        initAddressList(true);
                    } else {
                        ToastUtil.showToast(httpResult.getMsg());
                    }
                }

            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();
            }
        }, id);
    }

    private void setListener() {
        swipeRefreshLayoutUtil = new SwipeRefreshLayoutUtil();
        swipeRefreshLayoutUtil.setSwipeRefreshView(swipeRefreshLayout, new SwipeRefreshLayoutUtil.OnRefreshAndLoadMoreListener() {
            @Override
            public void onRefresh() {
                currentPage = Constants.PAGE_NUM;
                swipeRefreshLayoutUtil.setCanLoadMore(true);
                initAddressList(false);
            }

            @Override
            public void onLoadMore() {
                swipeRefreshLayoutUtil.setCanLoadMore(true);
                currentPage++;
                initAddressList(false);
            }
        });
    }

    @Override
    protected void dissLoadDialog() {
        super.dissLoadDialog();
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
            swipeRefreshLayout.setLoadMore(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == INTENT_REQUESTCODE_ADD_ADDRESS && resultCode == Activity.RESULT_OK) {
            currentPage = Constants.PAGE_NUM;
            initAddressList(true);
        }
    }
}
