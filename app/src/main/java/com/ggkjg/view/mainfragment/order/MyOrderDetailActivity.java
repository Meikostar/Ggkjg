package com.ggkjg.view.mainfragment.order;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.RxTimerUtil;
import com.ggkjg.common.utils.ToastUtil;
import com.ggkjg.dto.MyOrderDto;
import com.ggkjg.dto.MyOrderItemDto;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.response.HttpResult;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.adapter.MyOrderDetailAdapter;
import com.ggkjg.view.widgets.RechargeDialog;

import java.util.List;

import butterknife.BindView;


public class MyOrderDetailActivity extends BaseActivity {

    @BindView(R.id.tv_myorder_consignee)
    TextView tv_myorder_consignee;
    @BindView(R.id.tv_myorder_phone)
    TextView tv_myorder_phone;
    @BindView(R.id.tv_myorder_address)
    TextView tv_myorder_address;
    @BindView(R.id.recy_my_order)
    RecyclerView recy_my_order;
    @BindView(R.id.ll_detail_money)
    LinearLayout ll_detail_money;
    @BindView(R.id.tv_detail_price)
    TextView tv_detail_price;
    @BindView(R.id.tv_detail_weight)
    TextView tv_detail_weight;
    @BindView(R.id.tv_detail_express_money)
    TextView tv_detail_express_money;
    @BindView(R.id.tv_detail_pay_money)
    TextView tv_detail_pay_money;
    @BindView(R.id.ll_detail_bottom)
    LinearLayout ll_detail_bottom;
    @BindView(R.id.btn_detail_operation_one)
    TextView btn_detail_operation_one;
    @BindView(R.id.btn_detail_operation_two)
    TextView btn_detail_operation_two;
    @BindView(R.id.tv_detail_number)
    TextView tv_detail_number;
    @BindView(R.id.tv_detail_creat_time)
    TextView tv_detail_creat_time;
    @BindView(R.id.tv_detail_delivery_time)
    TextView tv_detail_delivery_time;
    @BindView(R.id.tv_detail_receive_time)
    TextView tv_detail_receive_time;
    @BindView(R.id.rl_top_status)
    RelativeLayout rl_top_status;

    private String orderId = "";
    private MyOrderDto mMyOrderDto;
    private MyOrderDetailAdapter mAdapter;
    //private PayDialog payDialog;
    private MyBroadcastReceiver mRecvier = null;
    private IntentFilter mIntentFilter;
    RechargeDialog tipDialog;
    private int mCount = -1;

