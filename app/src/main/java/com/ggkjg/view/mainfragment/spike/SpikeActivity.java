package com.ggkjg.view.mainfragment.spike;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.dto.TimeDataDto;
import com.ggkjg.view.adapter.FragmentViewPagerAdapter;
import com.ggkjg.view.adapter.HelpAdapter;
import com.ggkjg.view.adapter.TestAdapter;
import com.ggkjg.view.mainfragment.SpikeFragment;
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
public class SpikeActivity extends BaseActivity {

    HelpAdapter mAdapter;
    @BindView(R.id.custom_action_bar)
    ActionbarView customActionBar;
    @BindView(R.id.auto_scroll)
    AutoLocateHorizontalView autoScroll;
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
    @BindView(R.id.viewpager_main)
    NoScrollViewPager viewpagerMain;
    private int currentPage = Constants.PAGE_NUM;

    @Override
    public void initListener() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.ui_spike_layout;
    }

    //    private SpikeChooseTimeAdapter testAdapter;
    private TestAdapter testAdapter;
    private boolean isShow;
    @Override
    public void initView() {
        actionbar.setImgStatusBar(R.color.my_color_white);
        actionbar.setTitle(R.string.spike);
        actionbar.setTitleColor(R.color.my_color_212121);
        viewpagerMain.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
             autoScroll.moveToPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        StatusBarUtils.StatusBarLightMode(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        autoScroll.setHasFixedSize(true);
        autoScroll.setLayoutManager(linearLayoutManager);
        autoScroll.setOnSelectedPositionChangedListener(new AutoLocateHorizontalView.OnSelectedPositionChangedListener() {
            @Override
            public void selectedPositionChanged(int pos) {
                viewpagerMain.setCurrentItem(pos, false);


            }
        });

        testAdapter = new TestAdapter();
        autoScroll.setInitPos(5);
        autoScroll.setItemCount(5);
        autoScroll.setAdapter(testAdapter);
        testAdapter.setItemClick(new TestAdapter.ItemClickListener() {
            @Override
            public void itemClick(int poition, TimeDataDto data) {
                autoScroll.moveToPosition(poition);
            }
        });
    }

    private List<TimeDataDto> datas = new ArrayList<>();

    @Override
    public void initData() {
        TimeDataDto timeDataDto = new TimeDataDto();
        timeDataDto.setState(2);
        timeDataDto.setTime("08:00");
        TimeDataDto timeDataDto1 = new TimeDataDto();
        timeDataDto1.setState(2);
        timeDataDto1.setTime("09:00");
        TimeDataDto timeDataDto2 = new TimeDataDto();
        timeDataDto2.setState(2);
        timeDataDto2.setTime("10:00");
        TimeDataDto timeDataDto3 = new TimeDataDto();
        timeDataDto3.setState(1);
        timeDataDto3.setTime("11:00");
        TimeDataDto timeDataDto4 = new TimeDataDto();
        timeDataDto4.setState(3);
        timeDataDto4.setTime("12:00");
        TimeDataDto timeDataDto5 = new TimeDataDto();
        timeDataDto5.setState(3);
        timeDataDto5.setTime("13:00");
        TimeDataDto timeDataDto6 = new TimeDataDto();
        timeDataDto6.setState(3);
        timeDataDto6.setTime("14:00");
        TimeDataDto timeDataDto7 = new TimeDataDto();
        timeDataDto7.setState(3);
        timeDataDto7.setTime("15:00");
        TimeDataDto timeDataDto8 = new TimeDataDto();
        timeDataDto8.setState(3);
        timeDataDto8.setTime("16:00");
        TimeDataDto timeDataDto9 = new TimeDataDto();
        timeDataDto9.setState(3);
        timeDataDto9.setTime("17:00");
        datas.add(timeDataDto);
        datas.add(timeDataDto1);
        datas.add(timeDataDto2);
        datas.add(timeDataDto3);
        datas.add(timeDataDto4);
        datas.add(timeDataDto5);
        datas.add(timeDataDto6);
        datas.add(timeDataDto7);
        datas.add(timeDataDto8);
        datas.add(timeDataDto9);
        testAdapter.setDatas(datas);
        testAdapter.notifyDataSetChanged();
        initFragMents(0);
    }
    private String[] titles;
    private List<Fragment> list_productfragment;   //定义要装fragment的列表
    private FragmentViewPagerAdapter mainViewPagerAdapter;
    private void initFragMents(int poistion) {
        list_productfragment = new ArrayList<>();
       for (TimeDataDto dataDto:datas){
           SpikeFragment spikeFragment = new SpikeFragment();
           list_productfragment.add(spikeFragment);
       }

        mainViewPagerAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), list_productfragment);
        viewpagerMain.setAdapter(mainViewPagerAdapter);
        viewpagerMain.setOffscreenPageLimit(datas.size() - 1);//设置缓存view 的个数
        viewpagerMain.setCurrentItem(poistion);

    }


}
