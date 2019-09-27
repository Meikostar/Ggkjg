package com.ggkjg.view.mainfragment.login;

import android.text.TextUtils;
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
import com.ggkjg.common.utils.GlideUtils;
import com.ggkjg.common.utils.LogUtil;
import com.ggkjg.common.utils.MD5Utils;
import com.ggkjg.common.utils.RxTimerUtil;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.common.utils.StringUtil;
import com.ggkjg.common.utils.TextUtil;
import com.ggkjg.common.utils.ToastUtil;
import com.ggkjg.http.error.ApiException;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.response.HttpResult;
import com.ggkjg.http.subscribers.DefaultSingleObserver;

import java.util.HashMap;

import butterknife.BindView;

/**
 * 忘记密码
 * Created by xld021 on 2018/4/14.
 */

public class ForgetPayActivity extends BaseActivity {
    private static final String TAG = ForgetPayActivity.class.getSimpleName();
    @BindView(R.id.et_forget_pwd_phone)
    EditText et_forget_pwd_phone;
    @BindView(R.id.et_forget_img_code)
    EditText et_forget_img_code;
    @BindView(R.id.iv_forget_img_code)
    ImageView iv_forget_img_code;
    @BindView(R.id.et_forget_pwd_msm_code)
    EditText et_forget_pwd_msm_code;
    @BindView(R.id.but_forget_pwd_msm_code)
    Button but_forget_pwd_msm_code;
    @BindView(R.id.but_forget_pwd_submit)
    Button but_forget_pwd_submit;
    @BindView(R.id.tv_forget_pwd_login)
    TextView tv_forget_pwd_login;
    @BindView(R.id.iv_input_pwd_status)
    ImageView iv_input_pwd_status;
    @BindView(R.id.et_forget_pwd_passwords)
    EditText et_forget_pwd_passwords;
    private boolean isShowPwd;
    // 倒计时数
    private int mCount = 60;

    @Override
    public int getLayoutId() {
        return R.layout.ui_forget_pwd_layout;
    }

    @Override
    public void initView() {
        actionbar.setImgStatusBar(R.color.my_color_white);
        actionbar.setTitle("找回支付密码");
        StatusBarUtils.StatusBarLightMode(this);
    }

    @Override
    public void initData() {
    }
    private void modifyTradePwd(HashMap<String, String> map) {
        showLoadDialog();
        DataManager.getInstance().modifyTradePwd(new DefaultSingleObserver<HttpResult<Object>>() {
            @Override
            public void onSuccess(HttpResult<Object> object) {
                ToastUtil.showToast(object.getMsg());
                dissLoadDialog();
              finish();
            }

            @Override
            public void onError(Throwable throwable) {
                ToastUtil.toast(ApiException.getShowToast(throwable));
                dissLoadDialog();
            }
        }, map);

    }
    @Override
    public void initListener() {
        tv_forget_pwd_login.setVisibility(View.GONE);
        bindClickEvent(tv_forget_pwd_login, () -> {
            if (isTopActivity(1)) {
                finish();
            } else {
                gotoActivity(LoginActivity.class, true);
            }
        });
        bindClickEvent(iv_input_pwd_status, () -> {
            if (isShowPwd) {
                isShowPwd = false;
                iv_input_pwd_status.setImageResource(R.mipmap.login_chose);
                et_forget_pwd_passwords.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                et_forget_pwd_passwords.setSelection(et_forget_pwd_passwords.getText().toString().length());
            } else {
                isShowPwd = true;
                iv_input_pwd_status.setImageResource(R.mipmap.login_open);
                et_forget_pwd_passwords.setTransformationMethod(PasswordTransformationMethod.getInstance());
                et_forget_pwd_passwords.setSelection(et_forget_pwd_passwords.getText().toString().length());
            }
        });

        bindClickEvent(iv_forget_img_code, () -> {
            String phone = et_forget_pwd_phone.getText().toString();
            if (TextUtils.isEmpty(phone)) {
                ToastUtil.showToast("请输入手机号码");
                return;
            }
            if (!StringUtil.isMobileNO(phone)) {
                ToastUtil.showToast("请输入有效的手机号");
                return;
            }
            String url = BuildConfig.PICTURE_CODE + phone;
            GlideUtils.getInstances().loadRoundCornerImgUnCache(this, iv_forget_img_code, 5, url);
        });

        bindClickEvent(but_forget_pwd_msm_code, () -> {
            String phone = et_forget_pwd_phone.getText().toString();
            String pictureCode = et_forget_img_code.getText().toString();
            if (TextUtils.isEmpty(phone)) {
                ToastUtil.showToast("请输入手机号码");
                return;
            }
            if (!StringUtil.isMobileNO(phone)) {
                ToastUtil.showToast("请输入有效手机号");
                return;
            }
            if (TextUtils.isEmpty(pictureCode)) {
                ToastUtil.showToast("验证码不能为空！");
                return;
            }
            getMobileVerifyCode(phone, pictureCode);
        });

        bindClickEvent(but_forget_pwd_submit, () -> {
            String phone = et_forget_pwd_phone.getText().toString();
            String pictureCode = et_forget_img_code.getText().toString();
            String smsCode = et_forget_pwd_msm_code.getText().toString();
            String newPwd = et_forget_pwd_passwords.getText().toString();
            if (TextUtils.isEmpty(phone)) {
                ToastUtil.showToast("请输入手机号码");
                return;
            }
            if (!StringUtil.isMobileNO(phone)) {
                ToastUtil.showToast("请输入有效手机号");
                return;
            }
            if (TextUtils.isEmpty(newPwd)) {
                ToastUtil.showToast("密码不能为空！");
                return;
            }
            String pwdMd5 = MD5Utils.getMD5Str(newPwd + "hkonline");
            onSubmit(phone, pictureCode, smsCode, pwdMd5);
        });

    }