    @Override
    public void initListener() {
        bindClickEvent(btn_detail_operation_one, () -> {
            dealOptionBtn(btn_detail_operation_one.getTag().toString());
        });
        bindClickEvent(btn_detail_operation_two, () -> {
            dealOptionBtn(btn_detail_operation_two.getTag().toString());
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.ui_my_order_detail_layout;
    }

    @Override
    public void initView() {
        initAdapter();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            orderId = bundle.getString(Constants.ORDER_ID);
        }
        initBroadcast();
    }

    @Override
    public void initData() {
        loadData(true);
    }

    private void loadData(boolean isLoad) {
        getOrderDetail(isLoad);
    }

    private void getOrderDetail(boolean isLoad) {
        if (isLoad) {
            showLoadDialog();
        }
        DataManager.getInstance().findOrderDetail(new DefaultSingleObserver<List<MyOrderDto>>() {
            @Override
            public void onSuccess(List<MyOrderDto> myOrderDtos) {
                dissLoadDialog();
                if (myOrderDtos != null && myOrderDtos.size() > 0) {
                    mMyOrderDto = myOrderDtos.get(0);
                }

                initDateView();
            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();
                ToastUtil.showToast(throwable.getMessage());
            }
        }, orderId);
    }

    private void initDateView() {
        if (mMyOrderDto != null) {
            //订单状态1-待支付 2-待发货 3-待收货 4-待评价 5-已完成 0-已取消 查询全部不传
            int topStatus = -1;
            switch (mMyOrderDto.getOrderStatus()) {
                case "0":
                    topStatus = R.mipmap.bg_order_cancelled;
                    break;
                case "1":
                    topStatus = R.mipmap.bg_order_pendingpayment;
                    break;
                case "2":
                    topStatus = R.mipmap.bg_order_tobedelivered;
                    break;
                case "3":
                    topStatus = R.mipmap.bg_order_pendingreceipt;
                    break;
                case "4":
                    topStatus = R.mipmap.bg_order_comment;
                    break;
                case "5":
                    topStatus = R.mipmap.bg_order_completed;
                    break;

            }
            if (topStatus != -1) {
                rl_top_status.setBackgroundResource(topStatus);
            }
            tv_myorder_consignee.setText("收货人:" + mMyOrderDto.getContactName());
            tv_myorder_phone.setText(mMyOrderDto.getMobileNo());
            tv_myorder_address.setText("收货地址:" + mMyOrderDto.getAddress());
            tv_detail_price.setText("" + mMyOrderDto.getGoodsTotalMoney());
            if(mMyOrderDto.getWeight() != null) {
                tv_detail_weight.setText(mMyOrderDto.getWeight() + "kg");
            }else{
                tv_detail_weight.setText("0.0kg");
            }
            tv_detail_express_money.setText("" + mMyOrderDto.getDeliverMoney());
            tv_detail_pay_money.setText("" + mMyOrderDto.getRealOrderMoney());
            tv_detail_number.setText("订单编号:" + mMyOrderDto.getOrderNo());
            tv_detail_creat_time.setText("创建时间:" + mMyOrderDto.getCreateTime());
            if (!TextUtils.isEmpty(mMyOrderDto.getDeliveryTime())) {
                tv_detail_delivery_time.setText("发货时间:" + mMyOrderDto.getDeliveryTime());
            } else {
                tv_detail_delivery_time.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(mMyOrderDto.getReceiveTime())) {
                tv_detail_receive_time.setText("收货时间:" + mMyOrderDto.getReceiveTime());
            } else {
                tv_detail_receive_time.setVisibility(View.GONE);
            }
            tv_detail_creat_time.setText("创建时间:" + mMyOrderDto.getCreateTime());
            if (mMyOrderDto.getGoodsList() != null) {
                mAdapter.setStatus(mMyOrderDto.getOrderStatus());
                mAdapter.setNewData(mMyOrderDto.getGoodsList());
            }
            dealBottonLayout();
        }
    }

    private void dealBottonLayout() {
        if (mMyOrderDto == null) {
            return;
        }
        //订单状态1-待支付 2-待发货 3-待收货 4-待评价 5-已完成 0-已取消
        switch (mMyOrderDto.getOrderStatus()) {
            case "1":
                ll_detail_bottom.setVisibility(View.VISIBLE);
                btn_detail_operation_one.setText("取消");
                btn_detail_operation_two.setText("付款");
                btn_detail_operation_one.setTag(Constants.CANCEL_ORDER);
                btn_detail_operation_two.setTag(Constants.PAY_MONEY);
                break;
            case "3":
                ll_detail_bottom.setVisibility(View.VISIBLE);
                btn_detail_operation_one.setText("查看物流");
                btn_detail_operation_two.setText("确定收货");
                btn_detail_operation_one.setTag(Constants.LOOK_LOGISTICS);
                btn_detail_operation_two.setTag(Constants.CONFIRM_RECEIVE);
                break;
            default:
                ll_detail_bottom.setVisibility(View.GONE);
                break;

        }

    }

    private void initAdapter() {
        recy_my_order.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new MyOrderDetailAdapter(this, null);
        recy_my_order.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.btn_comment) {
                    MyOrderItemDto itemDto = mAdapter.getItem(position);
                    if (itemDto != null) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Constants.INTENT_DATA, itemDto);
//                        bundle.putString(Constants.ORDER_NO,mMyOrderDto.getOrderNo());
                        gotoActivity(MyOrderEvaluateActivity.class, false, bundle,Constants.INTENT_REQUESTCODE_EVALUATE);
                    }
                }
            }
        });
    }

    private void dealOptionBtn(String type) {
        if (mMyOrderDto == null) {
            return;
        }
        switch (type) {
            case Constants.CANCEL_ORDER:
                cancelOrder();
                break;
            case Constants.PAY_MONEY:
                Bundle bundle = new Bundle();
                bundle.putString(Constants.ORDER_ID, mMyOrderDto.getId());
                bundle.putString(Constants.ORDER_NO, mMyOrderDto.getOrderNo());
                bundle.putString(Constants.ORDER_MONEY, mMyOrderDto.getRealOrderMoney());
                gotoActivity(PayOrderActivity.class, false, bundle, Constants.INTENT_REQUESTCODE_PAY);
                break;

            case Constants.CONFIRM_RECEIVE:
                confirmOrder();
                break;
            case Constants.LOOK_LOGISTICS:
                Bundle lookBundle = new Bundle();
                lookBundle.putString(Constants.EXPRESS_ID, mMyOrderDto.getExpressId());
                lookBundle.putString(Constants.EXPRESS_NO, mMyOrderDto.getExpressNo());
                gotoActivity(MyOrderLogisticsActivity.class, false, lookBundle);
                break;
        }
    }

    private void confirmOrder() {
        showLoadDialog();
        DataManager.getInstance().confirmOrder(new DefaultSingleObserver<HttpResult<Object>>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                if (httpResult != null && httpResult.getStatus() == 1) {
                    getOrderDetail(false);
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
        }, orderId);
    }

    private void cancelOrder() {
        showLoadDialog();
        DataManager.getInstance().cancelOrder(new DefaultSingleObserver<HttpResult<Object>>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                if (httpResult != null && httpResult.getStatus() == 1) {
                    getOrderDetail(false);
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
        }, orderId);
    }

