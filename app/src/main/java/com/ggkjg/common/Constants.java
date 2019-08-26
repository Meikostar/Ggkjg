package com.ggkjg.common;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.text.TextUtils;

import com.ggkjg.base.BaseApplication;

import java.io.File;
import java.util.Map;

/**
 * 通用标签工具
 * Created by dahai on 2019/01/18.
 */

public class Constants {
    /**
     * 程序第一次启动
     */
    public static final String NO_FIRST_START = "NO_FIRST_START";
    /**
     * 程序登录成功
     */
    public static final String NO_LOGIN_SUCCESS = "login_success";
    /**
     * 用户登录servlet会话token凭证
     */
    public final static String USER_SERVLET_TOKEN = "user_servlet_token";
    /**
     * 用户登录shiro会话token凭证
     */
    public final static String USER_SHIRO_TOKEN = "user_shiro_token";
    /**
     * 用户昵称
     */
    public final static String USER_NICK_NAME = "user_nick_name";
    /**
     * 用户头像
     */
    public final static String USER_HEAD_IMG = "user_head_img";
    /**
     * 用户登录手机号码
     */
    public final static String USER_PHONE = "user_phone";
    /**
     * 用户名id
     */
    public final static String USER_ID = "user_id";
    /**
     * 用户会员等级 1-普通会员 2-店主 3-创客 4-城市运营中心
     */
    public final static String USER_MEMBER_LEVEL = "user_member_level";
    /**
     * 用户实名制认证状态0-未上传 1-未审核 2-审核通过 3-审核未通过
     */
    public final static String USER_AUTH_STATE = "user_auth_state";

    /**
     * 系统路径拼接图片地址
     */
    public final static String SYS_PATH = "sysPath";
    /**
     * 充值成功
     */
    public static final String RECHARGE_TYPE_SUCCESS = "type_success";
    /**
     * 充值失败
     */
    public static final String RECHARGE_TYPE_FAIL = "type_fail";
    /**
     * 支付成功
     */
    public static final String PAY_TYPE_SUCCESS = "pay_success";
    /**
     * 支付失败
     */
    public static final String PAY_TYPE_FAIL = "pay_fail";
    /**
     * 地址是否编辑
     */
    public static final String INTENT_ADDRESS_ISEDIT = "area_isEdit";
    /**
     * 所有我的评论
     */
    public static final String COMMENT_ALL_DATA_TYPE = "all_data";
    /**
     * 有图片评论
     */
    public static final String COMMENT_IMAGE_DATA_TYPE = "image_data";
    /**
     * 全部订单
     */
    public static final String TREE_ORDER_TYPE_ALL = "type_all";
    /**
     * 待发货
     */
    public static final String TREE_ORDER_TYPE_DELIVERY = "type_delivery";
    /**
     * 已收货
     */
    public static final String TREE_ORDER_TYPE_RECEIVE = "type_receive";
    /**
     * 评价
     */
    public static final String TREE_ORDER_TYPE_EVALUATE = "type_evaluate";
    /**
     * 待付款
     */
    public static final String TREE_ORDER_TYPE_PAYMENT = "type_payment";
    /**
     * 微信 WX_APP_ID
     */
//    public static final String WX_APP_ID = "wx71ff9cd3b8cb4dd0";
    public static final String WX_APP_ID = "wx7f15f82d4ef1b563";


    /**
     * 保持cookie key
     **/
    public static final String COOKIE_KEY = "cookie_key";

    public static final String WX_PAY_BROADCAST = "wx_pay_broadcast";
    public static final String INTENT_CODE = "code";
    /**
     * 确定收货
     */
    public static final String CONFIRM_RECEIVE = "confirm_receive";
    /**
     * 查看物流
     */
    public static final String LOOK_LOGISTICS = "look_logistics";

