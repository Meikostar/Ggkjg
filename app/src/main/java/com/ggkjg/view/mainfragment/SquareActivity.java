package com.ggkjg.view.mainfragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.LogUtil;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.common.utils.ToastUtil;
import com.ggkjg.view.mainfragment.login.LoginActivity;
import com.ggkjg.view.mainfragment.login.RegisterActivity;
import com.ggkjg.view.mainfragment.shop.CommodityDetailActivity;
import com.ggkjg.view.widgets.ConfirmHintDialog;
import com.ggkjg.view.widgets.TabEntity;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 主页
 * Created by dahai on 2019/01/18.
 */
public class SquareActivity extends BaseActivity implements BaseActivity.PermissionsListener {
    private static final String TAG = SquareActivity.class.getSimpleName();
    @BindView(R.id.mian_bottom_tabs_layout)
    CommonTabLayout tabsLayout;


    private ArrayList<CustomTabEntity> tabEntities = new ArrayList<>();
    private ArrayList<Fragment> fragments = new ArrayList<>();


    protected FragmentTransaction mTransaction;

    @Override
    public int getLayoutId() {
        return R.layout.ui_main_layout;
    }

    @Override
    public void initView() {
        mTransaction = getSupportFragmentManager().beginTransaction();
        SquareFragment squareFragment = new SquareFragment();
        mTransaction.replace(R.id.mian_content_layout, squareFragment);
        mTransaction.commit();
        tabsLayout.setVisibility(View.GONE);
        StatusBarUtils.StatusBarLightMode(this);



    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!Constants.getInstance().isLogin()) {
            tabsLayout.setCurrentTab(0);
        }
    }





    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    @Override
    public void callbackPermissions(String permissions, boolean isSuccess) {

    }
}
