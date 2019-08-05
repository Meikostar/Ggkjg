package com.ggkjg.view.mainfragment.login;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.InstallWeChatOrAliPay;
import com.ggkjg.common.utils.LogUtil;
import com.ggkjg.common.utils.MD5Utils;
import com.ggkjg.common.utils.ToastUtil;
import com.ggkjg.dto.LoginDto;
import com.ggkjg.http.error.ApiException;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.MainActivity;
import com.lzy.imagepicker.ImagePicker;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import butterknife.BindView;

/**
 * 商城快讯
 * Created by xld021 on 2018/4/14.
 */

public class LoginActivity extends BaseActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    @BindView(R.id.login_actionbar_back)
    ImageView login_actionbar_back;
    @BindView(R.id.et_login_phone)
    EditText et_login_phone;
    @BindView(R.id.et_login_passwords)
    EditText et_login_passwords;
    @BindView(R.id.iv_input_pwd_status)
    ImageView iv_input_pwd_status;
    @BindView(R.id.but_login_submit)
    Button buSubmit;
    @BindView(R.id.tv_login_register)
    TextView tv_login_register;
    @BindView(R.id.tv_login_forgot_password)
    TextView tv_login_forgot_password;
    @BindView(R.id.tv_login_wechat)
    TextView tv_login_wechat;
    private boolean isShowPwd = false;
    public static final String PAGE_INDEX = "page_index";//调用者传递的名字
    private int page_index = 0;//页面索引

    private IntentFilter mIntentFilter = null;

    private MyBroadcastReceiver mMyBroadcastRecvier = null;

    @Override
    public int getLayoutId() {
        return R.layout.ui_login_layout;
    }

    @Override
    public void initView() {
        initBroadcast();
    }

    @Override
    public void initData() {
        page_index = getIntent().getIntExtra(PAGE_INDEX, 0);
    }


    @Override
    public void initListener() {
        bindClickEvent(login_actionbar_back, () -> {
            onBackPressed();
        });
        bindClickEvent(iv_input_pwd_status, () -> {
            if (isShowPwd) {
                isShowPwd = false;
                iv_input_pwd_status.setImageResource(R.mipmap.login_open);
                et_login_passwords.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                et_login_passwords.setSelection(et_login_passwords.getText().toString().length());
            } else {
                isShowPwd = true;
                iv_input_pwd_status.setImageResource(R.mipmap.login_chose);
                et_login_passwords.setTransformationMethod(PasswordTransformationMethod.getInstance());
                et_login_passwords.setSelection(et_login_passwords.getText().toString().length());
            }
        });
        bindClickEvent(buSubmit, () -> {
            String phone = et_login_phone.getText().toString();
            String pwd = et_login_passwords.getText().toString();
            if (TextUtils.isEmpty(phone)) {
                ToastUtil.showToast("请输入手机号码");
                return;
            }
            if (TextUtils.isEmpty(pwd)) {
                ToastUtil.showToast("密码不能为空！");
                return;
            }
            String pwdMd5 = MD5Utils.getMD5Str(pwd + "hkonline");
            onSubmit(phone, pwdMd5);
        });
        bindClickEvent(tv_login_register, () -> {
            gotoActivity(RegisterActivity.class);
        });
        bindClickEvent(tv_login_forgot_password, () -> {
            gotoActivity(ForgetPwdActivity.class);
        });
        bindClickEvent(tv_login_wechat, () -> {

            if (!InstallWeChatOrAliPay.getInstance().isWeixinAvilible(this)) {
                ToastUtil.showToast("您还未安装微信客户端");
                return;
            }
            final IWXAPI api = WXAPIFactory.createWXAPI(this, null);
            // 将该app注册到微信
            api.registerApp(Constants.WX_APP_ID);

            // send oauth request
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo_test";
            api.sendReq(req);
//            gotoActivity(BindPhoneActivity.class);
        });
    }

    private void onSubmit(String phone, String loginPwd) {
        showLoadDialog();
        DataManager.getInstance().phoneLogin(new DefaultSingleObserver<LoginDto>() {
            @Override
            public void onSuccess(LoginDto object) {
                LogUtil.i(TAG, "--RxLog-Thread: onSuccess() = " + object);
                loginSuccessSaveUserInfo(object);
                dissLoadDialog();
            }

            @Override
            public void onError(Throwable throwable) {
                LogUtil.i(TAG, "--RxLog-Thread: onError() = " );
                ToastUtil.toast(ApiException.getShowToast(throwable));
                dissLoadDialog();
            }
        }, phone, loginPwd);
    }

    private void loginSuccessSaveUserInfo(LoginDto loginDto) {
        if (loginDto != null) {
            Constants.getInstance().saveBoolean(Constants.NO_LOGIN_SUCCESS, true);
            Constants.getInstance().save(Constants.USER_SERVLET_TOKEN, loginDto.getJSESSIONID());
            Constants.getInstance().save(Constants.USER_SHIRO_TOKEN, loginDto.getJSESSIONID_SHIRO());
            Constants.getInstance().save(Constants.USER_PHONE, loginDto.getMobileNo());
            Constants.getInstance().save(Constants.USER_NICK_NAME, loginDto.getNickName());
            Constants.getInstance().save(Constants.USER_HEAD_IMG, loginDto.getHeadImg());
            Constants.getInstance().save(Constants.USER_ID, loginDto.getId());
            Constants.getInstance().save(Constants.USER_MEMBER_LEVEL, loginDto.getMemberLevel());
            Constants.getInstance().save(Constants.USER_AUTH_STATE, loginDto.getAuthState());
            Constants.getInstance().save(Constants.SYS_PATH, loginDto.getSysPath());
        }
        Intent intent = new Intent();
        intent.putExtra(MainActivity.LOGIN_PAGE_INDEX, page_index);
        setResult(RESULT_OK, intent);
        finish();
    }



    private void initBroadcast() {
        //过滤器
        mIntentFilter = new IntentFilter(Constants.WX_LOGIN_BROADCAST);
        //创建广播接收者的对象
        mMyBroadcastRecvier = new MyBroadcastReceiver();
        //注册广播接收者的对象
        registerReceiver(mMyBroadcastRecvier, mIntentFilter);

    }

    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String code = intent.getStringExtra(Constants.INTENT_CODE);
                LogUtil.d(TAG, "Code==MyBroadcastReceiver=" + code);
//                loginRequest(code);
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        Constants.getInstance().save(Constants.COOKIE_KEY, "");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mMyBroadcastRecvier != null){
            unregisterReceiver(mMyBroadcastRecvier);
        }

    }
}
