package com.ggkjg.view.mainfragment.spike;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.dto.TimeDataDto;
import com.ggkjg.view.adapter.FragmentViewPagerAdapter;
import com.ggkjg.view.adapter.TestAdapter;
import com.ggkjg.view.mainfragment.MangerVoucherFragment;
import com.ggkjg.view.mainfragment.VoucherFragment;
import com.ggkjg.view.widgets.NoScrollViewPager;
import com.ggkjg.view.widgets.autoview.ActionbarView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 秒杀专区
 */
public class MangerVoucherActivity extends BaseActivity {


    @BindView(R.id.custom_action_bar)
    ActionbarView customActionBar;
    @BindView(R.id.viewpager_main)
    NoScrollViewPager viewpagerMain;
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.line_send)
    View lineSend;
    @BindView(R.id.tv_user)
    TextView tvUser;
    @BindView(R.id.line_user)
    View lineUser;
    private int currentPage = Constants.PAGE_NUM;

    @Override
    public void initListener() {
        bindClickEvent(tvSend, () -> {
            selection(0, true);
        });
        bindClickEvent(tvUser, () -> {
            selection(1, true);
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.ui_manger_voucher_layout;
    }

    //    private SpikeChooseTimeAdapter testAdapter;
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
                selection(position, false);
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


    public void selection(int poition, boolean isShow) {
        switch (poition) {
            case 0:
                tvSend.setTextColor(getResources().getColor(R.color.my_color_blue));
                tvUser.setTextColor(getResources().getColor(R.color.my_color_333333));
                lineSend.setVisibility(View.VISIBLE);
                lineUser.setVisibility(View.INVISIBLE);
                if (isShow) {
                    viewpagerMain.setCurrentItem(0, false);
                }

                break;
            case 1:
                tvSend.setTextColor(getResources().getColor(R.color.my_color_333333));
                tvUser.setTextColor(getResources().getColor(R.color.my_color_blue));
                lineUser.setVisibility(View.VISIBLE);
                lineSend.setVisibility(View.INVISIBLE);

                if (isShow) {
                    viewpagerMain.setCurrentItem(1, false);
                }
                break;

        }
    }

    private List<Fragment> list_productfragment;   //定义要装fragment的列表
    private FragmentViewPagerAdapter mainViewPagerAdapter;

    private void initFragMents(int poistion) {
        list_productfragment = new ArrayList<>();
        VoucherFragment spikeFragment = new VoucherFragment();
        list_productfragment.add(spikeFragment);
        MangerVoucherFragment spikeFragment1 = new MangerVoucherFragment();
        list_productfragment.add(spikeFragment1);


        mainViewPagerAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), list_productfragment);
        viewpagerMain.setAdapter(mainViewPagerAdapter);
        viewpagerMain.setOffscreenPageLimit(1);//设置缓存view 的个数
        viewpagerMain.setCurrentItem(poistion);

    }



}
