package com.ggkjg.view.mainfragment.personalcenter;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ggkjg.R;
import com.ggkjg.base.BaseFragment;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.SwipeRefreshLayoutUtil;
import com.ggkjg.dto.ShopEvaluateImgListDto;
import com.ggkjg.dto.ShopEvaluateRowsDto;
import com.ggkjg.dto.ShopEvaluateTotalDto;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.adapter.MyCommentAdapter;
import com.ggkjg.view.mainfragment.ImagePreviewActivity;
import com.ggkjg.view.widgets.SuperSwipeRefreshLayout;
import com.ggkjg.view.widgets.autoview.EmptyView;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import butterknife.BindView;


public class MyCommentFragment extends BaseFragment {
    @BindView(R.id.recy_my_comment)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    public SuperSwipeRefreshLayout swipeRefreshLayout;

    SwipeRefreshLayoutUtil swipeRefreshLayoutUtil;
    private int currentPage = Constants.PAGE_NUM;

    MyCommentAdapter mAdapter;
    private String type = "";

    public void setType(String type) {
        this.type = type;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_comment_layout;
    }

    @Override
    protected void initView() {
        initAdapter();
        setListener();
    }

    @Override
    protected void initData() {
        currentPage = Constants.PAGE_NUM;
        loadData(true);
    }

    @Override
    protected void initListener() {

    }

    private void loadData(boolean isLoad) {
        HashMap<String, String> map = new HashMap<>();
        map.put("page", currentPage + "");

        map.put("rows", Constants.PAGE_SIZE + "");
        if (Constants.COMMENT_IMAGE_DATA_TYPE.equals(type)) {
            map.put("type", "1");
        }
        if (isLoad) {
            showLoadDialog();
        }

        DataManager.getInstance().findMyEvaList(new DefaultSingleObserver<ShopEvaluateTotalDto>() {
            @Override
            public void onSuccess(ShopEvaluateTotalDto shopEvaluateDto) {
                dissLoadDialog();
                if (currentPage <= Constants.PAGE_NUM) {
                    mAdapter.setNewData(shopEvaluateDto.getRows());
                    if (shopEvaluateDto.getRows() == null || shopEvaluateDto.getRows().size() == 0) {
                        mAdapter.setEmptyView(new EmptyView(getActivity()));
                    }
                } else {
                    mAdapter.addData(shopEvaluateDto.getRows());
                }
            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();
            }
        }, map);
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

    private void initAdapter() {

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new MyCommentAdapter(null, type);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ShopEvaluateRowsDto evaluateDto = mAdapter.getItem(position);
                switch (view.getId()) {
                    case R.id.img_commodity_1:
                        dealImagePreview(0, evaluateDto.getImgList());
                        break;
                    case R.id.img_commodity_2:
                        dealImagePreview(1, evaluateDto.getImgList());
                        break;
                    case R.id.img_commodity_3:
                        dealImagePreview(2, evaluateDto.getImgList());
                        break;
                }
            }
        });
    }

    private void dealImagePreview(int position, List<ShopEvaluateImgListDto> imageList) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ImagePreviewActivity.INTENT_KEY_DATA,  (Serializable) imageList);
        bundle.putInt(ImagePreviewActivity.INTENT_KEY_START_POSITION, position);
        gotoActivity(ImagePreviewActivity.class, false, bundle);
    }

}
