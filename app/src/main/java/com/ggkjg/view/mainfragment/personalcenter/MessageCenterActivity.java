package com.ggkjg.view.mainfragment.personalcenter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.view.widgets.TabEntity;

import java.util.ArrayList;

import butterknife.BindView;

public class MessageCenterActivity extends BaseActivity {
    @BindView(R.id.commonTabLayout)
    CommonTabLayout commonTabLayout;
    private String[] mSubTitles = {"系统消息", "活动消息"};
    private ArrayList<CustomTabEntity> mSubTabEntities = new ArrayList<>();
    private MessageCenterFragment fragments[] = new MessageCenterFragment[2];
    private String fragments_name[] = {"system", "activity"};
    int currentTabIndex = -1;
    @Override
    public void initListener() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.ui_message_center_layout;
    }

    @Override
    public void initView() {
        actionbar.setImgStatusBar(R.color.my_color_white);
        StatusBarUtils.StatusBarLightMode(this);
        initTab();
        switchFragment(0);
    }

    @Override
    public void initData() {

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
        boolean isUpdate = true;
        if (fragments[index] == null) {
            isUpdate = false;
            fragments[index] = new MessageCenterFragment();
            trx.add(R.id.content_layout, fragments[index], fragments_name[index]);
//            if (index == 0) {
                fragments[index].setType(""+index);
//            } else if (index == 1) {
//                fragments[index].setType("1");
//            }
        }
        if (currentTabIndex != -1) {
            trx.hide(fragments[currentTabIndex]);
        }
        trx.show(fragments[index]).commit();
        currentTabIndex = index;
//        if (isUpdate) {
//            fragments[index].loadData(true);
//        }
    }
}
