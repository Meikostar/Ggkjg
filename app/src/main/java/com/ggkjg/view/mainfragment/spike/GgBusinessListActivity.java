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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.common.Constants;
import com.ggkjg.common.type.TransferType;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.common.utils.TextUtil;
import com.ggkjg.common.utils.ToastUtil;
import com.ggkjg.dto.BusinessDto;
import com.ggkjg.dto.BusinessListDto;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.adapter.BusinesstListAdapter;
import com.ggkjg.view.adapter.BussListAdapter;
import com.ggkjg.view.adapter.LoopViewAdapter;
import com.ggkjg.view.adapter.LoopViewListAdapter;
import com.ggkjg.view.widgets.ClearEditText;
import com.ggkjg.view.widgets.RegularListView;
import com.ggkjg.view.widgets.autoview.ActionbarView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GgBusinessListActivity extends BaseActivity {
    @BindView(R.id.list_view)
    RegularListView recyclerView;
    BussListAdapter mAdapter;

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
    @BindView(R.id.rl_bg)
    RelativeLayout rl_bg;
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.ll_bg)
    LinearLayout ll_bg;


    private int currentPage = Constants.PAGE_NUM;
    private String search;
    private String id;
    private String url;
    @Override
    public void initListener() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               if(TextUtil.isNotEmpty(charSequence.toString())){
                   ll_bg.setVisibility(View.GONE);
                 search=charSequence.toString();
                }else {
                   ll_bg.setVisibility(View.VISIBLE);
                   search="";
                   loadData();
               }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        actionbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtil.isNotEmpty(search)){
                    loadData();
                }else {
                    ToastUtil.showToast("请输入搜索内容");
                }
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.ui_gg_business_list_layout;
    }

    @Override
    public void initView() {

        id=getIntent().getStringExtra("id");
        url=getIntent().getStringExtra("url");
        if(TextUtil.isNotEmpty(id)){
            rl_bg.setVisibility(View.VISIBLE);
            BusinessListDto businessListDto = new BusinessListDto();
            businessListDto.imgUrl=url;
            data.add(businessListDto);
            setAds(data);
        }else {
            rl_bg.setVisibility(View.GONE);
        }
        initAdapter();


    }
   private List<BusinessListDto> data =new ArrayList<>();
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



        mAdapter = new BussListAdapter(this);

        recyclerView.setAdapter(mAdapter);
    }

    private void loadData() {
        showLoadDialog();
        HashMap<String, String> map = new HashMap<>();

        if(TextUtil.isNotEmpty(id)){
            map.put("commercialCollegeTypeId", id);
        }
        if(TextUtil.isNotEmpty(search)){
            map.put("cmsContent", search);
        }

        DataManager.getInstance().findCommercialCollegeList(new DefaultSingleObserver<BusinessListDto>() {
            @Override
            public void onSuccess(BusinessListDto helpDto) {
                dissLoadDialog();
                if (helpDto != null && helpDto.storeAdPositionPOList != null) {
                    if (helpDto.storeAdPositionPOList.size() > 0 && helpDto.storeAdPositionPOList.get(0).adsList != null) {

                    }
                }
                if (helpDto != null && helpDto.commercialCollegeInfoPage != null && helpDto.commercialCollegeInfoPage.records != null) {
                    mAdapter.setData(helpDto.commercialCollegeInfoPage.records);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();

            }
        },map);
    }



}
