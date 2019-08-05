package com.ggkjg.view.mainfragment.settings;


import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.common.utils.LogUtil;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.common.utils.ToastUtil;
import com.ggkjg.http.error.ApiException;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.response.HttpResult;
import com.ggkjg.http.subscribers.DefaultSingleObserver;

import java.util.HashMap;
import butterknife.BindView;

/**
 * 个人中心 --> 设置 -->  用户名
 * Created by dahai on 2018/12/08.
 */
public class UserNameActivity extends BaseActivity {
    private static final String TAG = UserNameActivity.class.getSimpleName();
    @BindView(R.id.et_setting_nickname)
    EditText et_setting_nickname;
    @BindView(R.id.btn_setting_sure)
    Button btnSure;
    String nickName;

    @Override
    public int getLayoutId() {
        return R.layout.ui_setting_nickname;
    }

    @Override
    public void initView() {
        actionbar.setImgStatusBar(R.color.my_color_white);
        StatusBarUtils.StatusBarLightMode(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        bindClickEvent(btnSure, () -> {
            nickName = et_setting_nickname.getText().toString().trim();
            if (TextUtils.isEmpty(nickName)) {
                ToastUtil.showToast("请输入昵称");
                return;
            }
            modifyUserInfo(nickName);
        });
    }

    private void modifyUserInfo(String nickName) {
        showLoadDialog();
        DataManager.getInstance().modifyNickName(new DefaultSingleObserver<HttpResult<Object>>() {
            @Override
            public void onSuccess(HttpResult<Object> object) {
                ToastUtil.showToast(object.getMsg());
                dissLoadDialog();
                onBackPressed();
            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();
            }
        }, nickName);
    }
}
