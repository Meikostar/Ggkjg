package com.ggkjg.view.mainfragment.settings;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.common.utils.MD5Utils;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.common.utils.ToastUtil;
import com.ggkjg.http.error.ApiException;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.response.HttpResult;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.mainfragment.login.ForgetPayActivity;

import java.util.HashMap;

import butterknife.BindView;

public class ModifyPayPwdActivity  extends BaseActivity {
    @BindView(R.id.btn_update_phone_ok)
    Button   btnSure;
    @BindView(R.id.ed_old_pwd)
    EditText edOldPwd;
    @BindView(R.id.ed_new_pwd)
    EditText edNewPwd;
    @BindView(R.id.ed_new_pwd_repeat)
    EditText ed_new_pwd_repeat;
    @BindView(R.id.tv_forget)
    TextView tv_forget;

    @Override
    public int getLayoutId() {
        return R.layout.ui_modify_pay_pwd_layout;
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
        tv_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ModifyPayPwdActivity.this,ForgetPayActivity.class));
            }
        });
        bindClickEvent(btnSure, () -> {
            String oldPwdStr = edOldPwd.getText().toString();
            String newPwdStr = edNewPwd.getText().toString();
            String newPwdRepeatStr = ed_new_pwd_repeat.getText().toString();
            if (TextUtils.isEmpty(oldPwdStr)) {
                ToastUtil.showToast("请输入原支付密码");
                return;
            }
            if (TextUtils.isEmpty(newPwdStr)) {
                ToastUtil.showToast("请输入新支付密码");
                return;
            }
            if (TextUtils.isEmpty(newPwdRepeatStr)) {
                ToastUtil.showToast("请输入新支付密码确定");
                return;
            }
            if (!newPwdStr.equals(newPwdRepeatStr)) {
                ToastUtil.showToast("新支付密码与确定新支付密码不同");
                return;
            }
            String oldPwdMD5 = MD5Utils.getMD5Str(oldPwdStr + "hkonline");
            String newPwdMD5 = MD5Utils.getMD5Str(newPwdStr + "hkonline");
            HashMap<String, String> map = new HashMap<>();
            map.put("oldPwd", oldPwdMD5);
            map.put("newPwd", newPwdMD5);
            modifyTradePwd(map);
        });
    }

    private void modifyTradePwd(HashMap<String, String> map) {
        showLoadDialog();
        DataManager.getInstance().modifyTradePwd(new DefaultSingleObserver<HttpResult<Object>>() {
            @Override
            public void onSuccess(HttpResult<Object> object) {
                ToastUtil.showToast(object.getMsg());
                dissLoadDialog();
                if (object.getStatus() == 1) {
                    onBackPressed();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                ToastUtil.toast(ApiException.getShowToast(throwable));
                dissLoadDialog();
            }
        }, map);

    }
}
