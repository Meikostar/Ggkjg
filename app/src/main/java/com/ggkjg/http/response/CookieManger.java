package com.ggkjg.http.response;

import android.text.TextUtils;

import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.LogUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class CookieManger implements CookieJar {


    public CookieManger() {

    }


    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
            String cookiesStr = new Gson().toJson(cookies);
            if (!Constants.getInstance().getBoolean(Constants.NO_LOGIN_SUCCESS)) {
                Constants.getInstance().save(Constants.COOKIE_KEY, cookiesStr);
            }
            LogUtil.d("-- cookiesStr", cookiesStr);
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        String httpUrl = "";
        if (url != null) {
            httpUrl = url.toString();
        }
        List<Cookie> cookies = null;
        String cookieStr = Constants.getInstance().getString(Constants.COOKIE_KEY, "");
        if (!TextUtils.isEmpty(cookieStr) && (httpUrl.indexOf("silent/member/login") == -1)) { //登录接口不要带cookie
            cookies = new Gson().fromJson(cookieStr, new TypeToken<List<Cookie>>() {
            }.getType());
        }
        if (cookies == null) {
            cookies = new ArrayList<>();
        }
        return cookies;
    }

}
