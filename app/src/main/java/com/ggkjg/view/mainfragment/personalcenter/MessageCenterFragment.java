package com.ggkjg.view.mainfragment.personalcenter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ggkjg.R;
import com.ggkjg.base.BaseFragment;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.SwipeRefreshLayoutUtil;
import com.ggkjg.dto.SystemMessageDataDto;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.adapter.MessageCenterAdapter;
import com.ggkjg.view.widgets.SuperSwipeRefreshLayout;
import com.ggkjg.view.widgets.autoview.EmptyView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class MessageCenterFragment extends BaseFragment {
    @BindView(R.id.recy_my_comment)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SuperSwipeRefreshLayout swipeRefreshLayout;
    SwipeRefreshLayoutUtil mSwipeRefreshLayoutUtil;
    MessageCenterAdapter mAdapter;
    private int mCurrentPage = Constants.PAGE_NUM;
    String type = "";

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
        systemMessage(true);
    }

    @Override
    protected void initListener() {
        setListener();
    }

    public void setType(String type) {
        this.type = type;
    }

    private void initAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new MessageCenterAdapter();
        recyclerView.setAdapter(mAdapter);
    }

    private void systemMessage(boolean isLoad) {
        HashMap<String, String> map = new HashMap<>();
        map.put("page",""+mCurrentPage);
        map.put("rows",""+Constants.PAGE_SIZE);
        map.put("classType",type);
        if (isLoad) {
            showLoadDialog();
        }

        DataManager.getInstance().systemMessage(new DefaultSingleObserver<SystemMessageDataDto>() {
            @Override
            public void onSuccess(SystemMessageDataDto systemMessageDataDto) {
                dissLoadDialog();
                if (systemMessageDataDto != null) {
                    if (mCurrentPage == Constants.PAGE_NUM) {
                        mAdapter.setNewData(systemMessageDataDto.getRows());
                        if(systemMessageDataDto.getRows() == null || systemMessageDataDto.getRows().size() == 0){
                            mAdapter.setEmptyView(new EmptyView(getActivity()));
                        }
                    } else {
                        mAdapter.addData(systemMessageDataDto.getRows());
                    }
                }
            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();
            }
        }, map);
    }

    private void setListener() {
        mSwipeRefreshLayoutUtil = new SwipeRefreshLayoutUtil();
        mSwipeRefreshLayoutUtil.setSwipeRefreshView(swipeRefreshLayout, new SwipeRefreshLayoutUtil.OnRefreshAndLoadMoreListener() {
            @Override
            public void onRefresh() {
                mCurrentPage = Constants.PAGE_NUM;
                systemMessage(false);
            }

            @Override
            public void onLoadMore() {
                mCurrentPage++;
                systemMessage(false);
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
}
