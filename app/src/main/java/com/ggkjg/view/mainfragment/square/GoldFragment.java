package com.ggkjg.view.mainfragment.square;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ggkjg.R;
import com.ggkjg.base.BaseFragment;
import com.ggkjg.common.Constants;
import com.ggkjg.common.type.TransferType;
import com.ggkjg.common.utils.LogUtil;
import com.ggkjg.common.utils.SwipeRefreshLayoutUtil;
import com.ggkjg.common.utils.ToastUtil;
import com.ggkjg.dto.SquareDto;
import com.ggkjg.http.error.ApiException;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.adapter.GoldInputAdapter;
import com.ggkjg.view.adapter.GoldOutAdapter;
import com.ggkjg.view.widgets.SuperSwipeRefreshLayout;
import com.ggkjg.view.widgets.autoview.EmptyView;

import butterknife.BindView;

/**
 * 港豆出让 \ 港豆收购
 * Created by dahai on 2019/01/18.
 */

public class GoldFragment extends BaseFragment {
    private static final String TAG = GoldFragment.class.getSimpleName();
    private int type = TransferType.push.getType();//1:港豆出让,2:港豆收购
    @BindView(R.id.home_gold_recy)
    RecyclerView recyclerView;
    private GoldInputAdapter inputAdapter;
    private GoldOutAdapter outAdapter;

    @BindView(R.id.refresh_gold_recy)
    SuperSwipeRefreshLayout swipeRefreshLayout;
    SwipeRefreshLayoutUtil swipeRefreshLayoutUtil;
    private int currentPage = Constants.PAGE_NUM;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gold_layout;
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected void initData() {
        if (getType() == TransferType.push.getType()) {
            outAdapter = new GoldOutAdapter(null, getActivity());
            recyclerView.setAdapter(outAdapter);
        } else if (getType() == TransferType.pull.getType()) {
            inputAdapter = new GoldInputAdapter(null, getActivity());
            recyclerView.setAdapter(inputAdapter);
        }
        getSquareList(false,getType(), currentPage, Constants.PAGE_SIZE);
    }

    @Override
    protected void initListener() {
        setScrollListener(getType(),Constants.PAGE_SIZE);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    /**
     * 滚动监听
     *      *
     * @param type 1:港豆出让,2:港豆收购
     * @param rows 每页加载行数
     */
    private void setScrollListener(int type, int rows) {
        swipeRefreshLayoutUtil = new SwipeRefreshLayoutUtil();
//        swipeRefreshLayout.setEnablePull(false);
        swipeRefreshLayoutUtil.setSwipeRefreshView(swipeRefreshLayout, new SwipeRefreshLayoutUtil.OnRefreshAndLoadMoreListener() {
            @Override
            public void onRefresh() {
                currentPage = Constants.PAGE_NUM;
                swipeRefreshLayoutUtil.setCanLoadMore(true);
                getSquareList(true,type, currentPage, rows);
            }

            @Override
            public void onLoadMore() {
                swipeRefreshLayoutUtil.setCanLoadMore(true);
                currentPage++;
                getSquareList(true,type, currentPage, rows);
            }
        });
    }

    /**
     * 获取广场列表
     * @param isScrollLoad 是否滚动加载
     * @param type 1:港豆出让,2:港豆收购
     * @param currentPage
     * @param rows
     */
    private void getSquareList(boolean isScrollLoad,int type, int currentPage, int rows) {
        if(!isScrollLoad){
            showLoadDialog();
        }
        DataManager.getInstance().getSquareList(new DefaultSingleObserver<SquareDto>() {
            @Override
            public void onSuccess(SquareDto object) {
                LogUtil.i(TAG, "--RxLog-Thread: onSuccess()");
                if (object != null && object.getRows() != null && !object.getRows().isEmpty()) {
                    if (currentPage <= Constants.PAGE_NUM) {
                        if (type == TransferType.push.getType()) {
                            outAdapter.setNewData(object.getRows());
                        } else {
                            inputAdapter.setNewData(object.getRows());
                        }
                    } else {
                        if (type == TransferType.push.getType()) {
                            outAdapter.setNewData(object.getRows());
                        } else {
                            inputAdapter.setNewData(object.getRows());
                        }
                    }
                    swipeRefreshLayoutUtil.isMoreDate(currentPage, rows, object.getTotal());
                } else {
                    swipeRefreshLayoutUtil.isMoreDate(currentPage, rows, 0);
                    EmptyView emptyView = new EmptyView(getActivity());
                    if (type == TransferType.push.getType()) {
                        emptyView.setTvEmptyTip(String.format("暂无%s数据", TransferType.push.getValue()));
                        outAdapter.setEmptyView(emptyView);
                    } else {
                        emptyView.setTvEmptyTip(String.format("暂无%s数据", TransferType.pull.getValue()));
                        inputAdapter.setEmptyView(emptyView);
                    }
                }
                dissLoadDialog();
            }

            @Override
            public void onError(Throwable throwable) {
                LogUtil.i(TAG, "--RxLog-Thread: onError() = " );
                dissLoadDialog();
            }
        }, currentPage, rows, type);
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
