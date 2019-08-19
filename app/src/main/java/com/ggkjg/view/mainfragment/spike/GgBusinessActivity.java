package com.ggkjg.view.mainfragment.spike;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.base.BuildConfig;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.GlideUtils;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.dto.BusinessDto;
import com.ggkjg.dto.HelpDto;
import com.ggkjg.dto.HomeAdsDto;
import com.ggkjg.dto.SlidersDto;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.adapter.BusinessAdapter;
import com.ggkjg.view.adapter.LoopViewAdapter;
import com.ggkjg.view.adapter.LoopViewPagerAdapter;
import com.ggkjg.view.widgets.ClearEditText;
import com.ggkjg.view.widgets.RecyclerItemDecoration;
import com.ggkjg.view.widgets.autoview.ActionbarView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GgBusinessActivity extends BaseActivity {
    @BindView(R.id.recy_view)
    RecyclerView recyclerView;
    BusinessAdapter mAdapter;
    @BindView(R.id.custom_action_bar)
    ActionbarView customActionBar;
    @BindView(R.id.vp_container)
    ViewPager vpContainer;
    @BindView(R.id.et_search)
    ClearEditText etSearch;
    @BindView(R.id.home_ll_indicators)
    LinearLayout homeLlIndicators;
    private int currentPage = Constants.PAGE_NUM;

    @Override
    public void initListener() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.ui_gg_business_layout;
    }

    @Override
    public void initView() {
        actionbar.setImgStatusBar(R.color.my_color_white);
        actionbar.setTitle("港港商学院");
        StatusBarUtils.StatusBarLightMode(this);
        initAdapter();
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
    /**
     * 设置广告位图片
     *
     * @param data
     */
    private void setAds(List<BusinessDto> data) {
        LoopViewAdapter loopViewPagerAdapter = new LoopViewAdapter(this, vpContainer,homeLlIndicators);
        vpContainer.setAdapter(loopViewPagerAdapter);
        loopViewPagerAdapter.setList(data);
        vpContainer.addOnPageChangeListener(loopViewPagerAdapter);
    }

    @Override
    public void initData() {
        loadData();
    }

    private void initAdapter() {
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(this, 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.addItemDecoration(new RecyclerItemDecoration(10, 2));
        recyclerView.setLayoutManager(gridLayoutManager2);

        mAdapter = new BusinessAdapter(null, this);
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
        DataManager.getInstance().findCommercialCollegeIndex(new DefaultSingleObserver<BusinessDto>() {
            @Override
            public void onSuccess(BusinessDto helpDto) {
                dissLoadDialog();
                if (helpDto != null&&helpDto.storeAdPositionPOList!=null) {
                    if(helpDto.storeAdPositionPOList.size()>0&&helpDto.storeAdPositionPOList.get(0).adsList!=null){
                        setAds(helpDto.storeAdPositionPOList.get(0).adsList);
                    }
                }
                if (helpDto != null&&helpDto.commercialCollegeTypePage!=null&&helpDto.commercialCollegeTypePage.records!=null) {
                   mAdapter.setNewData(helpDto.commercialCollegeTypePage.records);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();

            }
        });
    }


}
