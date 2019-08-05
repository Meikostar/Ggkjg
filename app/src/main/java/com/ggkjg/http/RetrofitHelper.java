package com.ggkjg.http;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.ggkjg.base.BuildConfig;
import com.ggkjg.common.utils.LogUtil;
import com.ggkjg.http.gson.GsonConverterFactory;
import com.ggkjg.http.response.CookieManger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.concurrent.TimeUnit;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by win764-1 on 2016/12/12.
 */

public class RetrofitHelper {

    private static final String TAG = RetrofitHelper.class.getSimpleName();

    private static final int DEFAULT_TIMEOUT = 5000;

    private static RetrofitHelper instance = null;
    private Retrofit mRetrofit = null;
    public Gson gson;

    public static RetrofitHelper getInstance() {
        if (instance == null) {
            instance = new RetrofitHelper();
        }
        return instance;
    }

    private RetrofitHelper() {
        init();
    }

    private void init() {
        resetApp();
    }

    private void resetApp() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.addInterceptor(new LoggingInterceptor());// 打印日志
        builder.hostnameVerifier((hostname, session) -> true);
        builder.cookieJar(new CookieManger());
//        builder.addInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request original = chain.request();
//                Request.Builder requestBuilder = original.newBuilder();
//                if(Constants.getInstance().getBoolean(Constants.NO_LOGIN_SUCCESS)){
//                    requestBuilder.addHeader("JSESSIONID", Constants.getInstance().getString(Constants.USER_SERVLET_TOKEN, ""));
//                    requestBuilder.addHeader("JSESSIONID_SHIRO", Constants.getInstance().getString(Constants.USER_SHIRO_TOKEN, ""));
//                }
//                Request request = requestBuilder.build();
//                return chain.proceed(request);
//            }
//        });

        builder.addNetworkInterceptor(new StethoInterceptor());
        try {
            SSLSocketUtils.ignoreClientCertificate(builder);
        } catch (Exception e) {
            LogUtil.i(TAG, e.toString());
        }
        gson = new GsonBuilder().create();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())//请求的结果转为实体类
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
    }

    public RetrofitService getServer() {
        return mRetrofit.create(RetrofitService.class);
    }


}
