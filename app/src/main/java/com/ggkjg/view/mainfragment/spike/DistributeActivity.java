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
import com.ggkjg.common.utils.TextUtil;
import com.ggkjg.common.utils.ToastUtil;
import com.ggkjg.dto.TimeDataDto;
import com.ggkjg.dto.VoucherDto;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.adapter.FragmentViewPagerAdapter;
import com.ggkjg.view.mainfragment.DistributeFragment;
import com.ggkjg.view.widgets.ClearEditText;
import com.ggkjg.view.widgets.MCheckBox;
import com.ggkjg.view.widgets.NoScrollViewPager;
import com.ggkjg.view.widgets.autoview.ActionbarView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 派发
 */
public class DistributeActivity extends BaseActivity implements DistributeFragment.DisFragmentListener {


    @BindView(R.id.custom_action_bar)
    ActionbarView customActionBar;
    @BindView(R.id.et_search)
    ClearEditText etSearch;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.line_send)
    View lineSend;
    @BindView(R.id.tv_person)
    TextView tvPerson;
    @BindView(R.id.line_user)
    View lineUser;
    @BindView(R.id.viewpager_main)
    NoScrollViewPager viewpagerMain;
    @BindView(R.id.mcb_chos)
    MCheckBox mcbChoose;
    @BindView(R.id.tv_send)
    TextView tvSend;
    private int currentPage = Constants.PAGE_NUM;

    private String conponBaseId;
    private String ids="";
    @Override
    public void initListener() {
        bindClickEvent(tvTime, () -> {
            selection(0, true);
        });
        bindClickEvent(tvPerson, () -> {
            selection(1, true);
        });
        bindClickEvent(mcbChoose, () -> {
            if(poi==0){
                if(mcbChoose.isCheck()){
                    spikeFragment.setChooseAll(0);
                }else {
                    spikeFragment.setChooseAll(1);
                }

            }else {
                if(mcbChoose.isCheck()){
                    spikeFragment1.setChooseAll(0);
                }else {
                    spikeFragment1.setChooseAll(1);
                }

            }
            if(mcbChoose.isCheck()){
                mcbChoose.setChecked(false);
            }else {
                mcbChoose.setChecked(true);
            }
        });

        bindClickEvent(tvSend, () -> {
            if(poi==0){
                 ids = spikeFragment.getIds();
            }else {
                 ids = spikeFragment1.getIds();
            }
            if(TextUtil.isEmpty(ids)){
                ToastUtil.showToast("你还未选择派发成员");
                return;
            }

        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.ui_distribute_layout;
    }

    //    private SpikeChooseTimeAdapter testAdapter;
    private boolean isShow;

    @Override
    public void initView() {
        conponBaseId=getIntent().getStringExtra("id");
        actionbar.setImgStatusBar(R.color.my_color_white);
        actionbar.setTitle("派发");
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
    public void disTribute(){
        DataManager.getInstance().payoutConpon(new DefaultSingleObserver<Object>() {
            @Override
            public void onSuccess(Object helpDto) {
                ToastUtil.showToast("派发成功");
                dissLoadDialog();
                finish();

            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();

            }
        },ids,conponBaseId);
    }
    private int poi;
    public void selection(int poition, boolean isShow) {
        switch (poition) {

            case 0:
                poition=0;
                tvTime.setTextColor(getResources().getColor(R.color.my_color_blue));
                tvPerson.setTextColor(getResources().getColor(R.color.my_color_333333));
                lineSend.setVisibility(View.VISIBLE);
                lineUser.setVisibility(View.INVISIBLE);
                if (isShow) {
                    viewpagerMain.setCurrentItem(0, false);
                }

                break;
            case 1:
                poition=1;
                tvTime.setTextColor(getResources().getColor(R.color.my_color_333333));
                tvPerson.setTextColor(getResources().getColor(R.color.my_color_blue));
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
    private DistributeFragment spikeFragment;
    private DistributeFragment spikeFragment1;
    private void initFragMents(int poistion) {
        list_productfragment = new ArrayList<>();
         spikeFragment = new DistributeFragment();
        spikeFragment.setColumn("createTime");
        list_productfragment.add(spikeFragment);
         spikeFragment1 = new DistributeFragment();
        spikeFragment1.setColumn("salesTotal");
        list_productfragment.add(spikeFragment1);


        mainViewPagerAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), list_productfragment);
        viewpagerMain.setAdapter(mainViewPagerAdapter);
        viewpagerMain.setOffscreenPageLimit(1);//设置缓存view 的个数
        viewpagerMain.setCurrentItem(poistion);

    }


    @Override
    public void chooseAll(int state) {
        if(state==1){
            mcbChoose.setChecked(true);
         }else {
            mcbChoose.setChecked(false);
        }
    }

    @Override
    public void showMsg(String str) {

    }
}
