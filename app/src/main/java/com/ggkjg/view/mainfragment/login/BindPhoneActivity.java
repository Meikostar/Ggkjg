package com.ggkjg.view.mainfragment.login;

import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.base.BuildConfig;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.GlideUtils;
import com.ggkjg.common.utils.LogUtil;
import com.ggkjg.common.utils.RxTimerUtil;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.common.utils.ToastUtil;
import com.ggkjg.http.error.ApiException;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.subscribers.DefaultSingleObserver;

import butterknife.BindView;

/**
 * 绑定手机
 * Created by xld021 on 2018/4/14.
 */

public class BindPhoneActivity extends BaseActivity {
    private static final String TAG = BindPhoneActivity.class.getSimpleName();
    @BindView(R.id.et_bind_phone_phone)
    EditText et_bind_phone_phone;
    @BindView(R.id.et_bind_phone_img_code)
    EditText et_bind_phone_img_code;
    @BindView(R.id.iv_bind_phone_img_code)
    ImageView iv_bind_phone_img_code;
    @BindView(R.id.et_bind_phone_msm_code)
    EditText et_bind_phone_msm_code;
    @BindView(R.id.but_bind_phone_get_code)
    Button but_bind_phone_get_code;
    @BindView(R.id.but_bind_phone_submit)
    Button butSubmit;

    // 倒计时数
    private int mCount = 30;

    @Override
    public int getLayoutId() {
        return R.layout.ui_bind_phone_layout;
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
        bindClickEvent(iv_bind_phone_img_code, () -> {
            String phone = et_bind_phone_phone.getText().toString();
            if (TextUtils.isEmpty(phone)) {
                ToastUtil.showToast("请输入手机号码");
                return;
            }
            String url = BuildConfig.PICTURE_CODE + phone;
            GlideUtils.getInstances().loadRoundCornerImgUnCache(this, iv_bind_phone_img_code, 5, url);
        });
        bindClickEvent(but_bind_phone_get_code, () -> {
            String phone = et_bind_phone_phone.getText().toString();
            String pictureCode = et_bind_phone_img_code.getText().toString();
            if (TextUtils.isEmpty(phone)) {
                ToastUtil.showToast("请输入手机号码");
                return;
            }
            if (TextUtils.isEmpty(pictureCode)) {
                ToastUtil.showToast("验证码不能为空！");
                return;
            }
            getMobileVerifyCode(phone, pictureCode);
        });
        bindClickEvent(butSubmit, () -> {
            String phone = et_bind_phone_phone.getText().toString();
            String pictureCode = et_bind_phone_img_code.getText().toString();
            String smsCode = et_bind_phone_msm_code.getText().toString();
            if (TextUtils.isEmpty(phone)) {
                ToastUtil.showToast("请输入手机号码");
                return;
            }

        });
    }

    private void getMobileVerifyCode(String phone, String positionCode) {
        startCount();
        DataManager.getInstance().getMobileVerifyCode(new DefaultSingleObserver<String>() {
            @Override
            public void onSuccess(String object) {
                ToastUtil.toast("获取验证成功");
                et_bind_phone_msm_code.setText(object);
                LogUtil.i(TAG, "--RxLog-Thread: onSuccess() = " + object);
                Constants.getInstance().save(Constants.COOKIE_KEY,"");
            }

            @Override
            public void onError(Throwable throwable) {
                LogUtil.i(TAG, "--RxLog-Thread: onError() = ");
                Constants.getInstance().save(Constants.COOKIE_KEY,"");
                stopCount();
            }
        }, phone, positionCode);
    }

    /**
     * 短信验证码下发后，倒计时程序
     */
    public void startCount() {
        but_bind_phone_get_code.setClickable(false);
//      but_bind_phone_get_code.setTextColor(Color.parseColor("#d2d2d2"));
        but_bind_phone_get_code.setText("" + mCount + "s");
        RxTimerUtil.interval(1000, number -> {
            mCount--;
            but_bind_phone_get_code.setText("" + mCount + "s");
            if (mCount < 0) {// 计时完毕就停掉计时器，并且重新设置
                but_bind_phone_get_code.setClickable(true);
                but_bind_phone_get_code.setText("获取验证码");
//              but_bind_phone_get_code.setTextColor(Color.parseColor("#1DC2CB"));
                mCount = 30;
                RxTimerUtil.cancel();
            }
        });
    }

    private void stopCount() {
        RxTimerUtil.cancel();
        but_bind_phone_get_code.setClickable(true);
        but_bind_phone_get_code.setText("获取验证码");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxTimerUtil.cancel();
    }
}
