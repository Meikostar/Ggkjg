package com.ggkjg.view.mainfragment.personalcenter;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.LogUtil;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.common.utils.ToastUtil;
import com.ggkjg.dto.AccountBalanceDto;
import com.ggkjg.http.error.ApiException;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.response.HttpResult;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.widgets.TransferDialog;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/*HKDT 转换*/
public class HKDTTransferActivity extends BaseActivity {
    @BindView(R.id.tv_remarks)
    TextView tvRemarks;
    @BindView(R.id.tv_balance_label)
    TextView tv_balance_label;
    @BindView(R.id.tv_balance)
    TextView tv_balance;
    @BindView(R.id.btn_confirm)
    Button btn_confirm;
    @BindView(R.id.btn_all_fill)
    TextView btn_all_fill;
    @BindView(R.id.ed_money)
    EditText ed_money;

    String mBalance;
    int fundType = 1;//港豆
    boolean isCoinTransferHKDT;//true 港豆置换，false HKDT置换

    @Override
    public void initListener() {
        bindClickEvent(btn_confirm, () -> {
            deanTransfer();
        });
        bindClickEvent(btn_all_fill, () -> {
            if (!TextUtils.isEmpty(mBalance)) {
                ed_money.setText(mBalance);
            }

        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.ui_hkdt_transfer_layout;
    }

    @Override
    public void initView() {
        actionbar.setImgStatusBar(R.color.my_color_white);
        StatusBarUtils.StatusBarLightMode(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isCoinTransferHKDT = bundle.getBoolean(Constants.INTENT_FLAG, false);
        }
        if (isCoinTransferHKDT) {
            tvRemarks.setText("备注：置换比例：1港豆=1HKDT，每单收取0.06%手续费");
            actionbar.setTitle("港豆置换HKDT");
            tv_balance_label.setText("港豆余额");
        } else {
            tvRemarks.setText("备注：置换比例：1HKDT=1港豆，每单收取0.06%手续费");
            actionbar.setTitle("HKDT置换港豆");
            tv_balance_label.setText("HKDT余额");
            fundType = 3;
        }
    }

    @Override
    public void initData() {
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
                    mBalance = object.get(0).getAvailAmount();
                    tv_balance.setText(mBalance);
                }
                dissLoadDialog();
            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();
            }
        }, fundType+"");
    }


    private void memberSwap(String swapCharge, String moneyStr) {

        String swapType = "";
        if (isCoinTransferHKDT) {
            swapType = "1";
        } else {
            swapType = "2";
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("swapNum", moneyStr);
        map.put("swapCharge", swapCharge);
        map.put("swapType", swapType);

        showLoadDialog();
        DataManager.getInstance().memberSwap(new DefaultSingleObserver<HttpResult<Object>>() {
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

    private void findSwapCharge(String moneyStr) {
        showLoadDialog();
        DataManager.getInstance().findSwapCharge(new DefaultSingleObserver<HttpResult<String>>() {
            @Override
            public void onSuccess(HttpResult<String> httpResult) {
                dissLoadDialog();
                if (httpResult != null && httpResult.getStatus() == 1) {
                    new TransferDialog(HKDTTransferActivity.this, isCoinTransferHKDT, moneyStr, httpResult.getData(), new TransferDialog.TransferListener() {
                        @Override
                        public void sure() {
                            memberSwap(httpResult.getData(),moneyStr);
                        }
                    }).show();
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
        }, moneyStr);
    }

    private void deanTransfer() {
        String moneyStr = ed_money.getText().toString().trim();
        if (TextUtils.isEmpty(moneyStr)) {
            ToastUtil.showToast("请输入置换数量");
            return;
        }
        long moneyLong = Long.parseLong(moneyStr);
        if (moneyLong < 500) {
            ToastUtil.showToast("置换数量要大于或等于500");
            return;
        }
        findSwapCharge(moneyStr);

    }
}
