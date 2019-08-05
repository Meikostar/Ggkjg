package com.ggkjg.view.mainfragment.message;

import android.graphics.Bitmap;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.base.BuildConfig;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.common.utils.WebViewUtil;

import java.util.Objects;

import butterknife.BindView;

/**
 * 商城快讯
 * Created by xld021 on 2018/4/14.
 */

public class ShopMessageActivity extends BaseActivity {
    private static final String TAG = ShopMessageActivity.class.getSimpleName();
    public static final String MESSAGE_ID = "message_id";//调用者传递的名字
    private long message_id;//消息ID
    @BindView(R.id.shop_message_webview)
    WebView webView;


    @Override
    public int getLayoutId() {
        return R.layout.ui_shop_message_layout;
    }

    @Override
    public void initView() {
        actionbar.setImgStatusBar(R.color.my_color_white);
        StatusBarUtils.StatusBarLightMode(this);
    }

    @Override
    public void initData() {
        message_id = getIntent().getLongExtra(MESSAGE_ID, 0);
        final String url = BuildConfig.BASE_URL + "cmsinfo/cmsinfo/CmsInfo/front/readDetail/gotopage?id=" + message_id;
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存
        webView.loadUrl(url);
        webView.setWebViewClient(new MyWebViewClient());
    }

    @Override
    public void initListener() {

    }

    class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            showLoadDialog();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            dissLoadDialog();
        }
    }


}
