package com.ggkjg;

import com.ggkjg.base.BaseActivity;
import com.ggkjg.base.BuildConfig;
import com.ggkjg.common.utils.LogUtil;
import com.ggkjg.dto.BrowerDto;
import com.ggkjg.view.MainActivity;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.subscribers.DefaultSingleObserver;

/**
 * 启动页面
 * Created by xld021 on 2018/4/14.
 */

public class SplashActivity extends BaseActivity {
    private static final String TAG = SplashActivity.class.getSimpleName();
    @Override
    public int getLayoutId() {
        return R.layout.ui_splash_layout;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
        fileBrower();
    }

    @Override
    public void initListener() {
//        RxTimerUtil.timer(500, number -> {
//            if (!Constants.getInstance().isLogin()) {
//                gotoActivity(LoginActivity.class,true);
//            } else {
//                gotoActivity(MainActivity.class,true);
//            }
//            gotoActivity(MainActivity.class, true);
//        });
    }

    private void fileBrower() {
        DataManager.getInstance().fileBrower(new DefaultSingleObserver<BrowerDto>() {

            @Override
            public void onSuccess(BrowerDto  bto) {
                try {
                    LogUtil.i(TAG, "cacaca--RxLog-Thread: onSuccess()" + bto.getBrowerPrefix());
                    BuildConfig.BASE_IMAGE_URL = bto.getBrowerPrefix();
                    LogUtil.i(TAG, "cacaca--RxLog-Thread: onSuccess()" + BuildConfig.BASE_IMAGE_URL);
                    gotoActivity(MainActivity.class, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                LogUtil.i(TAG, "--RxLog-Thread: onError()" + throwable);
                gotoActivity(MainActivity.class, true);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
