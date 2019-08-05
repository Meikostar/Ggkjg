package com.ggkjg.view.mainfragment.settings;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.base.BuildConfig;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.CountDownTimerUtils;
import com.ggkjg.common.utils.GlideUtils;
import com.ggkjg.common.utils.LogUtil;
import com.ggkjg.common.utils.MD5Utils;
import com.ggkjg.common.utils.RxTimerUtil;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.common.utils.ToastUtil;
import com.ggkjg.http.error.ApiException;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.response.HttpResult;
import com.ggkjg.http.subscribers.DefaultSingleObserver;

import java.util.HashMap;

import butterknife.BindView;


/**
 * 个人中心 --> 设置 -->  支付密码 -->  设置支付密码
 * Created by dahai on 2018/12/08.
 */
public class SetPayPwdActivity extends BaseActivity {
    private static final String TAG = SetPayPwdActivity.class.getSimpleName();

    @BindView(R.id.et_set_pay_pwd_1)
    EditText et_set_pay_pwd_1;

    @BindView(R.id.et_set_pay_pwd_2)
    EditText et_set_pay_pwd_2;

    @BindView(R.id.btn_set_pay_pwd_ok)
    Button btn_set_pay_pwd_ok;


    @Override
    public int getLayoutId() {
        return R.layout.ui_setting_set_pay_pwd_layout;
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

        bindClickEvent(btn_set_pay_pwd_ok, () -> {
            onSubmit();
        });
    }


    private void onSubmit() {

        if (TextUtils.isEmpty(et_set_pay_pwd_1.getText().toString().trim())) {
            ToastUtil.toast("密码不能为空！");
            return;
        }
        if (et_set_pay_pwd_1.getText().toString().trim().length() != 6) {
            ToastUtil.toast("请输入6位数字密码！");
            return;
        }
        if (!et_set_pay_pwd_1.getText().toString().equals(et_set_pay_pwd_2.getText().toString())) {
            ToastUtil.showToast("两次密码不一致");
            return;
        }
        setPayPassword(et_set_pay_pwd_1.getText().toString());
    }

    private void setPayPassword(String newPwd) {
        showLoadDialog();
        String newPwdMd5 = MD5Utils.getMD5Str(newPwd + "hkonline");
        DataManager.getInstance().setTradePwd(new DefaultSingleObserver<HttpResult<Object>>() {
            @Override
            public void onSuccess(HttpResult<Object> object) {
                dissLoadDialog();
                ToastUtil.showToast(object.getMsg());
                if (object.getStatus() == 1) {
                    onBackPressed();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();
            }
        }, newPwdMd5);
    }



}
