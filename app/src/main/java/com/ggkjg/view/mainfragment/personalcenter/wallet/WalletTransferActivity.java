package com.ggkjg.view.mainfragment.personalcenter.wallet;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.LogUtil;
import com.ggkjg.common.utils.MD5Utils;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.common.utils.ToastUtil;
import com.ggkjg.dto.AccountBalanceDto;
import com.ggkjg.dto.UserInfoDto;
import com.ggkjg.http.error.ApiException;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.response.HttpResult;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.mainfragment.order.PayOrderActivity;
import com.ggkjg.view.mainfragment.settings.SetPayPwdActivity;
import com.ggkjg.view.widgets.InputPwdDialog;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 港豆转让
 */
public class WalletTransferActivity extends BaseActivity {
    @BindView(R.id.tv_count_coin)
    TextView tv_count_coin;
    @BindView(R.id.btn_all_transfer)
    TextView btn_all_transfer;
    @BindView(R.id.ed_phone)
    EditText ed_phone;
    @BindView(R.id.ed_count)
    EditText ed_count;
    @BindView(R.id.ed_remark)
    EditText ed_remark;
    @BindView(R.id.btn_confirm)
    Button btn_confirm;
    String gdCount = "";

    @Override
    public void initListener() {
        bindClickEvent(btn_confirm, () -> {
            dealConfirm();
        });
        bindClickEvent(btn_all_transfer, () -> {
            if (!TextUtils.isEmpty(gdCount)) {
                ed_count.setText(gdCount);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.ui_wallet_transfer_layout;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        actionbar.setImgStatusBar(R.color.my_color_white);
        StatusBarUtils.StatusBarLightMode(this);
        getWalletBalance(true);
    }

    private void getWalletBalance(boolean isLoad) {
        if (isLoad) {
            showLoadDialog();
        }

        DataManager.getInstance().findAccountBalance(new DefaultSingleObserver<List<AccountBalanceDto>>() {
            @Override
            public void onSuccess(List<AccountBalanceDto> object) {
                if (object != null&&object.size()>0) {
                    gdCount = object.get(0).getAvailAmount();
                    tv_count_coin.setText(gdCount);
                }
                dissLoadDialog();
            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();
            }
        }, 1+"");
    }

    private void dealConfirm() {
        if (TextUtils.isEmpty(ed_phone.getText().toString())) {
            ToastUtil.showToast("请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(ed_count.getText().toString())) {
            ToastUtil.showToast("请输入数量");
            return;
        }
        getMemberBaseInfo(new InputPwdDialog.InputPasswordListener() {
            @Override
            public void callbackPassword(String password) {
                HashMap<String, String> map = new HashMap<>();
                map.put("mobileNo", ed_phone.getText().toString());
                map.put("num", ed_count.getText().toString());
                map.put("tradePwd", MD5Utils.getMD5Str(password+Constants.MD5_ADD_STR));
                if (!TextUtils.isEmpty(ed_remark.getText().toString())) {
                    map.put("remark", MD5Utils.getMD5Str(password));
                }

                showLoadDialog();
                DataManager.getInstance().memberTransfer(new DefaultSingleObserver<HttpResult<Object>>() {
                    @Override
                    public void onSuccess(HttpResult<Object> httpResult) {
                        if (httpResult != null && httpResult.getStatus() == 1) {
                            ToastUtil.showToast(httpResult.getMsg());
                            getWalletBalance(false);
                        } else {
                            dissLoadDialog();
                            if (httpResult != null) {
                                ToastUtil.showToast(httpResult.getMsg());
                            }

                        }

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        dissLoadDialog();
                    }
                }, map);
            }
        });

    }

    /**
     * 余额支付时，查询是否设置支付密码
     */
    private void getMemberBaseInfo(InputPwdDialog.InputPasswordListener listener) {
        showLoadDialog();
        DataManager.getInstance().getMemberBaseInfo(new DefaultSingleObserver<UserInfoDto>() {
            @Override
            public void onSuccess(UserInfoDto userInfoDto) {
                dissLoadDialog();
                if (!TextUtils.isEmpty(userInfoDto.getTradePwd())) {
                    InputPwdDialog inputPasswordDialog = new InputPwdDialog(WalletTransferActivity.this, listener);
                    inputPasswordDialog.show();
                } else {
                    gotoActivity(SetPayPwdActivity.class);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();

            }
        });
    }
}
