package com.ggkjg.view.mainfragment.message;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.base.BuildConfig;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.GlideUtils;
import com.ggkjg.common.utils.LogUtil;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.common.utils.SwipeRefreshLayoutUtil;
import com.ggkjg.common.utils.ToastUtil;
import com.ggkjg.dto.MessageDto;
import com.ggkjg.dto.MessageListDto;
import com.ggkjg.http.error.ApiException;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.adapter.ShopMessageListAdapter;
import com.ggkjg.view.mainfragment.HomeFragment;
import com.ggkjg.view.mainfragment.shop.ShopProductListActivity;
import com.ggkjg.view.widgets.LoadingDialog;
import com.ggkjg.view.widgets.RecyclerItemDecoration;
import com.ggkjg.view.widgets.SuperSwipeRefreshLayout;
import com.ggkjg.view.widgets.autoview.EmptyView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 商城快讯列表
 * Created by xld021 on 2018/4/14.
 */

public class ShopMessageListActivity extends BaseActivity {
    private static final String TAG = HomeFragment.class.getSimpleName();
    @BindView(R.id.ll_shop_message_top)
    LinearLayout ll_shop_message_top;
    @BindView(R.id.recy_shop_message_list)
    RecyclerView recyclerView;
    ShopMessageListAdapter adapter;

    @BindView(R.id.iv_shop_message)
    ImageView iv_shop_message;
    @BindView(R.id.tv_shop_message_title)
    TextView tv_shop_message_title;
    @BindView(R.id.tv_shop_message_time)
    TextView tv_shop_message_time;
    @BindView(R.id.tv_shop_message_desc)
    TextView tv_shop_message_desc;
    private long message_id = 0;//消息ID

    @BindView(R.id.refresh_shop_message)
    SuperSwipeRefreshLayout swipeRefreshLayout;
    SwipeRefreshLayoutUtil swipeRefreshLayoutUtil;
    private int currentPage = Constants.PAGE_NUM;
    private int pageRows = 10;


    @Override
    public int getLayoutId() {
        return R.layout.ui_shop_message_list_layout;
    }

    @Override
    public void initView() {
        actionbar.setImgStatusBar(R.color.my_color_white);
        StatusBarUtils.StatusBarLightMode(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.addItemDecoration(new RecyclerItemDecoration(15, 1));
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void initData() {
        adapter = new ShopMessageListAdapter(null, this);
        recyclerView.setAdapter(adapter);
        getHomeMessages(false, 1, pageRows);
    }

    @Override
    public void initListener() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                MessageDto item = (MessageDto) baseQuickAdapter.getItem(position);
                startActivityShopMessage(item.getId());
            }
        });
        bindClickEvent(ll_shop_message_top, () -> {
            if (message_id > 0) {
                startActivityShopMessage(message_id);
            }
        });
        setScrollListener(pageRows);
    }

    /**
     * 启动商城快讯
     *
     * @param messageId 消息ID
     */
    private void startActivityShopMessage(long messageId) {
        Bundle bundle = new Bundle();
        bundle.putLong(ShopMessageActivity.MESSAGE_ID, messageId);
        gotoActivity(ShopMessageActivity.class, false, bundle);
    }

    /**
     * 滚动监听
     *
     * @param rows 每页加载行数
     */
    private void setScrollListener(int rows) {
        swipeRefreshLayoutUtil = new SwipeRefreshLayoutUtil();
        swipeRefreshLayout.setEnablePull(false);
        swipeRefreshLayoutUtil.setSwipeRefreshView(swipeRefreshLayout, new SwipeRefreshLayoutUtil.OnRefreshAndLoadMoreListener() {
            @Override
            public void onRefresh() {
                currentPage = Constants.PAGE_NUM;
                swipeRefreshLayoutUtil.setCanLoadMore(true);
                getHomeMessages(true, currentPage, rows);
            }

            @Override
            public void onLoadMore() {
                swipeRefreshLayoutUtil.setCanLoadMore(true);
                currentPage++;
                getHomeMessages(true, currentPage, rows);
            }
        });
    }

    /**
     * 获取资讯列表
     */
    private void getHomeMessages(boolean isScrollLoad, int currentPage, int rows) {
        if (!isScrollLoad) {
            dissLoadDialog();
        }
        DataManager.getInstance().getHomeMessages(new DefaultSingleObserver<MessageListDto>() {
            @Override
            public void onSuccess(MessageListDto object) {
                LogUtil.i(TAG, "--RxLog-Thread: onSuccess()");
                if (object != null && object.getList() != null) {
                    if (currentPage <= Constants.PAGE_NUM) {
                        setTopMessageView(object.getList().get(0));
                        object.getList().remove(object.getList().get(0));
                        adapter.setNewData(object.getList());
                    } else {
                        adapter.addData(object.getList());
                    }
                    swipeRefreshLayoutUtil.isMoreDate(currentPage, rows, object.getTotal());
                } else {
                    swipeRefreshLayoutUtil.isMoreDate(currentPage, rows, 0);
                    EmptyView emptyView = new EmptyView(ShopMessageListActivity.this);
                    emptyView.setTvEmptyTip("暂无资讯");
                    adapter.setEmptyView(emptyView);
                }
                dissLoadDialog();
            }

            @Override
            public void onError(Throwable throwable) {
                LogUtil.i(TAG, "--RxLog-Thread: onError() = ");
                dissLoadDialog();
            }
        }, currentPage, rows);
    }

    private void setTopMessageView(MessageDto item) {
        String imgUrl = BuildConfig.BASE_IMAGE_URL + item.getCmsMainImg();
        GlideUtils.getInstances().loadNormalImg(this, iv_shop_message, imgUrl, R.mipmap.img_default_1);
        message_id = item.getId();
        tv_shop_message_title.setText(item.getCmsTitle());
        tv_shop_message_time.setText(item.getCreateDate());
        tv_shop_message_desc.setText(item.getSimpleDesc());
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
