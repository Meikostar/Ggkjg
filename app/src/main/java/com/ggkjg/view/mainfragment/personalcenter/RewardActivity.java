package com.ggkjg.view.mainfragment.personalcenter;

import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.dto.BonusAccmountDto;
import com.ggkjg.view.widgets.TabEntity;

import java.util.ArrayList;

import butterknife.BindView;

public class RewardActivity extends BaseActivity {
    @BindView(R.id.commonTabLayout)
    CommonTabLayout commonTabLayout;
    @BindView(R.id.tv_reward_money)
    TextView tv_reward_money;
    @BindView(R.id.tv_profit)
    TextView tv_profit;
    @BindView(R.id.tv_commission)
    TextView tv_commission;


    private String[] mSubTitles = {"全部", "分润", "分享"};
    private ArrayList<CustomTabEntity> mSubTabEntities = new ArrayList<>();
    RewardFragment fragments[] = new RewardFragment[3];
    private String fragments_name[] = {"all", "inCome", "outCome"};
    int currentTabIndex = -1;
    RewardInfoListener infoListener;

    @Override
    public void initListener() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.ui_reward_layout;
    }

    @Override
    public void initView() {
        actionbar.setImgStatusBar(R.color.my_color_white);
        StatusBarUtils.StatusBarLightMode(this);
        initTab();
        infoListener = new RewardInfoListener() {
            @Override
            public void baseInfoBack(BonusAccmountDto bonusAccmountDto) {
                if (bonusAccmountDto != null) {
                    tv_reward_money.setText(bonusAccmountDto.getBonusAmount());
                    tv_profit.setText(bonusAccmountDto.getBonusSalesAmount());
                    double mCommission = Double.parseDouble(bonusAccmountDto.getBonusDirectAmount()) + Double.parseDouble(bonusAccmountDto.getBonusManageAmount());
                    tv_commission.setText(mCommission + "");
                }
            }
        };
    }

    @Override
    public void initData() {
        switchFragment(0);
    }

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
        if (fragments[index] == null) {
            fragments[index] = new RewardFragment();
            trx.add(R.id.content_layout, fragments[index], fragments_name[index]);
        }
        if (index == 0) {
            fragments[index].setType("");
            fragments[index].setRewardInfoListener(infoListener);
        } else if (index == 1) {
            fragments[index].setType("2");
        } else if (index == 2) {
            fragments[index].setType("1");
        }
        if (currentTabIndex != -1) {
            trx.hide(fragments[currentTabIndex]);
        }
        trx.show(fragments[index]).commit();
        currentTabIndex = index;
    }


    public interface RewardInfoListener {
        public void baseInfoBack(BonusAccmountDto bonusAccmountDto);
    }
}
