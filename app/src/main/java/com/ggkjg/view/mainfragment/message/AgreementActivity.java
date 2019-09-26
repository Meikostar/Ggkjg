package com.ggkjg.view.mainfragment.message;

import android.icu.util.ULocale;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.common.utils.TextUtil;
import com.ggkjg.common.utils.ToastUtil;
import com.ggkjg.common.utils.WebViewUtil;
import com.ggkjg.dto.AgreeMentDto;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.subscribers.DefaultSingleObserver;

import java.util.Objects;

import butterknife.BindView;

/**
 * 协议
 * Created by xld021 on 2018/4/14.
 */

public class AgreementActivity extends BaseActivity {
    private static final String TAG = AgreementActivity.class.getSimpleName();
    @BindView(R.id.register_agreement_webview)
    WebView webView;

    @Override
    public int getLayoutId() {
        return R.layout.ui_register_agreement_layout;
    }

    @Override
    public void initView() {
        actionbar.setImgStatusBar(R.color.my_color_white);
        StatusBarUtils.StatusBarLightMode(this);
    }
     private String url;
     private String title;
    @Override
    public void initData() {
        url=getIntent().getStringExtra("url");
        title=getIntent().getStringExtra("title");
        if(TextUtil.isEmpty(url)){
            WebViewUtil.setWebView(webView, Objects.requireNonNull(this));
            findAgreeMent();
        }else {
            actionbar.setTitle(title);
            webView.getSettings().setJavaScriptEnabled(true);

            webView.setWebViewClient(new WebViewClient() { //通过webView打开链接，不调用系统浏览器

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    // TODO Auto-generated method stub
                    view.loadUrl(url);
                    return true;
                }

                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    // Toast.makeText(getApplicationContext(), "网络连接失败 ,请连接网络。", Toast.LENGTH_SHORT).show();
                }
            });


            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setBuiltInZoomControls(false);


            if (null != url) {
                webView.loadUrl(url);
            }

            webView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {

                    }
                }
            });
        }

    }

    @Override
    public void initListener() {


    }

    private void findAgreeMent() {
        showLoadDialog();
        DataManager.getInstance().findAgreeMent(new DefaultSingleObserver<AgreeMentDto>() {
            @Override
            public void onSuccess(AgreeMentDto object) {
                if (object != null) {
                    actionbar.setTitle(object.getTitle());
                    WebViewUtil.loadHtml(webView, object.getContent());
                } else {
                    ToastUtil.showToast("未知错误");
                }
                dissLoadDialog();
            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        WebViewUtil.destroyWebView(webView);
    }

}
