package com.ggkjg.view.mainfragment.personalcenter.wallet;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.LogUtil;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.common.utils.SwipeRefreshLayoutUtil;
import com.ggkjg.common.utils.ToastUtil;
import com.ggkjg.dto.AccountBalanceDto;
import com.ggkjg.dto.MemberLevelDto;
import com.ggkjg.dto.MyccRecordDto;
import com.ggkjg.http.error.ApiException;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.response.HttpResult;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.adapter.MYCCRecordAdapter;
import com.ggkjg.view.adapter.PopWalletFilterAdapter;
import com.ggkjg.view.adapter.WalletAdapter;
import com.ggkjg.view.widgets.SuperSwipeRefreshLayout;
import com.ggkjg.view.widgets.autoview.EmptyView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;


/**
 * 个人中心 --> 我的钱包
 */
public class WalletActivity extends BaseActivity {
    private static final String TAG = WalletActivity.class.getSimpleName();
    @BindView(R.id.tv_gold_coin)
    TextView tv_gold_coin;
    @BindView(R.id.ll_recharge)
    LinearLayout ll_recharge;
    @BindView(R.id.ll_change)
    LinearLayout ll_change;
    @BindView(R.id.btn_filter)
    TextView btn_filter;
    @BindView(R.id.recy_wallet)
    RecyclerView recyclerView;
    @BindView(R.id.ll_filter)
    LinearLayout ll_filter;
    @BindView(R.id.refresh)
    SuperSwipeRefreshLayout swipeRefreshLayout;
    SwipeRefreshLayoutUtil mSwipeRefreshLayoutUtil;
    WalletAdapter mAdapter;
    int mCurrentPage = Constants.PAGE_NUM;
    String type = "";
    View contentView;
    PopupWindow window;

    @Override
    public int getLayoutId() {
        return R.layout.my_wallet;
    }

    @Override
    public void initView() {
        actionbar.setImgStatusBar(R.color.my_color_white);
        StatusBarUtils.StatusBarLightMode(this);
        initAdapter();
    }

    @Override
    public void initData() {
        loadData(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWalletBalance();
    }

    @Override
    public void initListener() {
        bindClickEvent(ll_recharge, () -> {
            gotoActivity(WalletRechargeActivity.class);
        });
        bindClickEvent(ll_change, () -> {
            gotoActivity(WalletTransferActivity.class);
        });
        bindClickEvent(btn_filter, () -> {
            filterView();
        });
        setListener();
    }

    private void getWalletBalance() {
        showLoadDialog();
        DataManager.getInstance().findAccountBalance(new DefaultSingleObserver<AccountBalanceDto>() {
            @Override
            public void onSuccess(AccountBalanceDto object) {
                LogUtil.i(TAG, "--RxLog-Thread: onSuccess() = ");
                if (object != null) {
                    tv_gold_coin.setText(object.getAvailAmount());
                }
                dissLoadDialog();
            }

            @Override
            public void onError(Throwable throwable) {
                LogUtil.i(TAG, "--RxLog-Thread: onError() = ");
                dissLoadDialog();
            }
        }, 1);
    }

    private void filterView() {
        if (contentView == null) {
            // 用于PopupWindow的View
            contentView = LayoutInflater.from(this).inflate(R.layout.pop_wallet_filter_layout, null, false);
            // 创建PopupWindow对象，其中：
            // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
            // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
            recyclerView = (RecyclerView) contentView.findViewById(R.id.recy);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            List<MemberLevelDto> filterList = new ArrayList<>();
            MemberLevelDto item = new MemberLevelDto();
            item.setMemberLevel(0);
            item.setLevelName("全部");
            filterList.add(item);
            MemberLevelDto item1 = new MemberLevelDto();
            item1.setMemberLevel(2);
            item1.setLevelName("支出");
            filterList.add(item1);
            MemberLevelDto item2 = new MemberLevelDto();
            item2.setMemberLevel(1);
            item2.setLevelName("收入");
            filterList.add(item2);
            final PopWalletFilterAdapter filterAdapter = new PopWalletFilterAdapter(filterList);
            recyclerView.setAdapter(filterAdapter);
            filterAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    window.dismiss();
                    mCurrentPage = Constants.PAGE_NUM;
                    MemberLevelDto memberLevelDto = filterAdapter.getItem(position);
                    if (memberLevelDto.getMemberLevel() == 0) {
                        type = "";
                    } else {
                        type = String.valueOf(memberLevelDto.getMemberLevel());
                    }
                    loadData(true);
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

        window.showAsDropDown(ll_filter, 0, 0);
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

        DataManager.getInstance().findGD(new DefaultSingleObserver<HttpResult<MyccRecordDto>>() {
            @Override
            public void onSuccess(HttpResult<MyccRecordDto> httpResult) {
                dissLoadDialog();
                if (httpResult != null && httpResult.getData() != null && httpResult.getData().getFundFlow() != null) {
                    if (Constants.PAGE_NUM == mCurrentPage) {
                        mAdapter.setNewData(httpResult.getData().getFundFlow().getRows());
                        if (httpResult.getData().getFundFlow().getRows() == null || httpResult.getData().getFundFlow().getRows().size() == 0) {
                            mAdapter.setEmptyView(new EmptyView(WalletActivity.this));
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
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new WalletAdapter();
        recyclerView.setAdapter(mAdapter);
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
}