    private void getMobileVerifyCode(String phone, String positionCode) {

        startCount();
        DataManager.getInstance().getMobileVerifyCode(new DefaultSingleObserver<String>() {
            @Override
            public void onSuccess(String object) {
                ToastUtil.toast("获取验证成功");
//                et_forget_pwd_msm_code.setText(object);
                LogUtil.i(TAG, "--RxLog-Thread: onSuccess() = " + object);
            }

            @Override
            public void onError(Throwable throwable) {
                LogUtil.i(TAG, "--RxLog-Thread: onError() = ");
                ToastUtil.toast(ApiException.getShowToast(throwable));
                stopCount();
            }
        }, phone, positionCode);
    }

    /**
     * 短信验证码下发后，倒计时程序
     */
    public void startCount() {
        but_forget_pwd_msm_code.setClickable(false);
//      but_forget_pwd_msm_code.setTextColor(Color.parseColor("#d2d2d2"));
        but_forget_pwd_msm_code.setText("" + mCount + "s");
        RxTimerUtil.interval(1000, number -> {
            mCount--;
            but_forget_pwd_msm_code.setText("" + mCount + "s");
            if (mCount < 0) {// 计时完毕就停掉计时器，并且重新设置
                but_forget_pwd_msm_code.setClickable(true);
                but_forget_pwd_msm_code.setText("获取验证码");
//              but_forget_pwd_msm_code.setTextColor(Color.parseColor("#1DC2CB"));
                mCount = 60;
                RxTimerUtil.cancel();
            }
        });
    }

    private void stopCount() {
        RxTimerUtil.cancel();
        but_forget_pwd_msm_code.setClickable(true);
        but_forget_pwd_msm_code.setText("获取验证码");
    }

    private void onSubmit(String phone, String pictureCode, String smsCode, String newPwd) {

        HashMap<String, String> map = new HashMap<>();

        if(TextUtil.isNotEmpty(phone)){
            map.put("phone", phone);
        }
        if(TextUtil.isNotEmpty(pictureCode)){
            map.put("imageCode", pictureCode);
        }
        map.put("msgCode", smsCode);
        map.put("newPwd", newPwd);
        modifyTradePwd(map);


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxTimerUtil.cancel();
    }
}
