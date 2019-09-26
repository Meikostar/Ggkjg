package com.ggkjg.view.mainfragment.spike;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.common.utils.TextUtil;
import com.ggkjg.dto.BusinessDto;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.adapter.BusinessAdapter;
import com.ggkjg.view.adapter.LoopViewAdapter;
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
    ActionbarView  customActionBar;
    @BindView(R.id.vp_container)
    ViewPager      vpContainer;
    @BindView(R.id.home_ll_indicators)
    LinearLayout   homeLlIndicators;
    @BindView(R.id.rl_vp_container)
    RelativeLayout rlVpContainer;
    @BindView(R.id.rl_bg)
    RelativeLayout rlBg;
    private int currentPage = Constants.PAGE_NUM;

    @Override
    public void initListener() {
        rlBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(GgBusinessActivity.this, GgBusinessListActivity.class);
                   startActivity(intent);

            }
        });
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
        //        etSearch.addTextChangedListener(new TextWatcher() {
        //            @Override
        //            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        //
        //            }
        //
        //            @Override
        //            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        //
        //            }
        //
        //            @Override
        //            public void afterTextChanged(Editable editable) {
        //
        //            }
        //        });

    }

    /**
     * 设置广告位图片
     *
     * @param data
     */
    private void setAds(List<BusinessDto> data) {
        LoopViewAdapter loopViewPagerAdapter = new LoopViewAdapter(this, vpContainer, homeLlIndicators);
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
                if (helpDto != null && helpDto.banners != null) {
                    if (helpDto.banners!= null) {
                        setAds(helpDto.banners);
                    }
                }
                if (helpDto != null && helpDto.commercialCollegeTypes!= null) {
                    mAdapter.setNewData(helpDto.commercialCollegeTypes);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();

            }
        });
    }


}
