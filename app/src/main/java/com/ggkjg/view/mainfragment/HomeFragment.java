package com.ggkjg.view.mainfragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ggkjg.R;
import com.ggkjg.base.BaseFragment;
import com.ggkjg.base.BuildConfig;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.GlideUtils;
import com.ggkjg.common.utils.LogUtil;
import com.ggkjg.common.utils.SwipeRefreshLayoutUtil;
import com.ggkjg.common.utils.TextUtil;
import com.ggkjg.common.utils.TimeUtil;
import com.ggkjg.dto.GoodsPushDto;
import com.ggkjg.dto.HomeActiveIndexDto;
import com.ggkjg.dto.HomeAdsDto;
import com.ggkjg.dto.HomeCategoryIndexDto;
import com.ggkjg.dto.HomeDto;
import com.ggkjg.dto.HomeGoodsIndexDto;
import com.ggkjg.dto.MessageListDto;
import com.ggkjg.dto.SlidersDto;
import com.ggkjg.dto.SpikeDto;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.MainActivity;
import com.ggkjg.view.adapter.HomeAdapter;
import com.ggkjg.view.adapter.HomeCategoryIndexAdapter;
import com.ggkjg.view.adapter.HomeGoodShopAdapter;
import com.ggkjg.view.adapter.HomeGoodSpikeAdapter;
import com.ggkjg.view.adapter.HomeZoneAdapter;
import com.ggkjg.view.adapter.LoopViewPagerAdapter;
import com.ggkjg.view.mainfragment.message.ShopMessageListActivity;
import com.ggkjg.view.mainfragment.personalcenter.MessageCenterActivity;
import com.ggkjg.view.mainfragment.shop.CommodityDetailActivity;
import com.ggkjg.view.mainfragment.shop.SearchShopProduct;
import com.ggkjg.view.mainfragment.shop.ShopProductListActivity;
import com.ggkjg.view.mainfragment.spike.SpikeActivity;
import com.ggkjg.view.mainfragment.spike.VoucherProductListActivity;
import com.ggkjg.view.widgets.LoadingDialog;
import com.ggkjg.view.widgets.RecyclerItemDecoration;
import com.ggkjg.view.widgets.SuperSwipeRefreshLayout;
import com.ggkjg.view.widgets.autoview.EmptyView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 首页
 * Created by dahai on 2019/01/18.
 */

public class HomeFragment extends BaseFragment implements LoadingDialog.LoadingListener {
    private static final String TAG = HomeFragment.class.getSimpleName();
    @BindView(R.id.home_push_recy)
    RecyclerView recyclerView;
    HomeAdapter pushAdapter;
    @BindView(R.id.iv_top_message)
    ImageView iv_top_message;
    @BindView(R.id.iv_home_top_sweep)
    ImageView iv_top_qrCode;
    @BindView(R.id.top_search_view)
    LinearLayout top_search_view;
    @BindView(R.id.tv_marquee)
    TextView tv_marquee;

    @BindView(R.id.home_category_index)
    RecyclerView home_category_index;
    HomeCategoryIndexAdapter homeCategoryIndexAdapter;

    @BindView(R.id.rl_home_shop_message)
    RelativeLayout rl_home_shop_message;

    HomeGoodSpikeAdapter goodGabAdapter;
    HomeZoneAdapter zoneAdapter;
    @BindView(R.id.tv_integer)
    TextView tvInteger;
    @BindView(R.id.tv_hour)
    TextView tvHour;
    @BindView(R.id.tv_minter)
    TextView tvMinter;
    @BindView(R.id.tv_second)
    TextView tvSecond;
    @BindView(R.id.ll_time)
    LinearLayout llTime;
    @BindView(R.id.ll_more)
    LinearLayout llMore;
    @BindView(R.id.grid)
    GridView gridView;
    @BindView(R.id.grid_gg)
    GridView gridViewGg;

