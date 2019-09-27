package com.ggkjg.view.mainfragment.settings;


import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
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
import com.ggkjg.common.utils.RxTimerUtil;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.common.utils.ToastUtil;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.response.HttpResult;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.widgets.BaseDialog;
import com.ggkjg.view.widgets.ConfirmDialog;

import java.util.HashMap;

import butterknife.BindView;


/**
 * 个人中心 --> 设置 -->  修改手机号
 * Created by dahai on 2018/12/08.
 */
public class UpdatePhoneActivity extends BaseActivity {
    @BindView(R.id.img_phone_code)
    ImageView img_phone_code;
    @BindView(R.id.ed_phone)
    EditText ed_phone;
    @BindView(R.id.ed_img_code)
    EditText ed_phone_img_code;
    @BindView(R.id.ed_phone_code)
    EditText ed_phone_code;
    @BindView(R.id.tv_get_code)
    TextView tv_get_code;
    @BindView(R.id.btn_update_phone_ok)
    Button btn_update_phone_ok;

    // 倒计时数
    private int mCount = 60;

    @Override
    public void initListener() {
        bindClickEvent(img_phone_code, () -> {
            String phone = ed_phone.getText().toString();
            if (TextUtils.isEmpty(phone)) {
                ToastUtil.showToast("请输入手机号码");
                return;
            }
            String url = BuildConfig.PICTURE_CODE + phone;
            GlideUtils.getInstances().loadRoundCornerImgUnCache(this, img_phone_code, 0, url);
        });
        bindClickEvent(tv_get_code, () -> {
            String phone = ed_phone.getText().toString();
            String pictureCode = ed_phone_img_code.getText().toString();
            if (TextUtils.isEmpty(phone)) {
                ToastUtil.showToast("请输入手机号码");
                return;
            }
            if (TextUtils.isEmpty(pictureCode)) {
                ToastUtil.showToast("请输入图形验证码！");
                return;
            }
            getMobileVerifyCode(phone, pictureCode);
        });
        bindClickEvent(btn_update_phone_ok, () -> {
            String phone = ed_phone.getText().toString();
            String pictureCode = ed_phone_img_code.getText().toString();
            String phoneCode = ed_phone_code.getText().toString();
            if (TextUtils.isEmpty(phone)) {
                ToastUtil.showToast("请输入手机号码");
                return;
            }
            if (TextUtils.isEmpty(pictureCode)) {
                ToastUtil.showToast("请输入图形验证码！");
                return;
            }
            if (TextUtils.isEmpty(phoneCode)) {
                ToastUtil.showToast("请输入短信验证码！");
                return;
            }
            settingMobileNo();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.ui_setting_update_phone_layout;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    private void getMobileVerifyCode(String phone, String positionCode) {
        startCount();
        DataManager.getInstance().getMobileVerifyCode(new DefaultSingleObserver<String>() {
            @Override
            public void onSuccess(String object) {
                ToastUtil.toast("获取验证成功");
                ed_phone_code.setText(object);
            }

            @Override
            public void onError(Throwable throwable) {
                stopCount();
                ToastUtil.showToast("图形验证码有误");
            }
        }, phone, positionCode);
    }

    /**
     * 短信验证码下发后，倒计时程序
     */
    public void startCount() {
        tv_get_code.setClickable(false);
        mCount = 30;
        tv_get_code.setText("" + mCount + "s");
        RxTimerUtil.interval(1000, number -> {
            mCount--;
            tv_get_code.setText("" + mCount + "s");
            if (mCount < 0) {// 计时完毕就停掉计时器，并且重新设置
                tv_get_code.setClickable(true);
                tv_get_code.setText("获取验证码");
//              butGetSmsCode.setTextColor(Color.parseColor("#1DC2CB"));
                mCount = 30;
                RxTimerUtil.cancel();
            }
        });
    }

    private void stopCount() {
        RxTimerUtil.cancel();
        tv_get_code.setClickable(true);
        tv_get_code.setText("获取验证码");
    }

    private void settingMobileNo() {
        HashMap<String, String> map = new HashMap<>();
        map.put("mobileNo", ed_phone.getText().toString());
        map.put("pictureCode", ed_phone_img_code.getText().toString());
        map.put("smsCode", ed_phone_code.getText().toString());
        showLoadDialog();
        DataManager.getInstance().settingMobileNo(new DefaultSingleObserver<HttpResult<Object>>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                dissLoadDialog();
                if (httpResult != null) {
                    ToastUtil.showToast(httpResult.getMsg());
                    if (httpResult.getStatus() == 1) {
                        setResult(Activity.RESULT_OK);
                        finish();
                    }
                }
            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();
            }
        }, map);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxTimerUtil.cancel();
    }

}
