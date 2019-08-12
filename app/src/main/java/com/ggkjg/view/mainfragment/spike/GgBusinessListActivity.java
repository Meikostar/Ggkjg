package com.ggkjg.view.mainfragment.spike;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.common.utils.TextUtil;
import com.ggkjg.dto.BusinessDto;
import com.ggkjg.dto.BusinessListDto;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.adapter.BusinesstListAdapter;
import com.ggkjg.view.adapter.LoopViewAdapter;
import com.ggkjg.view.adapter.LoopViewListAdapter;
import com.ggkjg.view.widgets.ClearEditText;
import com.ggkjg.view.widgets.autoview.ActionbarView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GgBusinessListActivity extends BaseActivity {
    @BindView(R.id.recy_view)
    RecyclerView recyclerView;
    BusinesstListAdapter mAdapter;
    @BindView(R.id.custom_action_bar)
    ActionbarView customActionBar;
    @BindView(R.id.vp_container)
    ViewPager vpContainer;
    @BindView(R.id.et_search)
    ClearEditText etSearch;
    @BindView(R.id.home_ll_indicators)
    LinearLayout homeLlIndicators;
    @BindView(R.id.acb_status_bar)
    ImageView acbStatusBar;
    @BindView(R.id.actionbar_back)
    ImageView actionbarBack;
    @BindView(R.id.rl_vp_container)
    RelativeLayout rlVpContainer;
    private int currentPage = Constants.PAGE_NUM;
    private String search;
    @Override
    public void initListener() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               if(TextUtil.isNotEmpty(charSequence.toString())){
                 search=charSequence.toString();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.ui_gg_business_list_layout;
    }

    @Override
    public void initView() {
        actionbar.setImgStatusBar(R.color.my_color_white);
        StatusBarUtils.StatusBarLightMode(this);
        initAdapter();


    }

    /**
     * 设置广告位图片
     *
     * @param data
     */
    private void setAds(List<BusinessListDto> data) {
        LoopViewListAdapter loopViewPagerAdapter = new LoopViewListAdapter(this, vpContainer, homeLlIndicators);
        vpContainer.setAdapter(loopViewPagerAdapter);
        loopViewPagerAdapter.setList(data);
        vpContainer.addOnPageChangeListener(loopViewPagerAdapter);
    }

    @Override
    public void initData() {
        loadData();
    }

    private void initAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        recyclerView.setLayoutManager(linearLayoutManager);

        mAdapter = new BusinesstListAdapter(null);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                mAdapter.notifyDataSetChanged();
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    private void loadData() {
        showLoadDialog();
        DataManager.getInstance().findCommercialCollegeList(new DefaultSingleObserver<BusinessListDto>() {
            @Override
            public void onSuccess(BusinessListDto helpDto) {
                dissLoadDialog();
                if (helpDto != null && helpDto.storeAdPositionPOList != null) {
                    if (helpDto.storeAdPositionPOList.size() > 0 && helpDto.storeAdPositionPOList.get(0).adsList != null) {
                        setAds(helpDto.storeAdPositionPOList.get(0).adsList);
                    }
                }
                if (helpDto != null && helpDto.commercialCollegeInfoPage != null && helpDto.commercialCollegeInfoPage.records != null) {
                    mAdapter.setNewData(helpDto.commercialCollegeInfoPage.records);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();

            }
        });
    }



}
