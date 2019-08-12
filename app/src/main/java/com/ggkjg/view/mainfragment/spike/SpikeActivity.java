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
import com.ggkjg.common.utils.TimeUtil;
import com.ggkjg.dto.SpikeDto;
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

    @BindView(R.id.viewpager_main)
    NoScrollViewPager viewpagerMain;
    private int currentPage = Constants.PAGE_NUM;

    @Override
    public void initListener() {

    }
   private int state;
    @Override
    public int getLayoutId() {
        return R.layout.ui_spike_layout;
    }

    //    private SpikeChooseTimeAdapter testAdapter;
    private TestAdapter testAdapter;
    private boolean isShow;
    private SpikeDto spikeDto;
    @Override
    public void initView() {
        actionbar.setImgStatusBar(R.color.my_color_white);
        actionbar.setTitle(R.string.spike);
        actionbar.setTitleColor(R.color.my_color_212121);
        spikeDto= (SpikeDto) getIntent().getSerializableExtra("data");
        state=getIntent().getIntExtra("state",1);
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
        autoScroll.setInitPos(state);
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
        datas.clear();
        int i=0;
        for(SpikeDto spikeDto:spikeDto.sedKillTimes){
            TimeDataDto timeDataDto = new TimeDataDto();
            long star=TimeUtil.getStringToDate(spikeDto.startTime);
            long end=TimeUtil.getStringToDate(spikeDto.endTime);
            long curr=System.currentTimeMillis();
            if(curr>end){
                timeDataDto.setState(2);
            }else if(curr<star){
                timeDataDto.setState(3);
            }else {
                timeDataDto.setState(1);
            }

            timeDataDto.setTime(TimeUtil.formatHoursTime(TimeUtil.getStringToDate(spikeDto.startTime)));
            timeDataDto.id=spikeDto.id;
            datas.add(timeDataDto);
            i++;
        }

        testAdapter.setDatas(datas);
        testAdapter.notifyDataSetChanged();
        initFragMents(state);
    }
    private String[] titles;
    private List<Fragment> list_productfragment;   //定义要装fragment的列表
    private FragmentViewPagerAdapter mainViewPagerAdapter;
    private void initFragMents(int poistion) {
        list_productfragment = new ArrayList<>();
       for (TimeDataDto dataDto:datas){
           SpikeFragment spikeFragment = new SpikeFragment();
           spikeFragment.setId(dataDto.id);
           spikeFragment.setState(dataDto.getState());
           list_productfragment.add(spikeFragment);
       }

        mainViewPagerAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), list_productfragment);
        viewpagerMain.setAdapter(mainViewPagerAdapter);
        viewpagerMain.setOffscreenPageLimit(datas.size() - 1);//设置缓存view 的个数
        viewpagerMain.setCurrentItem(poistion);

    }


}
