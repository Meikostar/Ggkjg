package com.ggkjg.base;

public class BuildConfig {
    /**
     * 请求http基地址
     */
//    public static final String BASE_URL = "http://47.107.143.211:8080/hkonline/";
    /**
     * 调试
     */
//    public static final String BASE_URL = "http://ng.2aa6.com/hkonline/";
//    public static final String BASE_URL = "http://app.ggkjsz.com/test/";
//    public static final String BASE_URL = "http://app.ggkj.hk/hkonline/";
//    public static final String BASE_URL = "http://192.168.1.165/hkonline/";
    public static final String BASE_URL = "http://ggkj.frp.2aa6.com/hkonline/";
//    public static final String BASE_URL = "http://b.ng.2aa6.com:8080/hkonline/";

//    public static final String BASE_URL = "http://a.smg.kingstang.com:8080/hkonline/";

    /**
     * 图片的基本地址
     */
    //public static final String BASE_IMAGE_URL = "http://47.107.143.211:8080/hkonline";
    public static String BASE_IMAGE_URL = "http://app.ggkjsz.com";
//    public static String BASE_IMAGE_URL = "http://47.107.143.211:80/";
    /**
     * Get请求图片验证码地址
     */
    public static final String PICTURE_CODE = BASE_URL+"silent/member/getPictureCode?mobileNo=";
    /**
     * 是否打开调试信息
     */
    public static final boolean open_log = true;


}