    /**
     * 去支付
     */
    public static final String PAY_MONEY = "pay_money";
    /**
     * 取消订单
     */
    public static final String CANCEL_ORDER = "cancel_order";
    /**
     * 评价
     */
    public static final String ORDER_EVALUATE = "order_evaluate";
    /**
     * 传值
     */
    public static final String INTENT_PHONE = "phone";
    /**
     * 传值
     */
    public static final String INTENT_FLAG = "flag";
    public static final String INTENT_POSITION = "position";
    /*推广二维码*/
    public static final String INTENT_REFERRAL_QRCODE = "referralQrcode";
    /**
     * 订单转达类型Intent的名字
     */
    public static final String ORDER_TYPE = "order_type";
    public static final String INTENT_ORDER_NO = "order_no";

    public static final String INTENT_AREA_NAME = "area_name";
    public static final String INTENT_DATA = "data";
    /*是否选择地址*/
    public static final String INTENT_SEL_ADDRESS = "sel_address";
    public static final String ORDER_ID = "order_id";
    public static final String ORDER_NO = "order_no";
    public static final String ORDER_MONEY = "order_money";

    private static Constants constants;
    private static SharedPreferences sharedPreferences;
    public static final String FILENAME = "com.ggkjg";
    /*MD5加密添加的字段*/
    public static final String MD5_ADD_STR = "hkonline";
    public static final String IMAGEITEM_DEFAULT_ADD = "default_add";
    public static final String EXPRESS_ID = "express_id";
    public static final String EXPRESS_NO = "express_no";
    public static final String WX_LOGIN_BROADCAST = "wx_login_broadcast";

    public static final int INTENT_REQUESTCODE_VERIFIED_IMG1 = 50;
    public static final int INTENT_REQUESTCODE_VERIFIED_IMG2 = 60;
    public static final int INTENT_REQUESTCODE_VERIFIED_IMG3 = 70;


    public static final int INTENT_REQUESTCODE_AREA = 10;
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    /*退款申请成功*/
    public static final int INTENT_REQUESTCODE_RETURN_SUCCESS = 80;
    /*绑定手机号码成功*/
    public static final int INTENT_REQUESTCODE_BIND_PHONE_SUCCESS = 90;
    /*填快递单号*/
    public static final int INTENT_REQUESTCODE_FILL_EXPRESS = 110;
    /*选地址*/
    public static final int INTENT_REQUESTCODE_SEL_ADDRESS = 120;
    public static final int INTENT_REQUESTCODE_PAY = 130;
    public static final int INTENT_REQUESTCODE_EVALUATE = 140;
    public static final int INTENT_REQUESTCODE_VALDATEMOBILE = 150;
    public static final int PAGE_NUM = 1;
    public static final int PAGE_SIZE = 20;

    private Constants() {
        if (constants == null) {
            sharedPreferences = BaseApplication.getInstance().getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        }
    }

    public static Constants getInstance() {
        if (constants == null) {
            constants = new Constants();
        }
        return constants;
    }

    /**
     * 获取数据
     *
     * @param key
     * @return
     */
    public String get(String key) {
        return sharedPreferences.getString(key, null);
    }

    /**
     * 取出数据
     *
     * @param key
     * @param defValue
     * @return
     */
    public String getString(String key, String defValue) {
        return sharedPreferences.getString(key, defValue);
    }

    /**
     * 保存偏好设置
     *
     * @param key
     * @param value
     */
    public void save(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (value == null) {
            editor.putString(key, "");
        } else {
            editor.putString(key, value);
        }
        editor.commit();
    }

