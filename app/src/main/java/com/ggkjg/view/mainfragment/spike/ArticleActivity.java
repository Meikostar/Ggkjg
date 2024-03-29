package com.ggkjg.view.mainfragment.spike;

import android.webkit.WebView;

import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.common.utils.TextUtil;
import com.ggkjg.common.utils.ToastUtil;
import com.ggkjg.common.utils.WebViewUtil;
import com.ggkjg.dto.AgreeMentDto;
import com.ggkjg.dto.ArticleDto;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.subscribers.DefaultSingleObserver;

import java.util.Objects;

import butterknife.BindView;

/**
 * 协议
 * Created by xld021 on 2018/4/14.
 */

public class ArticleActivity extends BaseActivity {
    private static final String TAG = ArticleActivity.class.getSimpleName();
    @BindView(R.id.register_agreement_webview)
    WebView webView;

    @Override
    public int getLayoutId() {
        return R.layout.ui_article_layout;
    }

    @Override
    public void initView() {
        id=getIntent().getStringExtra("id");
        url=getIntent().getStringExtra("url");
        actionbar.setImgStatusBar(R.color.my_color_white);
        WebViewUtil.setWebView(webView, Objects.requireNonNull(this));
        if(TextUtil.isNotEmpty(url)){
        }else {
            findAgreeMent();
        }
        StatusBarUtils.StatusBarLightMode(this);
    }
   private String id;
   private String url;
    @Override
    public void initData() {


    }

    @Override
    public void initListener() {

    }

    private void findAgreeMent() {
        showLoadDialog();
        DataManager.getInstance().findCommercialCollegeDetail(new DefaultSingleObserver<ArticleDto>() {
            @Override
            public void onSuccess(ArticleDto object) {
                if (object != null) {
                    actionbar.setTitle(object.cmsTitle);
                    WebViewUtil.loadHtml(webView, object.cmsContent);
                } else {
                    ToastUtil.showToast("未知错误");
                }
                dissLoadDialog();
            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();
            }
        },id);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        WebViewUtil.destroyWebView(webView);
    }

}