//    private void payWXOrderInfo(String order_no) {
//        HashMap<String, String> maps = new HashMap<>();
//        maps.put("order_no",  order_no);
//        showLoadDialog();
//        DataManager.getInstance().payOrderInfo(new DefaultSingleObserver<RechargeOrderNoDto>() {
//            @Override
//            public void onSuccess(RechargeOrderNoDto orderNoDto) {
//                dissLoadDialog();
//                mCount = -1;
//                RxTimerUtil.cancel();
//                loadData(true);
//                PayUtils.getInstances().WXPay(MyOrderDetailActivity.this, orderNoDto);
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//                dissLoadDialog();
//                ToastUtil.showToast(ApiException.getHttpExceptionMessage(throwable));
//            }
//        }, maps);
//    }

//    private void payZFBOrderInfo(String order_no) {
//
//        HashMap<String, String> map = new HashMap<>();
//        map.put("order_no[0]", order_no);
//        showLoadDialog();
//        DataManager.getInstance().payZFBOrderInfo(new DefaultSingleObserver<String>() {
//            @Override
//            public void onSuccess(String signStr) {
//                dissLoadDialog();
//                PayUtils.getInstances().zfbPaySync(MyOrderDetailActivity.this, signStr, new PayResultListener() {
//                    @Override
//                    public void zfbPayOk(boolean payOk) {
//                        mCount = -1;
//                        RxTimerUtil.cancel();
//                        loadData(true);
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
//    }

    /**
     * 支付
     *
     * @param order_no 订单号
     * @param platform 支付平台 ： wechat（微信） ， alipay（支付宝） ， wallet（钱包）
     * @param scene    支付场景 ： app （微信或支付宝 app支付）， miniapp（微信小程序） ，balance（钱包余额）
     */
//    private void paySdk(String order_no, String platform, String scene, String password) {
//        showLoadDialog();
//        HashMap<String, String> maps = new HashMap<>();
//        maps.put("order_no[" + 0 + "]", order_no);
//        DataManager.getInstance().paySdk(new DefaultSingleObserver<Object>() {
//            @Override
//            public void onSuccess(Object object) {
//                dissLoadDialog();
//                mCount = -1;
//                RxTimerUtil.cancel();
//                loadData(true);
//                showTipDialog(true);
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//
//                dissLoadDialog();
//            }
//        }, maps, platform, scene, password);
//    }
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
                wxPayResult(code);
            }
        }
    }

    private void wxPayResult(int code) {
        if (code == 0) {
            mCount = -1;
            RxTimerUtil.cancel();
            loadData(true);
            showTipDialog(true);
        } else {
            showTipDialog(false);
        }
    }

    private void showTipDialog(boolean isSuccess) {
        if (isSuccess) {
            tipDialog = new RechargeDialog(MyOrderDetailActivity.this, Constants.RECHARGE_TYPE_SUCCESS);
            tipDialog.setTip("支付成功");
        } else {
            tipDialog = new RechargeDialog(MyOrderDetailActivity.this, Constants.RECHARGE_TYPE_FAIL);
            tipDialog.setTip("支付失败");
        }
        tipDialog.show();
    }

//    private void orderCancelTime() {
//        RxTimerUtil.interval(1000, number -> {
//            mCount--;
//            tv_order_tip.setText("订单取消还是剩下" + TimeUtil.secToTime(mCount));
//            if (mCount < 0) {// 计时完毕就停掉计时器，并且重新设置
//                if (ll_detail_bottom != null) {
//                    ll_detail_bottom.setVisibility(View.GONE);
//                }
//                if (tv_order_status != null) {
//                    tv_order_status.setText("订单过期，请重新下单");
//                }
//                if (tv_order_tip != null) {
//                    tv_order_tip.setText("");
//                }
//                RxTimerUtil.cancel();
//            }
//        });
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRecvier != null) {
            unregisterReceiver(mRecvier);
        }
        RxTimerUtil.cancel();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == Constants.INTENT_REQUESTCODE_RETURN_SUCCESS) {
            finish();
        } else if (resultCode == Activity.RESULT_OK && requestCode == Constants.INTENT_REQUESTCODE_PAY) {
            finish();
        } else if (resultCode == Activity.RESULT_OK && requestCode == Constants.INTENT_REQUESTCODE_EVALUATE) {
            getOrderDetail(true);
        }
    }
}
