package com.ggkjg.view.mainfragment.personalcenter.wallet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.InstallWeChatOrAliPay;
import com.ggkjg.common.utils.LogUtil;
import com.ggkjg.common.utils.MD5Utils;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.common.utils.TextUtil;
import com.ggkjg.common.utils.ToastUtil;
import com.ggkjg.common.utils.pay.PayResultListener;
import com.ggkjg.common.utils.pay.PayUtils;
import com.ggkjg.db.bean.WEIXINREQ;
import com.ggkjg.dto.RechargeDto;
import com.ggkjg.http.error.ApiException;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.response.HttpResult;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.mainfragment.order.PayOrderActivity;
import com.ggkjg.view.widgets.RechargeDialog;

import java.util.HashMap;

import butterknife.BindView;


/**
 * 钱包充值
 */
public class WalletRechargeActivity extends BaseActivity {
    private static final String TAG = "WalletRechargeActivity";
    public static final int WX_PAY = 2;
    public static final int ZFB_PAY = 3;
    @BindView(R.id.btn_recharge_sure)
    Button mSureBtn;
    @BindView(R.id.img_recharge_alipay)
    ImageView imgAlipay;
    @BindView(R.id.img_recharge_wpy)
    ImageView imgWpay;
    @BindView(R.id.ll_recharge_weixin)
    LinearLayout ll_recharge_weixin;
    @BindView(R.id.ll_recharge_alipay)
    LinearLayout ll_recharge_alipay;
    @BindView(R.id.ed_money)
    EditText ed_money;

    RechargeDialog tipDialog = null;
    private int currentPayWay = WX_PAY;
    private MyBroadcastReceiver mRecvier = null;
    private IntentFilter mIntentFilter;

    @Override
    public void initListener() {
        bindClickEvent(mSureBtn, () -> {
            String moneyStr = ed_money.getText().toString();
            if (TextUtils.isEmpty(moneyStr)) {
                ToastUtil.showToast("请输入金额");
                return;
            }
            if (currentPayWay == WX_PAY && !InstallWeChatOrAliPay.getInstance().isWeixinAvilible(this)) {
                ToastUtil.showToast("请安装微信");
                return;
            }

            if (currentPayWay == ZFB_PAY && !InstallWeChatOrAliPay.getInstance().checkAliPayInstalled(this)) {
                ToastUtil.showToast("请安装支付宝");
                return;
            }
            if(currentPayWay==WX_PAY){
                submitWxOrder(moneyStr);
            }else {
                getRechargeOrder(moneyStr);
            }

//            showTipDialog();
        });
        bindClickEvent(ll_recharge_weixin, () -> {
            currentPayWay = WX_PAY;
            imgWpay.setSelected(true);
            imgAlipay.setSelected(false);
        });
        bindClickEvent(ll_recharge_alipay, () -> {
            currentPayWay = ZFB_PAY;
            imgAlipay.setSelected(true);
            imgWpay.setSelected(false);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.ui_wallet_recharge_layout;
    }

    @Override
    public void initView() {
        imgWpay.setSelected(true);
        initBroadcast();
    }

    @Override
    public void initData() {
        actionbar.setImgStatusBar(R.color.my_color_white);
        StatusBarUtils.StatusBarLightMode(this);
    }

    private void showTipDialog(boolean isSuccess) {
        if (isSuccess) {
            tipDialog = new RechargeDialog(WalletRechargeActivity.this, Constants.RECHARGE_TYPE_SUCCESS);
            ed_money.setText("");
        } else {
            tipDialog = new RechargeDialog(WalletRechargeActivity.this, Constants.RECHARGE_TYPE_FAIL);
        }
        tipDialog.show();
    }
    private void submitWxOrder(String money) {

        DataManager.getInstance().memberWxRecharge(new DefaultSingleObserver<HttpResult<WEIXINREQ>>() {
            @Override
            public void onSuccess(HttpResult<WEIXINREQ>  httpResult) {
                dissLoadDialog();
                if (httpResult != null && httpResult.getStatus() == 1) {

                    PayUtils.getInstances().WXPay(WalletRechargeActivity.this, httpResult.getData());



                } else {
                    if (httpResult != null) {
                        ToastUtil.showToast(httpResult.getMsg());
                    }

                }

            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();
                ToastUtil.showToast(ApiException.getShowToast(throwable));
            }
        }, currentPayWay + "", money);
    }
    private void getRechargeOrder(String money) {
        showLoadDialog();
        DataManager.getInstance().memberRecharge(new DefaultSingleObserver<RechargeDto>() {
            @Override
            public void onSuccess(RechargeDto rechargeDto) {
                dissLoadDialog();
                if (currentPayWay == WX_PAY) { //微信支付
//                    payOrderInfo(rechargeOrderDto.getOrder_no());
                } else {

                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(rechargeDto.getOrderString());         //要跳转的网页
                    intent.setData(content_url);
                    intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
                    startActivity(intent);
//                    payZFBOrderInfo(rechargeDto.getOrderString());
                }

            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();
                ToastUtil.showToast(ApiException.getShowToast(throwable));
            }
        }, currentPayWay + "", money);
    }

//    private void payOrderInfo(String order_no) {
//        HashMap<String, String> maps = new HashMap<>();
//        maps.put("order_no", order_no);
//        showLoadDialog();
//        DataManager.getInstance().payOrderInfo(new DefaultSingleObserver<RechargeOrderNoDto>() {
//            @Override
//            public void onSuccess(RechargeOrderNoDto orderNoDto) {
//                dissLoadDialog();
//                PayUtils.getInstances().WXPay(WalletRechargeActivity.this, orderNoDto);
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//                dissLoadDialog();
//                ToastUtil.showToast(ApiException.getHttpExceptionMessage(throwable));
//            }
//        }, maps);
//    }

    private void payZFBOrderInfo(String order_no) {

        PayUtils.getInstances().zfbPaySync(WalletRechargeActivity.this, order_no, new PayResultListener() {
            @Override
            public void zfbPayOk(boolean payOk) {
                showTipDialog(payOk);
            }

            @Override
            public void wxPayOk(boolean payOk) {

            }
        });
//        HashMap<String, String> map = new HashMap<>();
//        map.put("order_no[0]", order_no);
//        showLoadDialog();
//        DataManager.getInstance().payZFBOrderInfo(new DefaultSingleObserver<String>() {
//            @Override
//            public void onSuccess(String signStr) {
//                dissLoadDialog();
//                PayUtils.getInstances().zfbPaySync(WalletRechargeActivity.this, signStr, new PayResultListener() {
//                    @Override
//                    public void zfbPayOk(boolean payOk) {
//                        showTipDialog(payOk);
//                    }
//
//                    @Override
//                    public void wxPayOk(boolean payOk) {
//
//                    }
//                });
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//                dissLoadDialog();
//                ToastUtil.showToast(ApiException.getHttpExceptionMessage(throwable));
//            }
//        }, map);
    }

    private void initBroadcast() {
        //过滤器
        mIntentFilter = new IntentFilter(Constants.WX_PAY_BROADCAST);
        //创建广播接收者的对象
        mRecvier = new MyBroadcastReceiver();
        //注册广播接收者的对象
        registerReceiver(mRecvier, mIntentFilter);

    }

    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                int code = intent.getIntExtra(Constants.INTENT_CODE, -1);
                LogUtil.d(TAG, "Code==MyBroadcastReceiver=" + code);
                wxPayResult(code);
            }
        }
    }

    private void wxPayResult(int code) {
        if (code == 0) {
            showTipDialog(true);
        } else {
            showTipDialog(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mRecvier);
    }
}
