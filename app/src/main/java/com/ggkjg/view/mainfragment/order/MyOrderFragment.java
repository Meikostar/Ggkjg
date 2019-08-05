package com.ggkjg.view.mainfragment.order;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ggkjg.R;
import com.ggkjg.base.BaseFragment;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.LogUtil;
import com.ggkjg.common.utils.SwipeRefreshLayoutUtil;
import com.ggkjg.common.utils.ToastUtil;
import com.ggkjg.dto.FloorBean;
import com.ggkjg.dto.GoodsOrderDetailDto;
import com.ggkjg.dto.GoodsPushDto;
import com.ggkjg.dto.GoodsPushRowsDto;
import com.ggkjg.http.error.ApiException;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.adapter.HomeAdapter;
import com.ggkjg.view.adapter.OrderListAdapter;
import com.ggkjg.view.widgets.RecyclerItemDecoration;
import com.ggkjg.view.widgets.SuperSwipeRefreshLayout;
import com.ggkjg.view.widgets.autoview.EmptyView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyOrderFragment extends BaseFragment {
    @BindView(R.id.recy_my_order)
    RecyclerView rvList;
    @BindView(R.id.recy_push)
    RecyclerView recommendRecy;
    @BindView(R.id.refresh)
    public SuperSwipeRefreshLayout swipeRefreshLayout;

    private static final String TAG = "MyOrderFragment";
    private SwipeRefreshLayoutUtil mSwipeRefreshLayoutUtil;
    private int mCurrentPage = 1;
    private ArrayList mFloorBeans;
    private OrderListAdapter mOrderListAdapter;

    HomeAdapter pushAdapter;
    private String type = "";
    private String mOrderStatus;

    public MyOrderFragment() {
    }


    public static MyOrderFragment newInstance(String orderStatus) {
        MyOrderFragment fragment = new MyOrderFragment();
        Bundle args = new Bundle();
        args.putString("orderStatus", orderStatus);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mOrderStatus = getArguments().getString("orderStatus");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_order_layout;
    }

    @Override
    protected void initView() {
        initAdapter();
        setListener();
    }

    @Override
    protected void initData() {
        mCurrentPage = 1;
        loadData(true);
        findQualityGoodsList();
    }

    @Override
    protected void initListener() {

    }

    public void loadData(boolean isLoad) {
        findOrderList(isLoad);
    }

    private void findOrderList(boolean isLoad) {
        if (isLoad) {
            showLoadDialog();
        }
        DataManager.getInstance().findOrderList(type, new DefaultSingleObserver<List<GoodsOrderDetailDto>>() {
            @Override
            public void onSuccess(List<GoodsOrderDetailDto> datas) {
                dissLoadDialog();
                ArrayList mFloorBeans = new ArrayList();
                for (GoodsOrderDetailDto data : datas) {
                    FloorBean floorBean = new FloorBean();
                    floorBean.setOrderDetail(data);
                    floorBean.setType(0);
                    mFloorBeans.add(floorBean);
                }
                mOrderListAdapter.setNewData(mFloorBeans);

                //if (mFloorBeans.size() == 0) {
                //    mOrderListAdapter.setEmptyView(new EmptyView(getActivity()));
                //}
                if (mCurrentPage == Constants.PAGE_NUM &&mOrderListAdapter.getItemCount() == 0 ) {
                    mOrderListAdapter.setNewData(null);
                    mOrderListAdapter.setEmptyView(new EmptyView(getActivity()));
                }
            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();
//                LogUtil.e(TAG, throwable.getMessage());
            }
        });
    }

    /**
     * 获取精品推荐
     */
    private void findQualityGoodsList() {
        DataManager.getInstance().findQualityList(new DefaultSingleObserver<List<GoodsPushRowsDto>>() {
            @Override
            public void onSuccess(List<GoodsPushRowsDto> object) {
                if (object != null && !object.isEmpty()) {
                    pushAdapter.setNewData(object);
                } else {
                    EmptyView emptyView = new EmptyView(getActivity());
                    emptyView.setTvEmptyTip("暂无推荐商品");
                    pushAdapter.setEmptyView(emptyView);
                }
            }

            @Override
            public void onError(Throwable throwable) {
            }
        });
    }


    private void initAdapter() {
        rvList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mFloorBeans = new ArrayList<>();
        mOrderListAdapter = new OrderListAdapter(mFloorBeans);
        rvList.setAdapter(mOrderListAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recommendRecy.addItemDecoration(new RecyclerItemDecoration(3, 2));
        recommendRecy.setLayoutManager(gridLayoutManager);

        pushAdapter = new HomeAdapter(null, getActivity());
        recommendRecy.setAdapter(pushAdapter);
    }


    private void setListener() {
        mSwipeRefreshLayoutUtil = new SwipeRefreshLayoutUtil();
        swipeRefreshLayout.setEnableLoadMore(false);
        mSwipeRefreshLayoutUtil.setSwipeRefreshView(swipeRefreshLayout, new SwipeRefreshLayoutUtil.OnRefreshAndLoadMoreListener() {
            @Override
            public void onRefresh() {
                loadData(false);
            }

            @Override
            public void onLoadMore() {
            }
        });
    }

    @Override
    protected void dissLoadDialog() {
        super.dissLoadDialog();
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    public void setType(String type) {
        this.type = type;
    }
}
