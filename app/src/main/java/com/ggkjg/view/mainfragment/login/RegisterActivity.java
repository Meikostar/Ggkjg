package com.ggkjg.view.mainfragment.login;

import android.content.Intent;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.base.BuildConfig;
import com.ggkjg.common.Constants;
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
import com.ggkjg.view.mainfragment.message.AgreementActivity;
import com.ggkjg.view.mainfragment.shop.CommodityDetailActivity;
import com.ggkjg.view.widgets.autoview.RemoteImageView;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;

import butterknife.BindView;

/**
 * 注册
 * Created by xld021 on 2018/4/14.
 */

public class RegisterActivity extends BaseActivity implements BaseActivity.PermissionsListener {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    public static final String PUSH_CODE = "push_code";//调用者传递的名字
    @BindView(R.id.iv_register_scan_code)
    ImageView iv_register_scan_code;
    @BindView(R.id.tv_register_login_submit)
    TextView tv_register_login_submit;
    @BindView(R.id.iv_input_pwd_status)
    ImageView iv_input_pwd_status;
    @BindView(R.id.et_login_phone)
    EditText et_login_phone;
    @BindView(R.id.et_register_img_code)
    EditText et_register_img_code;
    @BindView(R.id.et_register_msm_code)
    EditText et_register_msm_code;
    @BindView(R.id.et_register_passwords)
    EditText et_register_passwords;
    @BindView(R.id.et_login_push_code)
    EditText et_login_push_code;
    private boolean isShowPwd = false;

    @BindView(R.id.but_register_sms)
    Button butGetSmsCode;
    @BindView(R.id.but_register_submit)
    Button butSubmit;
    @BindView(R.id.iv_register_img_code)
    ImageView iv_register_img_code;
    @BindView(R.id.tv_register_agreement)
    TextView tv_register_agreement;


    private int REQUEST_CODE_SCAN = 111;
    private boolean isScan = false;
    // 倒计时数
    private int mCount = 30;

    @Override
    public int getLayoutId() {
        return R.layout.ui_register_layout;
    }

    @Override
    public void initView() {
        actionbar.setImgStatusBar(R.color.my_color_white);
        StatusBarUtils.StatusBarLightMode(this);
    }

    @Override
    public void initData() {
        long push_code = getIntent().getLongExtra(PUSH_CODE, 0);
        if(push_code > 0){
            et_login_push_code.setText(push_code+"");
        }
    }

    @Override
    public void initListener() {
        bindClickEvent(tv_register_login_submit, () -> {
            if (isTopActivity(0)) {
                finish();
            } else {
                gotoActivity(LoginActivity.class, true);
            }

        });
        bindClickEvent(tv_register_agreement, () -> {
            gotoActivity(AgreementActivity.class);
        });

        bindClickEvent(iv_register_scan_code, () -> {
            if (isPermissioin(CAMERA) && isPermissioin(READ_EXTERNAL_STORAGE)) {
                startScan();
            } else {
                if (!isPermissioin(CAMERA)) {
                    checkPermissioin(CAMERA);
                }
                if (!isPermissioin(READ_EXTERNAL_STORAGE)) {
                    checkPermissioin(READ_EXTERNAL_STORAGE);
                }
                isScan = true;
                setPermissionsListener(this);
            }
        });
        bindClickEvent(iv_input_pwd_status, () -> {
            if (isShowPwd) {
                isShowPwd = false;
                iv_input_pwd_status.setImageResource(R.mipmap.login_chose);
                et_register_passwords.setTransformationMethod(PasswordTransformationMethod.getInstance());
                et_register_passwords.setSelection(et_register_passwords.getText().toString().length());
            } else {
                isShowPwd = true;
                iv_input_pwd_status.setImageResource(R.mipmap.login_open);
                et_register_passwords.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                et_register_passwords.setSelection(et_register_passwords.getText().toString().length());
            }
        }, 100);

        bindClickEvent(butGetSmsCode, () -> {
            String phone = et_login_phone.getText().toString();
            String pictureCode = et_register_img_code.getText().toString();
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
            String phone = et_login_phone.getText().toString();
            String pictureCode = et_register_img_code.getText().toString();
            String smsCode = et_register_msm_code.getText().toString();
            String loginPwd = et_register_passwords.getText().toString();
            String pwdMd5 = MD5Utils.getMD5Str(loginPwd + "hkonline");
            String pushCode = et_login_push_code.getText().toString();
            if (TextUtils.isEmpty(phone)) {
                ToastUtil.showToast("请输入手机号码");
                return;
            }
            if (TextUtils.isEmpty(loginPwd)) {
                ToastUtil.showToast("密码不能为空！");
                return;
            }
            if (TextUtils.isEmpty(pushCode)) {
                pushCode = "";
            }
            registerSubmit(phone, pictureCode, smsCode, pwdMd5, pushCode);
        });
        bindClickEvent(iv_register_img_code, () -> {
            String phone = et_login_phone.getText().toString();
            if (TextUtils.isEmpty(phone)) {
                ToastUtil.showToast("请输入手机号码");
                return;
            }
            String url = BuildConfig.PICTURE_CODE + phone;
            GlideUtils.getInstances().loadRoundCornerImgUnCache(this, iv_register_img_code, 5, url);
        });


    }

