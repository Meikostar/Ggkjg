package com.ggkjg.view.mainfragment.personalcenter;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.ggkjg.R;
import com.ggkjg.base.BaseFragment;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.SwipeRefreshLayoutUtil;
import com.ggkjg.dto.FloorBean;
import com.ggkjg.dto.GoodsOrderDetailDto;
import com.ggkjg.dto.MyccRecordDto;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.response.HttpResult;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.adapter.MYCCRecordAdapter;
import com.ggkjg.view.adapter.OrderListAdapter;
import com.ggkjg.view.widgets.SuperSwipeRefreshLayout;
import com.ggkjg.view.widgets.autoview.EmptyView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class MYCCFragment extends BaseFragment {
    @BindView(R.id.recy_my_comment)
    RecyclerView rvList;
    @BindView(R.id.refresh)
    SuperSwipeRefreshLayout swipeRefreshLayout;

    private SwipeRefreshLayoutUtil mSwipeRefreshLayoutUtil;
    private int mCurrentPage = Constants.PAGE_NUM;

    private String type = "";
    MYCCRecordAdapter mAdapter;

    public MYCCFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_comment_layout;
    }

    @Override
    protected void initView() {
        initAdapter();

    }

    @Override
    protected void initData() {
        mCurrentPage = Constants.PAGE_NUM;
        loadData(true);
    }

    @Override
    protected void initListener() {
        setListener();
    }


    private void loadData(boolean isLoad) {
        if (isLoad) {
            showLoadDialog();
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("page", mCurrentPage + "");
        map.put("rows", Constants.PAGE_SIZE + "");
        if (!TextUtils.isEmpty(type)) {
            map.put("type", type);
        }

        DataManager.getInstance().findMYCC(new DefaultSingleObserver<HttpResult<MyccRecordDto>>() {
            @Override
            public void onSuccess(HttpResult<MyccRecordDto> httpResult) {
                dissLoadDialog();
                if (httpResult != null && httpResult.getData() != null && httpResult.getData().getFundFlow() != null) {
                    if (mCurrentPage == Constants.PAGE_NUM) {
                        mAdapter.setNewData(httpResult.getData().getFundFlow().getRows());
                        if (httpResult.getData().getFundFlow().getRows() == null || httpResult.getData().getFundFlow().getRows().size() == 0) {
                            mAdapter.setEmptyView(new EmptyView(getActivity()));
                        }
                    } else {
                        mAdapter.addData(httpResult.getData().getFundFlow().getRows());
                    }

                }
            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();

            }
        }, map);


    }


    private void initAdapter() {
        rvList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new MYCCRecordAdapter();
        rvList.setAdapter(mAdapter);
    }


    private void setListener() {
        mSwipeRefreshLayoutUtil = new SwipeRefreshLayoutUtil();
        mSwipeRefreshLayoutUtil.setSwipeRefreshView(swipeRefreshLayout, new SwipeRefreshLayoutUtil.OnRefreshAndLoadMoreListener() {
            @Override
            public void onRefresh() {
                mCurrentPage = Constants.PAGE_NUM;
                loadData(false);
            }

            @Override
            public void onLoadMore() {
                mCurrentPage++;
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

    public void setType(String type) {
        this.type = type;
    }
}