    private List<HomeActiveIndexDto> homeActiveIndexDtos;
    @BindView(R.id.iv_home_img1)
    ImageView iv_home_img1;
    @BindView(R.id.iv_home_img2)
    ImageView iv_home_img2;
    @BindView(R.id.iv_home_img3)
    ImageView iv_home_img3;
    @BindView(R.id.iv_home_img4)
    ImageView iv_home_img4;
    @BindView(R.id.iv_home_img5)
    ImageView iv_home_img5;
    @BindView(R.id.iv_home_img6)
    ImageView iv_home_img6;

    @BindView(R.id.home_vp_container)
    ViewPager homeVpContainer;
    Unbinder unbinder;
    @BindView(R.id.home_ll_indicators)
    LinearLayout homeLlIndicators;
    @BindView(R.id.ll_zone)
    LinearLayout ll_zone;

    @BindView(R.id.iv_ad_one)
    ImageView ivAdOne;
    @BindView(R.id.iv_ad_two)
    ImageView ivAdTwo;
    @BindView(R.id.iv_ad_three)
    ImageView ivAdThree;

    private LoadingDialog loadingDialog;
    private int loadinglevel = 0;

    @BindView(R.id.refresh_home)
    SuperSwipeRefreshLayout swipeRefreshLayout;
    SwipeRefreshLayoutUtil swipeRefreshLayoutUtil;
    private int currentPage = Constants.PAGE_NUM;
    private SlidersDto adOneSlider = null;
    private SlidersDto adTwoSlider = null;
    private SlidersDto adThreeSlider = null;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_layout;
    }

    @Override
    protected void initView() {
        loadingDialog = LoadingDialog.show(getActivity());
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setCallbackListener(this);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getActivity(), 4) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        home_category_index.setLayoutManager(gridLayoutManager1);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getActivity(), 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.addItemDecoration(new RecyclerItemDecoration(3, 2));
        recyclerView.setLayoutManager(gridLayoutManager2);
        LinearLayoutManager linearLayoutManagerShop = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

    }

    @Override
    protected void initData() {
        pushAdapter = new HomeAdapter(null, getActivity());
        recyclerView.setAdapter(pushAdapter);
        goodGabAdapter = new HomeGoodSpikeAdapter(getActivity());
        zoneAdapter = new HomeZoneAdapter(getActivity());
        homeCategoryIndexAdapter = new HomeCategoryIndexAdapter(null, getActivity());
        home_category_index.setAdapter(homeCategoryIndexAdapter);
        findGoodsSedKill();
        getHomeMessage();
        findCategoryIndex();
        findGoodsIndex();
        findActiveIndex();
        findAdsList();
        findQualityGoodsList(false, currentPage, Constants.PAGE_SIZE);
    }

    private void findAdsList() {
        DataManager.getInstance().findHomeAdsList(new DefaultSingleObserver<List<HomeAdsDto>>() {
            @Override
            public void onSuccess(List<HomeAdsDto> data) {
                setAds(data);
                loadingDialog.setLoadinglevel(++loadinglevel);
            }

            @Override
            public void onError(Throwable throwable) {
                loadingDialog.setLoadinglevel(++loadinglevel);
            }
        });
    }


    private void iniGridView(final List<HomeDto> list) {

        int length = 96;  //定义一个长度
        int size = 0;  //得到集合长度
        //获得屏幕分辨路
        DisplayMetrics dm = new DisplayMetrics();
        if(dm==null||getActivity()==null){
            return;
        }
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;

        int gridviewWidth = (int) (list.size() * (length + 5) * density);
        int itemWidth = (int) (length * density);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                gridviewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(10, 0, 0, 0);
        gridView.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
        gridView.setColumnWidth(itemWidth); // 设置列表项宽
        gridView.setHorizontalSpacing(10); // 设置列表项水平间距
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setNumColumns(list.size()); // 设置列数量=列表集合数
        goodGabAdapter.setData(list);
        gridView.setAdapter(goodGabAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



            }
        });
    }
    private void iniGridViewgg(final List<HomeDto> list) {

        int length = 96;  //定义一个长度
        int size = 0;  //得到集合长度
        //获得屏幕分辨路
        DisplayMetrics dm = new DisplayMetrics();
        if(dm==null||getActivity()==null){
            return;
        }
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;

        int gridviewWidth = (int) (list.size() * (length + 5) * density);
        int itemWidth = (int) (length * density);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                gridviewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(10, 0, 0, 0);
        gridViewGg.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
        gridViewGg.setColumnWidth(itemWidth); // 设置列表项宽
        gridViewGg.setHorizontalSpacing(10); // 设置列表项水平间距
        gridViewGg.setStretchMode(GridView.NO_STRETCH);
        gridViewGg.setNumColumns(list.size()); // 设置列数量=列表集合数
        zoneAdapter.setData(list);
        gridViewGg.setAdapter(zoneAdapter);

        gridViewGg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



            }
        });
    }
    /**
     * 设置广告位图片
     *
     * @param data
     */
    private void setAds(List<HomeAdsDto> data) {
        for (int i = 0; i < data.size(); i++) {
            List<SlidersDto> adsList = data.get(i).getAdsList();
            switch (i) {
                case 0:
                    LoopViewPagerAdapter loopViewPagerAdapter = new LoopViewPagerAdapter(getActivity(), homeVpContainer, homeLlIndicators);
                    homeVpContainer.setAdapter(loopViewPagerAdapter);
                    loopViewPagerAdapter.setList(adsList);
                    homeVpContainer.addOnPageChangeListener(loopViewPagerAdapter);
                    break;
                case 1:
                    if (adsList.size() > 0) {
                        adOneSlider = adsList.get(0);
                        String imgUrl = BuildConfig.BASE_IMAGE_URL + adsList.get(0).getImgUrl();
                        GlideUtils.getInstances().loadNormalImg(getActivity(), ivAdOne, imgUrl, R.mipmap.img_default_2);
                    }
                    break;
                case 2:
                    if (adsList.size() > 0) {
                        adTwoSlider = adsList.get(0);
                        String imgUrl = BuildConfig.BASE_IMAGE_URL + adsList.get(0).getImgUrl();
                        GlideUtils.getInstances().loadNormalImg(getActivity(), ivAdTwo, imgUrl, R.mipmap.img_default_2);
                    }
                    break;
                case 3:
                    if (adsList.size() > 0) {
                        adThreeSlider = adsList.get(0);
                        String imgUrl = BuildConfig.BASE_IMAGE_URL + adsList.get(0).getImgUrl();
                        GlideUtils.getInstances().loadNormalImg(getActivity(), ivAdThree, imgUrl, R.mipmap.img_default_2);
                    }
                    break;
            }
        }
    }

    @Override
    protected void initListener() {
        bindClickEvent(iv_home_img1, () -> {
            startActivityCommodityDetail(homeActiveIndexDtos.get(0).getCiteId());
        });
        bindClickEvent(iv_home_img2, () -> {
            startActivityCommodityDetail(homeActiveIndexDtos.get(1).getCiteId());
        });
        bindClickEvent(iv_home_img3, () -> {
            startActivityCommodityDetail(homeActiveIndexDtos.get(2).getCiteId());
        });
        bindClickEvent(iv_home_img4, () -> {
            startActivityCommodityDetail(homeActiveIndexDtos.get(3).getCiteId());
        });
        bindClickEvent(iv_home_img5, () -> {
            startActivityCommodityDetail(homeActiveIndexDtos.get(4).getCiteId());
        });
        bindClickEvent(ivAdOne, () -> {
            adClick(adOneSlider);
        });
        bindClickEvent(ivAdTwo, () -> {
            adClick(adTwoSlider);
        });
        bindClickEvent(ivAdThree, () -> {
            adClick(adThreeSlider);
        });

        bindClickEvent(llMore,() -> {
            Intent intent = new Intent(getActivity(),SpikeActivity.class);
            intent.putExtra("data",spikeDto);
            intent.putExtra("state",state);
            startActivity(intent);

        });
        bindClickEvent(ll_zone,() -> {
            Intent intent = new Intent(getActivity(), VoucherProductListActivity.class);
            startActivity(intent);

        });


//        goodShopAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                HomeGoodsIndexDto item = (HomeGoodsIndexDto) adapter.getItem(position);
//                startActivityCommodityDetail(item.getCiteId());
//            }
//        });
        homeCategoryIndexAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                HomeCategoryIndexDto item = (HomeCategoryIndexDto) adapter.getItem(position);
                startActivityProductList(item.getCiteId());
            }
        });
        bindClickEvent(top_search_view, () -> {
            gotoActivity(SearchShopProduct.class);
        });
        bindClickEvent(rl_home_shop_message, () -> {
            gotoActivity(ShopMessageListActivity.class);
        });
        bindClickEvent(iv_top_qrCode, () -> {
            Intent intent = new Intent();
            intent.setAction(MainActivity.ACTION_HOME_CAPTURE_CODE);
            getActivity().sendBroadcast(intent);
        }, 2500);
        bindClickEvent(iv_top_message, () -> {
            gotoActivity(MessageCenterActivity.class);
        });
        setScrollListener(Constants.PAGE_SIZE);

    }

    /**
     * 其他广告点击事件的操作
     */
    private void adClick(SlidersDto sDto) {
        if (sDto != null) {
            if (!TextUtils.isEmpty(sDto.getClickType())) {
                switch (sDto.getClickType()) {
                    case "1":
                        if (!TextUtils.isEmpty(sDto.getClickUrl())) {
                            startActivityProductList(Integer.valueOf(sDto.getClickUrl()));
                        }
                        break;
                    case "2":
                        if (!TextUtils.isEmpty(sDto.getClickUrl())) {
                            startActivityCommodityDetail(Long.valueOf(sDto.getClickUrl()));
                        }
                        break;
                    default:
                        break;
                }

            }
        }
    }

    /**
     * 启动商品详细
     *
     * @param product_id 商品ID
     */
    private void startActivityCommodityDetail(long product_id) {
        Bundle bundle = new Bundle();
        bundle.putLong(CommodityDetailActivity.PRODUCT_ID, product_id);
        gotoActivity(CommodityDetailActivity.class, false, bundle);
    }

    /**
     * 启动产品列表
     *
     * @param product_type 商品类型
     */
    private void startActivityProductList(int product_type) {
        Bundle bundle = new Bundle();
        bundle.putInt(ShopProductListActivity.PRODUCT_TYPE, product_type);
        gotoActivity(ShopProductListActivity.class, false, bundle);
    }

    /**
     * 滚动监听
     *
     * @param rows 每页加载行数
     */
    private void setScrollListener(int rows) {
        swipeRefreshLayoutUtil = new SwipeRefreshLayoutUtil();
//        swipeRefreshLayout.setEnablePull(false);
        swipeRefreshLayoutUtil.setSwipeRefreshView(swipeRefreshLayout, new SwipeRefreshLayoutUtil.OnRefreshAndLoadMoreListener() {
            @Override
            public void onRefresh() {
                currentPage = Constants.PAGE_NUM;
                swipeRefreshLayoutUtil.setCanLoadMore(true);
                findGoodsSedKill();
                findCategoryIndex();
                findGoodsIndex();
                findActiveIndex();
                findAdsList();
                findQualityGoodsList(true, currentPage, rows);
            }

            @Override
            public void onLoadMore() {
                swipeRefreshLayoutUtil.setCanLoadMore(true);
                currentPage++;
                findGoodsSedKill();
                findCategoryIndex();
                findGoodsIndex();
                findActiveIndex();
                findAdsList();
                findQualityGoodsList(true, currentPage, rows);
            }
        });
    }

    /**
     * 获取主页资讯滚动字符
     */
    private void getHomeMessage() {
        DataManager.getInstance().getHomeMessages(new DefaultSingleObserver<MessageListDto>() {
            @Override
            public void onSuccess(MessageListDto object) {
                LogUtil.i(TAG, "--RxLog-Thread: onSuccess()");
                if (object.getList() != null && !object.getList().isEmpty()) {
//                    tv_marquee.setText(object.getList().get(0).getCmsTitle());
//                    tv_marquee.setFocusable(true);
//                    tv_marquee.requestFocus();
                }
                loadingDialog.setLoadinglevel(++loadinglevel);
            }

            @Override
            public void onError(Throwable throwable) {
                LogUtil.i(TAG, "--RxLog-Thread: onError() = ");
                loadingDialog.setLoadinglevel(++loadinglevel);
            }
        }, 1, 1);
    }

    /**
     * 获取首页分类
     */
    private void findCategoryIndex() {
        DataManager.getInstance().findCategoryIndex(new DefaultSingleObserver<List<HomeCategoryIndexDto>>() {
            @Override
            public void onSuccess(List<HomeCategoryIndexDto> object) {
                LogUtil.i(TAG, "--RxLog-Thread: onSuccess()");
                homeCategoryIndexAdapter.setNewData(object);
                loadingDialog.setLoadinglevel(++loadinglevel);
            }

            @Override
            public void onError(Throwable throwable) {
                LogUtil.i(TAG, "--RxLog-Thread: onError() = ");
                loadingDialog.setLoadinglevel(++loadinglevel);
            }
        });
    }

    /**
     * 获取精选好货
     */
    private void findGoodsIndex() {
        DataManager.getInstance().findGoodsIndex(new DefaultSingleObserver<HomeDto>() {
            @Override
            public void onSuccess(HomeDto object) {
                if(object!=null&&object.conponList!=null){
                    iniGridViewgg(object.conponList);
                }
                if(object!=null&&object.goodsSedKill!=null&&object.goodsSedKill.records!=null){
                    if(object.goodsSedKill.records.size()==0){
//                        gridView.setVisibility(View.GONE);
                    }else {
                        iniGridView(object.goodsSedKill.records);
                        gridView.setVisibility(View.VISIBLE);
                    }

                }else {
//                    gridView.setVisibility(View.GONE);
                }
                if(object!=null&&object.sedKillTime!=null){
                  if(TextUtil.isNotEmpty(object.sedKillTime.startTime)){
                      String hour = TimeUtil.formatHourTime(TimeUtil.getStringToDate(object.sedKillTime.startTime));
                      tvInteger.setText(hour+"点场");
                  }

                  if(TextUtil.isNotEmpty(object.sedKillTime.endTime)){
                        long time = TimeUtil.getStringToDate(object.sedKillTime.endTime);
                        long current = System.currentTimeMillis();
                        long times=0;
                        if(time>current){
                            times=time-current;
                        }else{
                            times=current-time;
                        }
                        if(countDownTimer!=null){
                            countDownTimer.cancel();
                        }

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
                                findGoodsIndex();
                                if(tvHour!=null){
                                    tvHour.setText("00");
                                    tvMinter.setText("00");
                                    tvSecond.setText("00");
                                }

                            }
                        }.start();
                    }
                }

                LogUtil.i(TAG, "--RxLog-Thread: onSuccess()");
//                goodShopAdapter.setNewData(object);
                loadingDialog.setLoadinglevel(++loadinglevel);
            }

            @Override
            public void onError(Throwable throwable) {
                LogUtil.i(TAG, "--RxLog-Thread: onError() = ");
                loadingDialog.setLoadinglevel(++loadinglevel);
            }
        });
    }
    private SpikeDto spikeDto;
    private int state=1;
    /**
     * 秒杀专区
     */
    private void findGoodsSedKill() {
        DataManager.getInstance().findGoodsSedKill(new DefaultSingleObserver<SpikeDto>() {
            @Override
            public void onSuccess(SpikeDto object) {
                if(object!=null&&object.sedKillTimes!=null){
                    spikeDto=object;
                    int i=0;
                    for(SpikeDto skd:object.sedKillTimes){
                        String star = TimeUtil.formatHourTime(TimeUtil.getStringToDate(skd.startTime));
                        String end = TimeUtil.formatHourTime(TimeUtil.getStringToDate(skd.endTime));
                        String cur = TimeUtil.formatHourTime(System.currentTimeMillis());
                        if(Integer.valueOf(star)<=Integer.valueOf(cur)&&Integer.valueOf(cur)<Integer.valueOf(end)){
                            state=i;

                        }
                        i++;
                    }
                }

            }

            @Override
            public void onError(Throwable throwable) {
                LogUtil.i(TAG, "--RxLog-Thread: onError() = ");
                loadingDialog.setLoadinglevel(++loadinglevel);
            }
        });
    }


    private CountDownTimer countDownTimer;
    /**
     * 获取首页活动商品
     */
    private void findActiveIndex() {
        DataManager.getInstance().findActiveIndex(new DefaultSingleObserver<List<HomeActiveIndexDto>>() {
            @Override
            public void onSuccess(List<HomeActiveIndexDto> object) {
                homeActiveIndexDtos = object;
                LogUtil.i(TAG, "--RxLog-Thread: onSuccess()");
                if (object != null && !object.isEmpty()) {
                    for (int i = 0; i < object.size(); i++) {
                        String imgUrl = BuildConfig.BASE_IMAGE_URL + object.get(i).getIcon();
                        switch (i) {
                            case 0:
                                GlideUtils.getInstances().loadNormalImg(getActivity(), iv_home_img1, imgUrl, R.mipmap.img_default_6);
                                break;
                            case 1:
                                GlideUtils.getInstances().loadNormalImg(getActivity(), iv_home_img2, imgUrl, R.mipmap.img_default_6);
                                break;
                            case 2:
                                GlideUtils.getInstances().loadNormalImg(getActivity(), iv_home_img3, imgUrl, R.mipmap.img_default_6);
                                break;
                            case 3:
                                GlideUtils.getInstances().loadNormalImg(getActivity(), iv_home_img4, imgUrl, R.mipmap.img_default_6);
                                break;
                            case 4:
                                GlideUtils.getInstances().loadNormalImg(getActivity(), iv_home_img5, imgUrl, R.mipmap.img_default_6);
                                break;
                            case 5:
                                GlideUtils.getInstances().loadNormalImg(getActivity(), iv_home_img6, imgUrl, R.mipmap.img_default_6);
                                break;
                        }
                    }
                }
                loadingDialog.setLoadinglevel(++loadinglevel);
            }

            @Override
            public void onError(Throwable throwable) {
                LogUtil.i(TAG, "--RxLog-Thread: onError() = ");
                loadingDialog.setLoadinglevel(++loadinglevel);
            }
        });
    }


    /**
     * 获取精品推荐
     */
    private void findQualityGoodsList(boolean isScrollLoad, int currentPage, int rows) {
        DataManager.getInstance().findQualityList(new DefaultSingleObserver<GoodsPushDto>() {
            @Override
            public void onSuccess(GoodsPushDto object) {
                LogUtil.i(TAG, "--RxLog-Thread: onSuccess()");
                if (object != null && object.getRows() != null && !object.getRows().isEmpty()) {
                    if (currentPage <= Constants.PAGE_NUM) {
                        pushAdapter.setNewData(object.getRows());
                    } else {
                        pushAdapter.addData(object.getRows());
                    }
                    swipeRefreshLayoutUtil.isMoreDate(currentPage, rows, object.getTotal());
                } else {
                    swipeRefreshLayoutUtil.isMoreDate(currentPage, rows, 0);
                    EmptyView emptyView = new EmptyView(getActivity());
                    emptyView.setTvEmptyTip("暂无推荐商品");
                    pushAdapter.setEmptyView(emptyView);
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
        }, currentPage, rows);
    }

    @Override
    public void onLoadCompleted(int level) {
        LogUtil.i(TAG, "-- onLoadCompleted level=" + level);
        if (level == 5) {
            loadingDialog.cancelDialog();
            loadinglevel = 0;
        }
    }

    @Override
    protected void dissLoadDialog() {
        super.dissLoadDialog();
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
            swipeRefreshLayout.setLoadMore(false);
        }
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
}
