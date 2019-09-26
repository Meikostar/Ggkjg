package com.ggkjg.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import com.facebook.stetho.Stetho;
import com.ggkjg.common.Constants;
import com.ggkjg.common.CrashUtil;
import com.ggkjg.common.utils.TextUtil;
import com.ggkjg.db.DaoManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.udesk.UdeskSDKManager;
import cn.udesk.config.UdeskConfig;
import udesk.core.UdeskConst;


/**
 * Created by dahai on 2018/4/11.
 */

public class BaseApplication extends Application {

    private static BaseApplication instance;
    private static List<Activity> activityList = new ArrayList<>();

    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //GreenDao数据库管理初始化
        UdeskSDKManager.getInstance().initApiKey(this, "ggkjds.s2.udesk.cn","c83493630ab4134046357585e25cc3eb","e74387aa0a787b71");
        CrashUtil crashUtil = CrashUtil.getInstance();
        crashUtil.init(this);

        DaoManager.getInstance().init(this);
        Stetho.initializeWithDefaults(this); //调试接口
    }


    /**
     * 关闭每一个list内的activity
     */
    public void finishAll() {
        try {
            for (Activity activity : activityList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加activity管理
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }


}
