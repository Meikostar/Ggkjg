package com.ggkjg.view.mainfragment.message;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.base.BuildConfig;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.common.utils.TextUtil;
import com.ggkjg.common.utils.WebViewUtil;
import com.ggkjg.dto.DetailDto;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.widgets.autoview.ActionbarView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 商城快讯
 * Created by xld021 on 2018/4/14.
 */

public class ShopMessageActivity extends BaseActivity {
    private static final String TAG        = ShopMessageActivity.class.getSimpleName();
    public static final  String MESSAGE_ID = "message_id";//调用者传递的名字
    @BindView(R.id.custom_action_bar)
    ActionbarView customActionBar;
    @BindView(R.id.tv_title)
    TextView      tvTitle;
    @BindView(R.id.tv_time)
    TextView      tvTime;
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
        findMemberQrCode();
        final String url = BuildConfig.BASE_URL + "cmsinfo/cmsinfo/CmsInfo/readDetail?id=" + message_id;


    }

    @Override
    public void initListener() {

    }

    private void findMemberQrCode() {
        showLoadDialog();
        DataManager.getInstance().readDetail(new DefaultSingleObserver<DetailDto>() {
            @Override
            public void onSuccess(DetailDto imgUrl) {
                if(TextUtil.isNotEmpty(imgUrl.cmsTitle)){
                    tvTitle.setText(imgUrl.cmsTitle);
                }
                if(TextUtil.isNotEmpty(imgUrl.createDate)){
                    tvTime.setText(imgUrl.createDate);
                }
                if (imgUrl != null && TextUtil.isNotEmpty(imgUrl.cmsContentz)) {

                    WebViewUtil.setWebView(webView, Objects.requireNonNull(ShopMessageActivity.this));
                    WebViewUtil.loadHtml(webView, imgUrl.cmsContentz);
                }
                dissLoadDialog();

            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();
            }
        }, message_id + "");
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
