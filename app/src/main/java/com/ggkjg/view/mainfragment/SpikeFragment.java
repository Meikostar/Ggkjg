package com.ggkjg.view.mainfragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ggkjg.R;
import com.ggkjg.base.BaseFragment;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.LogUtil;
import com.ggkjg.common.utils.SwipeRefreshLayoutUtil;
import com.ggkjg.common.utils.TimeUtil;
import com.ggkjg.dto.ShopCartDto;
import com.ggkjg.dto.SpikeDto;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.adapter.ShopSpikeAdapter;
import com.ggkjg.view.widgets.LoadingDialog;
import com.ggkjg.view.widgets.SuperSwipeRefreshLayout;
import com.ggkjg.view.widgets.autoview.EmptyView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 购物车
 * Created by dahai on 2019/01/18.
 */

public class SpikeFragment extends BaseFragment implements LoadingDialog.LoadingListener {
    private static final String TAG = SpikeFragment.class.getSimpleName();


    Unbinder unbinder;
    @BindView(R.id.common_recycler)
    RecyclerView recySpikeCart;
    @BindView(R.id.common_refresh)
    SuperSwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.tv_integer)
    TextView tvInteger;
    @BindView(R.id.tv_hour)
    TextView tvHour;
    @BindView(R.id.tv_minter)
    TextView tvMinter;
    @BindView(R.id.tv_second)
    TextView tvSecond;
    @BindView(R.id.tv_state)
    TextView tvState;

    @BindView(R.id.ll_time)
    LinearLayout llTime;
    @BindView(R.id.ll_more)
    LinearLayout llMore;
    private ShopSpikeAdapter shopSpikeAdapter;
    SwipeRefreshLayoutUtil swipeRefreshLayoutUtil;

    private Set<ShopCartDto> selectList = new HashSet<>();//被选中的资源
    public String id;

    public void setId(String id) {
        this.id = id;
    }
    public int state;

    public void setState(int state) {
        this.state = state;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_spike_layout;
    }

    private LoadingDialog loadingDialog;

    @Override
    protected void initView() {
        loadingDialog = LoadingDialog.show(getActivity());
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setCallbackListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recySpikeCart.setLayoutManager(linearLayoutManager);


    }

    private List<ShopCartDto> datas = new ArrayList<>();

    @Override
    protected void initData() {

        shopSpikeAdapter = new ShopSpikeAdapter(getActivity(),null);
        recySpikeCart.setAdapter(shopSpikeAdapter);
        shopSpikeAdapter.setState(state);
        findGoodsSedKill(true,currentPage,20);

    }

    /**
     * 滚动监听
     *
     * @param rows 每页加载行数
     */
    private int currentPage=1;
    private void setScrollListener(int rows) {
        swipeRefreshLayoutUtil = new SwipeRefreshLayoutUtil();
//        swipeRefreshLayout.setEnablePull(false);
        swipeRefreshLayoutUtil.setSwipeRefreshView(swipeRefreshLayout, new SwipeRefreshLayoutUtil.OnRefreshAndLoadMoreListener() {
            @Override
            public void onRefresh() {
                currentPage = Constants.PAGE_NUM;
                swipeRefreshLayoutUtil.setCanLoadMore(true);
                findGoodsSedKill(true,currentPage,rows);

            }

            @Override
            public void onLoadMore() {
                swipeRefreshLayoutUtil.setCanLoadMore(true);
                currentPage++;
                findGoodsSedKill(true,currentPage,rows);

            }
        });
    }

    /**
     * 秒杀
     */

    private CountDownTimer countDownTimer;
    private int loadinglevel = 0;
    private void findGoodsSedKill(boolean isScrollLoad, int currentPage, int rows) {
        DataManager.getInstance().findGoodsSedKill(new DefaultSingleObserver<SpikeDto>() {
            @Override
            public void onSuccess(SpikeDto objects) {
                if (objects != null && objects.page != null&&objects.page.records!=null&&objects.page.records.size()>0) {
                    objects.page.records.get(objects.page.records.size()-1).isLast=true;
                    if (currentPage <= Constants.PAGE_NUM) {
                        shopSpikeAdapter.setNewData(objects.page.records);
                    } else {
                        shopSpikeAdapter.addData(objects.page.records);
                    }

                    swipeRefreshLayoutUtil.isMoreDate(currentPage, rows, 0);

                }else {
                    swipeRefreshLayoutUtil.isMoreDate(currentPage, rows, 0);
                    EmptyView emptyView = new EmptyView(getActivity());
                    emptyView.setTvEmptyTip("暂无推荐商品");
                    shopSpikeAdapter.setEmptyView(emptyView);
                }
                if(objects!=null&&objects.sedKillTimes!=null){
                    SpikeDto object=null;
                    for(SpikeDto skd:objects.sedKillTimes){
                        if(id.equals(skd.id)){
                            object=skd;
                        }
                    }
                    if(object==null){
                        object=objects.sedKillTime;
                    }
                    if(countDownTimer!=null){
                        countDownTimer.cancel();
                    }
                    long times=0;
                    if(state==1){
                        llMore.setVisibility(View.VISIBLE);
                        tvInteger.setText("抢购中 先下单先得哦");
                        tvState.setText("距结束");
                        times= TimeUtil.getStringToDate(object.endTime)-System.currentTimeMillis();
                    }else if(state==3){
                        llMore.setVisibility(View.VISIBLE);
                        tvInteger.setText("即将开始 先下单先得哦");
                        tvState.setText("距开始");
                        times= TimeUtil.getStringToDate(object.startTime)-System.currentTimeMillis();
                    }else {
                        llMore.setVisibility(View.GONE);
                    }
                    if(state!=2){
                        countDownTimer = new CountDownTimer(times, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                String timeFormat = TimeUtil.getTimeFormat(millisUntilFinished/1000);
                                String[] split = timeFormat.split(",");
                                if(tvHour!=null){
                                    tvHour.setText(split[1]);
                                    tvMinter.setText(split[2]);
                                    tvSecond.setText(split[3]);
                                }

                            }
                            @Override
                            public void onFinish() {
                                if(tvHour!=null){
                                    tvHour.setText("00");
                                    tvMinter.setText("00");
                                    tvSecond.setText("00");
                                }

                            }
                        }.start();
                    }

                }
                if (isScrollLoad) {
                    dissLoadDialog();
                } else {
                    loadingDialog.setLoadinglevel(++loadinglevel);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                LogUtil.i(TAG, "--RxLog-Thread: onError() = ");
                if (isScrollLoad) {
                    dissLoadDialog();
                } else {
                    loadingDialog.setLoadinglevel(++loadinglevel);
                }
            }
        }, currentPage, rows,id);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
        }
    }

    @Override
    public void onResume() {
        selectList.clear();


        super.onResume();
    }
    @Override
    protected void dissLoadDialog() {
        super.dissLoadDialog();
        if (swipeRefreshLayout != null) {
            if(loadingDialog!=null&&swipeRefreshLayout!=null){
                loadingDialog.cancelDialog();
                swipeRefreshLayout.setRefreshing(false);
                swipeRefreshLayout.setLoadMore(false);
            }

        }
    }
    @Override
    protected void initListener() {

        shopSpikeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {


            }
        });

        setScrollListener(20);
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onLoadCompleted(int level) {
        if (level == 5) {
            loadingDialog.cancelDialog();
            loadinglevel = 0;
        }
    }
}
