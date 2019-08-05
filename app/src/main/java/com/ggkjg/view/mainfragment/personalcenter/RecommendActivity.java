package com.ggkjg.view.mainfragment.personalcenter;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.LogUtil;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.common.utils.SwipeRefreshLayoutUtil;
import com.ggkjg.common.utils.ToastUtil;
import com.ggkjg.dto.AccountBalanceDto;
import com.ggkjg.dto.DataPageDto;
import com.ggkjg.dto.EvaluateDto;
import com.ggkjg.dto.MemberLevelDto;
import com.ggkjg.dto.RecommendDto;
import com.ggkjg.http.error.ApiException;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.adapter.MyCommentAdapter;
import com.ggkjg.view.adapter.PopWalletFilterAdapter;
import com.ggkjg.view.adapter.RecommendAdapter;
import com.ggkjg.view.widgets.SuperSwipeRefreshLayout;
import com.ggkjg.view.widgets.autoview.EmptyView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/*我的推荐列表*/
public class RecommendActivity extends BaseActivity {
    private static final String TAG = RecommendActivity.class.getSimpleName();
    @BindView(R.id.common_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.common_refresh)
    SuperSwipeRefreshLayout swipeRefreshLayout;
    SwipeRefreshLayoutUtil swipeRefreshLayoutUtil;

    RecommendAdapter mAdapter;
    private View contentView;
    PopupWindow window;
    private int currentPage = Constants.PAGE_NUM;
    String memberLevel = "";

    @Override
    public void initListener() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.ui_recommend_layout;
    }

    @Override
    public void initView() {
        actionbar.setImgStatusBar(R.color.my_color_white);
        StatusBarUtils.StatusBarLightMode(this);
        initAdapter();
        actionbar.setTextAction("分类", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMemberLevel();
            }
        });
        setListener();
    }

    @Override
    public void initData() {
        findMyTeam(true);
    }

    private void initAdapter() {

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new RecommendAdapter(null);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }

    private void filterView(List<MemberLevelDto> object) {
        if (contentView == null) {
            // 用于PopupWindow的View
            contentView = LayoutInflater.from(this).inflate(R.layout.pop_recommend_filter_layout, null, false);
            // 创建PopupWindow对象，其中：
            // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
            // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
            recyclerView = (RecyclerView) contentView.findViewById(R.id.recy);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            PopWalletFilterAdapter windowAdapter = new PopWalletFilterAdapter(object);
            recyclerView.setAdapter(windowAdapter);
            windowAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    MemberLevelDto item = (MemberLevelDto) adapter.getItem(position);
                    currentPage = Constants.PAGE_NUM;
                    memberLevel = String.valueOf(item.getMemberLevel());
                    findMyTeam(true);
                    window.dismiss();
                }
            });
        }

        if (window == null) {
            window = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
            // 设置PopupWindow的背景
            window.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.transparent)));
            // 设置PopupWindow是否能响应外部点击事件
            window.setOutsideTouchable(true);
            // 设置PopupWindow是否能响应点击事件
            window.setTouchable(true);
            // 显示PopupWindow，其中：
            // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
        }
        window.showAsDropDown(actionbar, 0, 0);
    }

    /**
     * 获取会员等级
     */
    private void getMemberLevel() {
        showLoadDialog();
        DataManager.getInstance().findMemberLevel(new DefaultSingleObserver<List<MemberLevelDto>>() {
            @Override
            public void onSuccess(List<MemberLevelDto> object) {
                LogUtil.i(TAG, "--RxLog-Thread: onSuccess() object.size()=" + object.size());
                dissLoadDialog();
                filterView(object);
            }

            @Override
            public void onError(Throwable throwable) {
                LogUtil.i(TAG, "--RxLog-Thread: onError() = ");
                dissLoadDialog();
            }
        });
    }

    /**
     * 获取团队信息
     */
    private void findMyTeam(boolean isLoad) {
        showLoadDialog();
        DataManager.getInstance().findMyTeam(new DefaultSingleObserver<DataPageDto<RecommendDto>>() {
            @Override
            public void onSuccess(DataPageDto<RecommendDto> dataPageDto) {
                dissLoadDialog();
                if (dataPageDto != null) {
                    if (currentPage == Constants.PAGE_NUM) {
                        mAdapter.setNewData(dataPageDto.getRows());
                        if(dataPageDto.getRows() ==null || dataPageDto.getRows().size() == 0){
                            mAdapter.setEmptyView(new EmptyView(RecommendActivity.this));
                        }
                    } else {
                        mAdapter.addData(dataPageDto.getRows());
                    }
                }
            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();
            }
        }, currentPage, memberLevel);
    }

    private void setListener() {
        swipeRefreshLayoutUtil = new SwipeRefreshLayoutUtil();
        swipeRefreshLayoutUtil.setSwipeRefreshView(swipeRefreshLayout, new SwipeRefreshLayoutUtil.OnRefreshAndLoadMoreListener() {
            @Override
            public void onRefresh() {
                currentPage = Constants.PAGE_NUM;
                swipeRefreshLayoutUtil.setCanLoadMore(true);
                findMyTeam(false);
            }

            @Override
            public void onLoadMore() {
                swipeRefreshLayoutUtil.setCanLoadMore(true);
                currentPage++;
                findMyTeam(false);
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