    /**
     * 启动扫一扫
     */
    private void startScan() {
        Intent intent = new Intent(this, CaptureActivity.class);
        ZxingConfig config = new ZxingConfig();
//        config.setPlayBeep(false);//是否播放扫描声音 默认为true
//        config.setShake(false);//是否震动  默认为true
//        config.setDecodeBarCode(false);//是否扫描条形码 默认为true
        config.setReactColor(R.color.my_color_00AAED);//设置扫描框四个角的颜色 默认为白色
//        config.setFrameLineColor(R.color.colorAccent);//设置扫描框边框颜色 默认无色
        config.setScanLineColor(R.color.my_color_FABB48);//设置扫描线的颜色 默认白色
//        config.setFullScreenScan(false);//是否全屏扫描  默认为true  设为false则只会在扫描框中扫描
        intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
    }

    @Override
    public void callbackPermissions(String permissions, boolean isSuccess) {
        if (permissions.equals(CAMERA) && !isSuccess) {
            ToastUtil.showToast("请在设置中打开相机权限.");
        } else if (permissions.equals(READ_EXTERNAL_STORAGE) && !isSuccess) {
            ToastUtil.showToast("请在设置中打开读写手机权限.");
        } else if (isScan) {
            startScan();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                if (!TextUtils.isEmpty(content) && content.indexOf("referralCode=") != -1) {
                    content = content.substring(content.indexOf("referralCode=") + 13, content.length());
                    et_login_push_code.setText(content);
                }
                ToastUtil.showToast("扫描结果为：" + content);
            }
        }
    }

    private void getMobileVerifyCode(String phone, String positionCode) {
        startCount();
        DataManager.getInstance().getMobileVerifyCode(new DefaultSingleObserver<String>() {
            @Override
            public void onSuccess(String object) {
                ToastUtil.toast("获取验证成功");
                et_register_msm_code.setText(object);
                LogUtil.i(TAG, "--RxLog-Thread: onSuccess() = " + object);
            }

            @Override
            public void onError(Throwable throwable) {
                LogUtil.i(TAG, "--RxLog-Thread: onError() = ");
                stopCount();
            }
        }, phone, positionCode);
    }

    private void registerSubmit(String phone, String pictureCode, String smsCode, String loginPwd, String pushCode) {
        showLoadDialog();
        DataManager.getInstance().register(new DefaultSingleObserver<HttpResult<Object>>() {
            @Override
            public void onSuccess(HttpResult<Object> object) {
                dissLoadDialog();
                if (object != null) {
                    ToastUtil.toast(object.getMsg());
                    if (object.getStatus() == 1) {
                        onBackPressed();
                    }
                }

            }

            @Override
            public void onError(Throwable throwable) {
                LogUtil.i(TAG, "--RxLog-Thread: onError() = ");
                dissLoadDialog();
            }
        }, phone, pictureCode, smsCode, loginPwd, pushCode);
    }

    /**
     * 短信验证码下发后，倒计时程序
     */
    public void startCount() {
        butGetSmsCode.setClickable(false);
//      butGetSmsCode.setTextColor(Color.parseColor("#d2d2d2"));
        butGetSmsCode.setText("" + mCount + "s");
        RxTimerUtil.interval(1000, number -> {
            mCount--;
            butGetSmsCode.setText("" + mCount + "s");
            if (mCount < 0) {// 计时完毕就停掉计时器，并且重新设置
                butGetSmsCode.setClickable(true);
                butGetSmsCode.setText("获取验证码");
//              butGetSmsCode.setTextColor(Color.parseColor("#1DC2CB"));
                mCount = 30;
                RxTimerUtil.cancel();
            }
        });
    }

    private void stopCount() {
        RxTimerUtil.cancel();
        butGetSmsCode.setClickable(true);
        butGetSmsCode.setText("获取验证码");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxTimerUtil.cancel();
    }
}