    /**
     * 保存偏好设置
     *
     * @param map 需要保存的map集合
     */
    public void save(Map<String, String> map) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (Map.Entry<String, String> entry : map.entrySet()) {

            if (entry.getValue().length() == 0) {
                editor.putString(entry.getKey(), null);

            } else {
                editor.putString(entry.getKey(), entry.getValue());
            }
        }
        editor.commit();
    }

    // 保存int类型
    public void saveInt(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();

    }

    // 获取int类型
    public int getInt(String key) {
        return sharedPreferences.getInt(key, 0);
    }

    // 获取int类型
    public int getInt(String key, int defaultInt) {
        return sharedPreferences.getInt(key, defaultInt);
    }

    /**
     * 获取数据
     *
     * @param key
     * @return
     */
    public boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    /**
     * 获取数据
     *
     * @param key
     * @param value
     * @return
     */
    public boolean getBoolean(String key, boolean value) {
        return sharedPreferences.getBoolean(key, value);
    }

    /**
     * 保存偏好设置
     *
     * @param key
     * @param value
     */
    public void saveBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 清空指定的Key
     */
    public void removeKey(String key) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * 退出时候清空用户信息
     */
    public void cleanUserInfo() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_SERVLET_TOKEN, "");
        editor.putString(USER_SHIRO_TOKEN, "");
        editor.putString(USER_PHONE, "");
        editor.putString(USER_NICK_NAME, "");
        editor.putString(USER_HEAD_IMG, "");
        editor.putString(USER_ID, "");
        editor.putString(USER_MEMBER_LEVEL, "");
        editor.putString(USER_AUTH_STATE, "");
        editor.putString(SYS_PATH, "");
        editor.clear();
        // 清空但保存不是一登录
        editor.putBoolean(NO_FIRST_START, true);
        editor.putBoolean(NO_LOGIN_SUCCESS, false);
        // 清空但保存提示信息
//		editor.putBoolean(Constants.NOTFIRST, true);
        editor.commit();
    }

    /**
     * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache)
     *
     * @param context
     */
    public void cleanInternalCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
    }

    /**
     * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases)
     */
    public void cleanDatabases(Context context) {
        deleteFilesByDirectory(new File("/data/data/" + context.getPackageName() + "/databases"));
    }

    /**
     * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs)
     */
    public void cleanSharedPreference(Context context) {
        deleteFilesByDirectory(new File("/data/data/" + context.getPackageName() + "/shared_prefs"));
    }

    /**
     * 按名字清除本应用数据库
     *
     * @param context
     * @param dbName
     */
    public void cleanDatabaseByName(Context context, String dbName) {
        context.deleteDatabase(dbName);
    }

    /**
     * 清除/data/data/com.xxx.xxx/files下的内容
     *
     * @param context
     */
    public void cleanFiles(Context context) {
        deleteFilesByDirectory(context.getFilesDir());
    }

    /**
     * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache)
     *
     * @param context
     */
    public void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteFilesByDirectory(context.getExternalCacheDir());
        }
    }

    /**
     * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除
     *
     * @param filePath
     */
    public void cleanCustomCache(String filePath) {
        deleteFilesByDirectory(new File(filePath));
    }

    /**
     * 清除本应用所有的数据
     *
     * @param context
     * @param filepath
     */
    public void cleanApplicationData(Context context, String... filepath) {
        cleanInternalCache(context);
        cleanExternalCache(context);
        cleanDatabases(context);
        cleanSharedPreference(context);
        cleanFiles(context);
        for (String filePath : filepath) {
            cleanCustomCache(filePath);
        }
    }

    /**
     * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理
     *
     * @param directory
     */
    private void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }

    /**
     * 用户是否登录
     *
     * @return
     */
    public boolean isLogin() {
        if (!TextUtils.isEmpty(Constants.getInstance().getString(USER_SERVLET_TOKEN, "")) &&
                !TextUtils.isEmpty(Constants.getInstance().getString(USER_SHIRO_TOKEN, ""))) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 截取字符串str中指定字符 strStart、strEnd之间的字符串
     *
     * @param value
     * @param strStart
     * @param strEnd
     * @return
     */
    public static String subString(String value, String strStart, String strEnd) {
        /* 找出指定的2个字符在 该字符串里面的 位置 */
        int strStartIndex = value.indexOf(strStart);
        int strEndIndex = value.indexOf(strEnd);
        /* index 为负数 即表示该字符串中 没有该字符 */
        if (strStartIndex < 0) {
//            return "字符串 :---->" + value + "<---- 中不存在 " + strStart + ", 无法截取目标字符串";
            return null;
        }
        if (strEndIndex < 0) {
//            return "字符串 :---->" + value + "<---- 中不存在 " + strEnd + ", 无法截取目标字符串";
            return null;
        }
        /* 开始截取 */
        String result = value.substring(strStartIndex, strEndIndex).substring(strStart.length());
        return result;
    }

}
