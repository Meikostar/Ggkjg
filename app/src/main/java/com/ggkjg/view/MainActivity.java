package com.ggkjg.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.LogUtil;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.common.utils.ToastUtil;
import com.ggkjg.view.mainfragment.ShopClassFragment;
import com.ggkjg.view.mainfragment.login.LoginActivity;
import com.ggkjg.view.mainfragment.login.RegisterActivity;
import com.ggkjg.view.mainfragment.shop.CommodityDetailActivity;
import com.ggkjg.view.widgets.ConfirmHintDialog;
import com.ggkjg.view.widgets.TabEntity;
import com.ggkjg.view.mainfragment.HomeFragment;
import com.ggkjg.view.mainfragment.MeFragment;
import com.ggkjg.view.mainfragment.ShopCartFragment;
import com.ggkjg.view.mainfragment.SquareFragment;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 主页
 * Created by dahai on 2019/01/18.
 */
public class MainActivity extends BaseActivity implements BaseActivity.PermissionsListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.mian_bottom_tabs_layout)
    CommonTabLayout tabsLayout;
    private long clickTime;

    public static final String PAGE_INDEX = "page_index";//调用者传递的名字
    private int page_index = 0;//页面索引

    private ArrayList<CustomTabEntity> tabEntities = new ArrayList<>();
    private ArrayList<Fragment> fragments = new ArrayList<>();

    private int[] iconUnSelectIds = {R.mipmap.mian_bottom_tabs_icon_1,
            R.mipmap.mian_bottom_tabs_icon_2,
//            R.mipmap.mian_bottom_tabs_icon_3,
            R.mipmap.mian_bottom_tabs_icon_4,
            R.mipmap.mian_bottom_tabs_icon_5};
    private int[] iconSelectIds = {R.mipmap.mian_bottom_tabs_icon_sel_1,
            R.mipmap.mian_bottom_tabs_icon_sel_2,
//            R.mipmap.mian_bottom_tabs_icon_sel_3,
            R.mipmap.mian_bottom_tabs_icon_sel_4,
            R.mipmap.mian_bottom_tabs_icon_sel_5};

    private int REQUEST_CODE_SCAN = 111;
    public static final int REQUEST_CODE_LOGIN = 10002;
    public static final String LOGIN_PAGE_INDEX = "login_page_index";

    //扫一扫广播
    public static final String ACTION_HOME_CAPTURE_CODE = "com.ggkjg.action.HOME_CAPTURE_CODE";
    public static final String ACTION_SHOP_CART_CAPTURE_CODE = "com.ggkjg.action.SHOP_CART_CAPTURE_CODE";
    public static final String ACTION_SHOP_CLASS_CAPTURE_CODE = "com.ggkjg.action.SHOP_CLASS_CAPTURE_CODE";
    private boolean isOpenCaptureCodeTag = false;//是否打开扫一扫
    private MyBroadcastReceiver mRecvier = null;


    @Override
    public int getLayoutId() {
        return R.layout.ui_main_layout;
    }

    @Override
    public void initView() {
        StatusBarUtils.StatusBarLightMode(this);
        String[] titles = getResources().getStringArray(R.array.home_bottom_titles);
        HomeFragment homeFragment = new HomeFragment();
        ShopClassFragment shopClassFragment = new ShopClassFragment();
        SquareFragment squareFragment = new SquareFragment();
        ShopCartFragment shopCartFragment = new ShopCartFragment();
        MeFragment meFragment = new MeFragment();
        fragments.add(homeFragment);
        fragments.add(shopClassFragment);
//        fragments.add(squareFragment);
        fragments.add(shopCartFragment);
        fragments.add(meFragment);
        for (int i = 0; i < titles.length; i++) {
            tabEntities.add(new TabEntity(titles[i], iconSelectIds[i], iconUnSelectIds[i]));
        }
        tabsLayout.setTabData(tabEntities, this, R.id.mian_content_layout, fragments);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public void initData() {
        page_index = getIntent().getIntExtra(PAGE_INDEX, 0);
        tabsLayout.setCurrentTab(page_index);
    }

    @Override
    public void initListener() {
        checkPermissioin();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_HOME_CAPTURE_CODE);
        intentFilter.addAction(ACTION_SHOP_CART_CAPTURE_CODE);
        intentFilter.addAction(ACTION_SHOP_CLASS_CAPTURE_CODE);
        mRecvier = new MyBroadcastReceiver();
        registerReceiver(mRecvier, intentFilter);
        tabsLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                LogUtil.i(TAG, "-- onTabSelect position:" + position);
                if (position == 2 || position == 3) {
                    if (!Constants.getInstance().isLogin()) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.putExtra(LoginActivity.PAGE_INDEX, position);
                        startActivityForResult(intent, REQUEST_CODE_LOGIN);
                        tabsLayout.setCurrentTab(page_index);
                    } else {
                        tabsLayout.setCurrentTab(position);
                    }
                } else {
                    tabsLayout.setCurrentTab(position);
                }
            }

            @Override
            public void onTabReselect(int position) {
                LogUtil.i(TAG, "-- onTabReselect position:" + position);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!Constants.getInstance().isLogin()) {
            tabsLayout.setCurrentTab(0);
        }
    }

    /**
     * 启动扫一扫
     */
    private void startScan() {
        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
        ZxingConfig config = new ZxingConfig();
//        config.setPlayBeep(false);//是否播放扫描声音 默认为true
//        config.setShake(false);//是否震动  默认为true
//        config.setDecodeBarCode(false);//是否扫描条形码 默认为true
        config.setReactColor(R.color.my_color_00AAED);//设置扫描框四个角的颜色 默认为白色
//        config.setFrameLineColor(R.color.colorAccent);//设置扫描框边框颜色 默认无色
        config.setScanLineColor(R.color.my_color_FABB48);//设置扫描线的颜色 默认白色
//        config.setFullScreenScan(false);//是否全屏扫描  默认为true  设为false则只会在扫描框中扫描
        intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
    }

    @Override
    public void callbackPermissions(String permissions, boolean isSuccess) {
        if (permissions.equals(CAMERA) && !isSuccess) {
            ToastUtil.showToast("请在设置中打开相机权限.");
        } else if (permissions.equals(READ_EXTERNAL_STORAGE) && !isSuccess) {
            ToastUtil.showToast("请在设置中打开读写手机权限.");
        }
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            LogUtil.d(TAG, "-- action=" + action);
            if (action.equals(ACTION_HOME_CAPTURE_CODE)
                    || action.equals(ACTION_SHOP_CART_CAPTURE_CODE)
                    || action.equals(ACTION_SHOP_CLASS_CAPTURE_CODE)) {
                isOpenCaptureCodeTag = true;
                if (!isPermissioin(CAMERA) && !isPermissioin(READ_EXTERNAL_STORAGE)) {
                    checkPermissioin(CAMERA);
                    checkPermissioin(READ_EXTERNAL_STORAGE);
                    setPermissionsListener(MainActivity.this::callbackPermissions);
                } else {
                    startScan();
                }
            }


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                String userStr = "/register.html?referralCode=";
                String productStr = "/good_details.html?id=";
                if (content.indexOf(userStr) != -1) {
                    String referralCode = content.substring(content.lastIndexOf("=")+1);
                    if(Constants.getInstance().isLogin()){
                        ConfirmHintDialog dialog = new ConfirmHintDialog(this);
                        dialog.setTitle("推荐码");
                        dialog.setMessage(referralCode);
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.show();
                        ToastUtil.showToast("推荐码：" + referralCode);
                    }else{
                        Bundle bundle = new Bundle();
                        bundle.putLong(RegisterActivity.PUSH_CODE, Long.valueOf(referralCode));
                        gotoActivity(RegisterActivity.class, false, bundle);
                    }
                }else if (content.indexOf(productStr) != -1) {
                    Bundle bundle = new Bundle();
                    String id = content.substring(content.lastIndexOf("=")+1);
                    bundle.putLong(CommodityDetailActivity.PRODUCT_ID, Long.valueOf(id));
                    gotoActivity(CommodityDetailActivity.class, false, bundle);
                }else{
                    ToastUtil.showToast("扫描结果为：" + content);
                }
            }
        } else if (requestCode == REQUEST_CODE_LOGIN && resultCode == RESULT_OK) {
            int pageIndex = data.getIntExtra(LOGIN_PAGE_INDEX, 0);
            tabsLayout.setCurrentTab(pageIndex);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRecvier != null) {
            unregisterReceiver(mRecvier);
        }
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - clickTime < 2000) {
            finishAll();
        } else {
            clickTime = System.currentTimeMillis();
            ToastUtil.toast("再按一次退出应用");
        }
    }
}
