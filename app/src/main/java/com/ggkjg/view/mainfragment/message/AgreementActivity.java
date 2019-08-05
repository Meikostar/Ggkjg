package com.ggkjg.view.mainfragment.message;

import android.webkit.WebView;

import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.common.utils.StatusBarUtils;
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

    @Override
    public void initData() {
        WebViewUtil.setWebView(webView, Objects.requireNonNull(this));
        findAgreeMent();
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
