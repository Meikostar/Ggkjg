package com.ggkjg.http;

import android.annotation.SuppressLint;

import com.ggkjg.common.utils.LogUtil;
import com.ggkjg.http.error.ApiException;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by uqduiba on 11/5/2017.
 */

class LoggingInterceptor implements Interceptor {

    private static final String TAG = LoggingInterceptor.class.getSimpleName();

    @SuppressLint("DefaultLocale")
    @Override
    public Response intercept(Chain chain) throws IOException {
        // 这个chain里面包含了request和response，所以你要什么都可以从这里拿
        Request request = chain.request();
        // 请求发起的时间
        long t1 = System.nanoTime();
        LogUtil.i(TAG, String.format("--发送请求 %s %s", request.url(), request.headers()));
        Response response = chain.proceed(request);
        // 对返回code统一拦截
        LogUtil.i(TAG, "--response code:" + response.code());
        // 收到响应的时间
        long t2 = System.nanoTime();
        ResponseBody responseBody = response.peekBody(1024 * 1024);
        LogUtil.i(TAG, String.format("--接收响应 %s, 历时%.1fms %n%s",
                response.request().url(),
                (t2 - t1) / 1e6d,
                responseBody.string()));
        return response;
    }
}
