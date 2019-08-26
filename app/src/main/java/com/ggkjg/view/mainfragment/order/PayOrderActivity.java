package com.ggkjg.view.mainfragment.order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.LogUtil;
import com.ggkjg.common.utils.MD5Utils;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.common.utils.TextUtil;
import com.ggkjg.common.utils.ToastUtil;
import com.ggkjg.common.utils.pay.PayResultListener;
import com.ggkjg.common.utils.pay.PayUtils;
import com.ggkjg.db.bean.WEIXINREQ;
import com.ggkjg.dto.AccountBalanceDto;
import com.ggkjg.dto.AddressDto;
import com.ggkjg.dto.PayOrderDto;
import com.ggkjg.dto.RechargeDto;
import com.ggkjg.dto.UserInfoDto;
import com.ggkjg.http.error.ApiException;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.response.HttpResult;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.adapter.PayOrderAdapter;
import com.ggkjg.view.mainfragment.personalcenter.wallet.WalletRechargeActivity;
import com.ggkjg.view.mainfragment.settings.SetPayPwdActivity;
import com.ggkjg.view.widgets.InputPwdDialog;
import com.ggkjg.view.widgets.RechargeDialog;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class PayOrderActivity extends BaseActivity {
    @BindView(R.id.recy_pay)
    RecyclerView recyclerView;
    @BindView(R.id.btn_confirm)
    TextView btnConfim;
    PayOrderAdapter mAdapter;
    String orderId, orderNo, realOrderMoney, GdBalance,state;
    private RechargeDialog tipDialog;
    @Override
    public void initListener() {
        bindClickEvent(btnConfim, () -> {
            dealPay();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.ui_pay_order_layout;
    }

    @Override
    public void initView() {
        actionbar.setImgStatusBar(R.color.my_color_white);
        StatusBarUtils.StatusBarLightMode(this);
        initAdapter();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            orderId = bundle.getString(Constants.ORDER_ID);
            state = bundle.getString("state");
            orderNo = bundle.getString(Constants.ORDER_NO);
            realOrderMoney = bundle.getString(Constants.ORDER_MONEY);
            if(TextUtil.isNotEmpty(state)){
                HintType=Constants.PAY_TYPE_FAIL;
            }
        }

    }

    @Override
    public void initData() {
        getWalletBalance();
        findPaymentList();
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new PayOrderAdapter();
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mAdapter.setSelPosition(position);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void findPaymentList() {
        DataManager.getInstance().findPaymentList(new DefaultSingleObserver<List<PayOrderDto>>() {
            @Override
            public void onSuccess(List<PayOrderDto> payOrderDtoList) {
                mAdapter.setNewData(payOrderDtoList);
                mAdapter.setMoney(GdBalance);
            }
        });
    }

    private void dealPay() {
        if (mAdapter != null && mAdapter.getData() != null && mAdapter.getData().size() > mAdapter.getSelPosition()) {
            PayOrderDto item = mAdapter.getItem(mAdapter.getSelPosition());
            switch (item.getPaymentCode()) {
                case "gd":
                    getMemberBaseInfo(new InputPwdDialog.InputPasswordListener() {
                        @Override
                        public void callbackPassword(String password) {
                            submitOrder(item.getId() + "", password,item.getPaymentCode());
                        }
                    });
                    break;
                case "wx":
                    submitWxOrder(item.getId() + "", "",item.getPaymentCode());
                    break;
                case "zfb":
                    submitOrder(item.getId() + "", "",item.getPaymentCode());
                    break;
            }
        }
    }
    /**
     * 余额支付
     */
    private void submitWxOrder(String payMentId, String tradePwd, String payType) {

        HashMap<String, String> map = new HashMap<>();
        map.put("orderId", orderId);
        map.put("orderNo", orderNo);
        //        map.put("realOrderMoney", realOrderMoney);  //订单支付 去掉参数 订单金额  realOrderMoney
        map.put("payMentId", payMentId);
        if(TextUtil.isNotEmpty(tradePwd)){
            map.put("tradePwd", MD5Utils.getMD5Str(tradePwd + Constants.MD5_ADD_STR));
        }else {
            //            map.put("tradePwd", "");
        }

        DataManager.getInstance().submitWxOrder(new DefaultSingleObserver<HttpResult<WEIXINREQ>>() {
            @Override
            public void onSuccess(HttpResult<WEIXINREQ> httpResult) {
                dissLoadDialog();
                if (httpResult != null && httpResult.getStatus() == 1) {

                    PayUtils.getInstances().WXPay(PayOrderActivity.this, httpResult.getData());



                } else {
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
                    InputPwdDialog inputPasswordDialog = new InputPwdDialog(PayOrderActivity.this, listener);
                    inputPasswordDialog.show();
                } else {
                    startActivity(new Intent(PayOrderActivity.this, SetPayPwdActivity.class));
                }
            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();

            }
        });
    }

    /**
     * 余额支付
     */
    private void submitOrder(String payMentId, String tradePwd, String payType) {
        HashMap<String, String> map = new HashMap<>();
        map.put("orderId", orderId);
        map.put("orderNo", orderNo);
        //map.put("realOrderMoney", realOrderMoney);  //订单支付 去掉参数 订单金额  realOrderMoney
        map.put("payMentId", payMentId);
        map.put("tradePwd", MD5Utils.getMD5Str(tradePwd + Constants.MD5_ADD_STR));
        DataManager.getInstance().submitOrder(new DefaultSingleObserver<HttpResult<RechargeDto>>() {
            @Override
            public void onSuccess(HttpResult<RechargeDto> httpResult) {
                dissLoadDialog();
                if (httpResult != null && httpResult.getStatus() == 1) {
                    if ("gd".equals(payType)) {
                        setResult(Activity.RESULT_OK);
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.ORDER_MONEY, realOrderMoney);
                        bundle.putString(Constants.ORDER_ID, orderId);
                        gotoActivity(PaySuccessActivity.class, true, bundle);
                    } else if ("wx".equals(payType)) {

                    }else if ("zfb".equals(payType)) {
                        if(httpResult != null && !TextUtils.isEmpty(httpResult.getData().getOrderString())){
                            PayUtils.getInstances().zfbPaySync(PayOrderActivity.this, httpResult.getData().getOrderString(), new PayResultListener() {
                                @Override
                                public void zfbPayOk(boolean payOk) {
                                    showTipDialog(payOk);
                                }

                                @Override
                                public void wxPayOk(boolean payOk) {

                                }
                            });
                        }

                    }


                } else {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == Constants.INTENT_REQUESTCODE_SEL_ADDRESS && data != null) {
            if (requestCode == 12) {
                ToastUtil.showToast("支付成功");
                finish();
                //           if (requestCode == RQ_WEIXIN_PAY) {
                //                RxBus.getInstance().send(SubscriptionBean.createSendBean(SubscriptionBean.CHAEGE_SUCCESS,""));
                //            }
            }

        }
    }

    private void getWalletBalance() {
        DataManager.getInstance().findAccountBalance(new DefaultSingleObserver<List<AccountBalanceDto>>() {
            @Override
            public void onSuccess(List<AccountBalanceDto> object) {
                if (object != null&&object.size()>0) {
                    GdBalance = object.get(0).getAvailAmount();
                    if (mAdapter != null) {
                        mAdapter.setMoney(GdBalance);
                    }
                }
            }

            @Override
            public void onError(Throwable throwable) {
            }
        }, 1+"");
    }
   private String HintType=Constants.RECHARGE_TYPE_FAIL;
    private void showTipDialog(boolean isSuccess) {
        if (isSuccess) {
            setResult(Activity.RESULT_OK);
            Bundle bundle = new Bundle();
            bundle.putString(Constants.ORDER_MONEY, realOrderMoney);
            bundle.putString(Constants.ORDER_ID, orderId);
            gotoActivity(PaySuccessActivity.class, true, bundle);
        } else {
            tipDialog = new RechargeDialog(PayOrderActivity.this,HintType );
        }
        tipDialog.show();
    }
}
