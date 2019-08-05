package com.ggkjg.view.mainfragment.order;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.common.utils.SwipeRefreshLayoutUtil;
import com.ggkjg.view.adapter.AfterSaleAdapter;
import com.ggkjg.view.widgets.SuperSwipeRefreshLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import butterknife.BindView;

/**
 * 个人中心 --> 退款/售后
 * Created by dahai on 2018/12/10.
 */
public class AfterSaleActivity extends BaseActivity {

    private static final String TAG = AfterSaleActivity.class.getSimpleName();

    @BindView(R.id.common_recycler)
    RecyclerView recyclerView;
    private String type = "";
    @BindView(R.id.common_refresh)
    public SuperSwipeRefreshLayout swipeRefreshLayout;

    SwipeRefreshLayoutUtil swipeRefreshLayoutUtil;
    private int currentPage = Constants.PAGE_NUM;


    AfterSaleAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.after_sale_layout;
    }

    @Override
    public void initView() {
        actionbar.setImgStatusBar(R.color.my_color_white);
        StatusBarUtils.StatusBarLightMode(this);
        initAdapter();
        setListener();
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        currentPage = Constants.PAGE_NUM;
        loadData(true);
    }

    @Override
    public void initListener() {

    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new AfterSaleAdapter(null);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }

    private void setListener() {
        swipeRefreshLayoutUtil = new SwipeRefreshLayoutUtil();
        swipeRefreshLayoutUtil.setSwipeRefreshView(swipeRefreshLayout, new SwipeRefreshLayoutUtil.OnRefreshAndLoadMoreListener() {
            @Override
            public void onRefresh() {
                currentPage = Constants.PAGE_NUM;
                swipeRefreshLayoutUtil.setCanLoadMore(true);
                loadData(false);
            }

            @Override
            public void onLoadMore() {
                swipeRefreshLayoutUtil.setCanLoadMore(true);
                currentPage++;
                loadData(false);
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

    private void loadData(boolean isLoad) {

//        DataManager.getInstance().getMyOrderList(new DefaultSingleObserver<HttpResult<List<MyOrderDto>>>() {
//            @Override
//            public void onSuccess(HttpResult<List<MyOrderDto>> httpResult) {
//                dissLoadDialog();
//                List<GoodMultiItemEntity> newData = dealDataList(httpResult.getData());
//                if (currentPage <= Constants.PAGE_NUM) {
//
//                    mAdapter.setNewData(newData);
//                    if (httpResult.getData() == null || httpResult.getData().size() == 0) {
//                        mAdapter.setEmptyView(new EmptyView(AfterSaleActivity.this));
//                    }
//                } else {
//                    mAdapter.addData(newData);
//                }
//                swipeRefreshLayoutUtil.isMoreDate(httpResult.getMeta());
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//                dissLoadDialog();
//            }
//        }, map);
    }

    /**
     * 数据拆分
     **/
//    private List<GoodMultiItemEntity> dealDataList(List<MyOrderDto> myOrderDtoList) {
//        List<GoodMultiItemEntity> newData = new ArrayList<>();
//        if (myOrderDtoList == null) {
//            return newData;
//        }
//        for (MyOrderDto myOrderDto : myOrderDtoList) {
//            //产品信息
//            GoodOrderInfo goodOrderInfo = new GoodOrderInfo(myOrderDto.getStatus(), myOrderDto.getRefund_status(),
//                    myOrderDto.getNo(), myOrderDto.getRemaining_expires(), myOrderDto.getId());
//            goodOrderInfo.setItemType(goodOrderInfo.GOOD_ORDER_INFO);
//            MyOrderShopDataDto shopDataDto = myOrderDto.getShop();
//            String shopName = "";
//            if (shopDataDto != null && shopDataDto.getData() != null) {
//                shopName = shopDataDto.getData().getShop_name();
//            }
//            goodOrderInfo.setShop_name(shopName);
//            newData.add(goodOrderInfo);
//
//            if (myOrderDto.getItems() != null && myOrderDto.getItems().getData() != null && myOrderDto.getItems().getData().size() > 0) {
//                //产品子信息
//                List<MyOrderItemDto> myOrderItemDtoList = myOrderDto.getItems().getData();
//                for (MyOrderItemDto myOrderItemDto : myOrderItemDtoList) {
//                    myOrderItemDto.setItemType(myOrderItemDto.GOOD_ORDER_ITEM);
//                    myOrderItemDto.setNo(myOrderDto.getNo());
//                    newData.add(myOrderItemDto);
//                }
//            }
//
//            GoodPayInfo goodPayInfo = new GoodPayInfo(myOrderDto.getStatus(), myOrderDto.getRefund_status()
//                    , myOrderDto.getId(), myOrderDto.getRemaining_expires());
//            goodPayInfo.setItemType(goodPayInfo.GOOD_ORDER_PAY);
//            goodPayInfo.setNo(myOrderDto.getNo());
//            goodPayInfo.setRefund_status_text(myOrderDto.getRefund_status_text());
//            newData.add(goodPayInfo);
//        }
//        return newData;
//    }

}
