package com.ggkjg.view.mainfragment.order;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.view.adapter.MyPagerAdapter;
import com.ggkjg.view.widgets.TabEntity;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的订单
 * Created by xld021 on 2017/4/14.
 */

public class MyOrderActivity extends BaseActivity {
    @BindView(R.id.common_tab_layout)
    CommonTabLayout commonTabLayout;

    private String[] mSubTitles = {"全部", "待付款", "待发货", "待收货", "待评价"};
    private MyOrderFragment[] fragments = new MyOrderFragment[5];
    private String fragments_name[] = {"allOrder", "orderPayment", "orderSend", "orderReceived", "orderDiscuss"};

    private ArrayList<CustomTabEntity> mSubTabEntities = new ArrayList<>();
    private int type;
    private int currentTabIndex = -1;

    private boolean isInit = false;

    @Override
    public void initListener() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.my_order_layout;
    }

    @Override
    public void initView() {
        actionbar.setImgStatusBar(R.color.my_color_white);
        StatusBarUtils.StatusBarLightMode(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            type = bundle.getInt(Constants.ORDER_TYPE);
        }
        initTab();
    }

    @Override
    public void initData() {
    }

//    private void initTab() {
//        for (int i = 0; i < mSubTitles.length; i++) {
//            mFragments.add(MyOrderFragment.newInstance(mHashMap.get(i)));
//        }
//        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
//        mAdapter.setTitles(mSubTitles);
//        mAdapter.setFragments(mFragments);
//        viewpager.setAdapter(mAdapter);
////        slidingTabLayout.setViewPager(viewpager);
//    }


    private void initTab() {
        for (int i = 0; i < mSubTitles.length; i++) {
            mSubTabEntities.add(new TabEntity(mSubTitles[i], R.mipmap.icon_arrow, R.mipmap.icon_arrow));
        }
        commonTabLayout.setTabData(mSubTabEntities);
        commonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switchFragment(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        commonTabLayout.setCurrentTab(type);
        switchFragment(type);

    }

    /**
     * 切换页面
     *
     * @param index
     */
    private void switchFragment(int index) {
        if (currentTabIndex == index) {
            return;
        }
        FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
        boolean isUpdate = true;
        if (fragments[index] == null) {
            isUpdate = false;
            fragments[index] = new MyOrderFragment();
            trx.add(R.id.content_layout, fragments[index], fragments_name[index]);
        }
        if (index == 0) {
            fragments[index].setType("");
        } else if (index == 1) {
            fragments[index].setType("1");
        } else if (index == 2) {
            fragments[index].setType("2");
        } else if (index == 3) {
            fragments[index].setType("3");
        } else if (index == 4) {
            fragments[index].setType("4");
        }
        if (currentTabIndex != -1) {
            trx.hide(fragments[currentTabIndex]);
        }
        trx.show(fragments[index]).commit();
        currentTabIndex = index;
        if (isUpdate) {
            fragments[index].loadData(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isInit && fragments[currentTabIndex] != null) {
            fragments[currentTabIndex].loadData(true);
        }
        isInit = true;
    }

}
