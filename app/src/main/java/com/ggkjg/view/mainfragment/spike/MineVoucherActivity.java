package com.ggkjg.view.mainfragment.spike;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.dto.TimeDataDto;
import com.ggkjg.view.adapter.FragmentViewPagerAdapter;
import com.ggkjg.view.adapter.TestAdapter;
import com.ggkjg.view.mainfragment.SpikeFragment;
import com.ggkjg.view.mainfragment.VoucherFragment;
import com.ggkjg.view.widgets.NoScrollViewPager;
import com.ggkjg.view.widgets.autoview.ActionbarView;
import com.ggkjg.view.widgets.autoview.AutoLocateHorizontalView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 秒杀专区
 */
public class MineVoucherActivity extends BaseActivity {


    @BindView(R.id.custom_action_bar)
    ActionbarView customActionBar;
    @BindView(R.id.tv_un_user)
    TextView tvUnUser;
    @BindView(R.id.tv_user)
    TextView tvUser;
    @BindView(R.id.tv_no_user)
    TextView tvNoUser;
    @BindView(R.id.viewpager_main)
    NoScrollViewPager viewpagerMain;
    private int currentPage = Constants.PAGE_NUM;

    @Override
    public void initListener() {
        bindClickEvent(tvUnUser,()->{
         selection(0,true);
        });
        bindClickEvent(tvUser,()->{
            selection(1,true);
        });
        bindClickEvent(tvNoUser,()->{
            selection(2,true);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.ui_voucher_layout;
    }

    private TestAdapter testAdapter;
    private boolean isShow;

    @Override
    public void initView() {
        actionbar.setImgStatusBar(R.color.my_color_white);
        actionbar.setTitle("我的港券");
        actionbar.setTitleColor(R.color.my_color_212121);
        viewpagerMain.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                selection(position,false);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        StatusBarUtils.StatusBarLightMode(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);



    }

    private List<TimeDataDto> datas = new ArrayList<>();

    @Override
    public void initData() {

        initFragMents(0);
    }



    public void selection(int poition,boolean isShow){
        switch (poition){
            case 0:
                tvUnUser.setTextColor(getResources().getColor(R.color.my_color_blue));
                tvUser.setTextColor(getResources().getColor(R.color.my_color_333333));
                tvNoUser.setTextColor(getResources().getColor(R.color.my_color_333333));
                if(isShow){
                    viewpagerMain.setCurrentItem(0,false);
                }

                break;
            case 1:
                tvUnUser.setTextColor(getResources().getColor(R.color.my_color_333333));
                tvUser.setTextColor(getResources().getColor(R.color.my_color_blue));
                tvNoUser.setTextColor(getResources().getColor(R.color.my_color_333333));

                if(isShow){
                    viewpagerMain.setCurrentItem(1,false);
                }
                break;
            case 2:
                tvUnUser.setTextColor(getResources().getColor(R.color.my_color_333333));
                tvUser.setTextColor(getResources().getColor(R.color.my_color_333333));
                tvNoUser.setTextColor(getResources().getColor(R.color.my_color_blue));
                if(isShow){
                    viewpagerMain.setCurrentItem(2,false);
                }

                break;
        }
    }
    private List<Fragment> list_productfragment;   //定义要装fragment的列表
    private FragmentViewPagerAdapter mainViewPagerAdapter;

    private void initFragMents(int poistion) {
        list_productfragment = new ArrayList<>();
        VoucherFragment spikeFragment = new VoucherFragment();
        spikeFragment.setConponStatus(0);
        list_productfragment.add(spikeFragment);
        VoucherFragment spikeFragment1 = new VoucherFragment();
        spikeFragment1.setConponStatus(2);
        list_productfragment.add(spikeFragment1);
        VoucherFragment spikeFragment2 = new VoucherFragment();
        spikeFragment2.setConponStatus(4);
        list_productfragment.add(spikeFragment2);

        mainViewPagerAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), list_productfragment);
        viewpagerMain.setAdapter(mainViewPagerAdapter);
        viewpagerMain.setOffscreenPageLimit(2);//设置缓存view 的个数
        viewpagerMain.setCurrentItem(poistion);

    }



}
