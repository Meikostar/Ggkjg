package com.ggkjg.common.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.io.File;
import java.util.List;

/**
 * 检查第三方app是否安装
 * * Created by dahai on 2019/01/02.
 */
public class InstallWeChatOrAliPay {
    private static InstallWeChatOrAliPay instance = null;

    public static InstallWeChatOrAliPay getInstance() {
        if (instance == null) {
            instance = new InstallWeChatOrAliPay();
        }
        return instance;
    }

    /**
     * 检测是否安装支付宝
     *
     * @param context
     * @return
     */
    public boolean checkAliPayInstalled(Context context) {
        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        return componentName != null;
    }

    /**
     * 判断 用户是否安装微信客户端
     */
    public boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断 用户是否安装QQ客户端
     */
    public boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equalsIgnoreCase("com.tencent.qqlite") || pn.equalsIgnoreCase("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * sina
     * 判断是否安装新浪微博
     */
    public boolean isSinaInstalled(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.sina.weibo")) {
                    return true;
                }
            }
        }
        return false;
    }


    public static final String PACKAGE_MANAGER_baidu = "com.baidu.BaiduMap";
    public static final String PACKAGE_MANAGER_gaode = "com.autonavi.minimap";
    public static final String PACKAGE_MANAGER_google = "com.google.android.apps.maps";
    /**
     * 方案二
     * 判断是否安装目标应用
     *
     * @param packageName 目标应用安装后的包名
     * @return 是否已安装目标应用
     */
    private boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }

    /**
     * 高德地图
     * @return
     */
    public boolean isGaodeAvilible() {
        if (isInstallByread(PACKAGE_MANAGER_gaode)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 百度地图
     * @return
     */
    public boolean isBaiduAvilible() {
        if (isInstallByread(PACKAGE_MANAGER_baidu)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Google 地图
     * @return
     */
    public boolean isGoogleAvilible() {
        if (isInstallByread(PACKAGE_MANAGER_google)) {
            return true;
        } else {
            return false;
        }
    }


}
