package com.ggkjg.view.mainfragment.square;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.common.type.TransferType;
import com.ggkjg.common.utils.LogUtil;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.common.utils.StringUtil;
import com.ggkjg.common.utils.TextUtil;
import com.ggkjg.common.utils.ToastUtil;
import com.ggkjg.dto.AccountBalanceDto;
import com.ggkjg.http.error.ApiException;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.response.HttpResult;
import com.ggkjg.http.subscribers.DefaultSingleObserver;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 广场 - 港豆出让
 * Created by dahai on 2018/4/14.
 */

public class SquareGoldOutActivity extends BaseActivity {
    private static final String TAG = SquareGoldOutActivity.class.getSimpleName();
    @BindView(R.id.btn_gold_out_title)
    EditText btn_gold_out_title;
    @BindView(R.id.btn_gold_out_realName)
    EditText btn_gold_out_realName;
    @BindView(R.id.btn_gold_out_QQ)
    EditText btn_gold_out_QQ;
    @BindView(R.id.btn_gold_mb)
    EditText btn_gold_mb;

    @BindView(R.id.btn_gold_out_transferNum)
    EditText btn_gold_out_transferNum;
    @BindView(R.id.btn_gold_out_transferPrice)
    EditText btn_gold_out_transferPrice;
    @BindView(R.id.btn_gold_out_fundAmount)
    TextView btn_gold_out_fundAmount;
    @BindView(R.id.btn_gold_out_submit)
    Button btn_gold_out_submit;
    @BindView(R.id.tv_gold_out_all)
    TextView tv_gold_out_all;

    @Override
    public int getLayoutId() {
        return R.layout.ui_square_gold_out_layout;
    }

    @Override
    public void initView() {
        actionbar.setImgStatusBar(R.color.my_color_white);
        StatusBarUtils.StatusBarLightMode(this);
    }

    @Override
    public void initData() {
        findAccountBalance();
    }

    @Override
    public void initListener() {
        bindClickEvent(btn_gold_out_submit, () -> {
            String title = btn_gold_out_title.getText().toString().trim();
            String realName = btn_gold_out_realName.getText().toString().trim();
            String QQ = btn_gold_out_QQ.getText().toString().trim();
            String phone = btn_gold_mb.getText().toString().trim();
            String transferNum = btn_gold_out_transferNum.getText().toString().trim();
            String transferPrice = btn_gold_out_transferPrice.getText().toString().trim();
            if(TextUtil.isEmpty(phone)&&TextUtil.isEmpty(QQ)){
                ToastUtil.showToast("请至少填入手机号或微信号");
                return;
            }
            if(TextUtil.isEmpty(title)){
                ToastUtil.showToast("请输入标题");
                return;
            } if(TextUtil.isEmpty(realName)){
                ToastUtil.showToast("请输入发布信息");
                return;
            } if(TextUtil.isEmpty(title)){
                ToastUtil.showToast("请输入标题");
                return;
            } if(TextUtil.isEmpty(transferNum)){
                ToastUtil.showToast("请输入转让数量");
                return;
            }if(TextUtil.isEmpty(transferPrice)){
                ToastUtil.showToast("请输入转让价格");
                return;
            }if(!StringUtil.isMobileNO(phone)){
                ToastUtil.showToast("请输入正确手机号");

                return;
            }
            onSubmit(title, realName, QQ, transferNum, transferPrice,phone);
        });
        bindClickEvent(tv_gold_out_all, () -> {
            btn_gold_out_transferNum.setText(btn_gold_out_fundAmount.getText().toString());
        });

    }

    private void findAccountBalance() {
        showLoadDialog();
        DataManager.getInstance().findAccountBalance(new DefaultSingleObserver<List<AccountBalanceDto>>() {
            @Override
            public void onSuccess(List<AccountBalanceDto> object) {
                if (object != null&&object.size()>0) {
                    btn_gold_out_fundAmount.setText(object.get(0).getAvailAmount());
                }
                dissLoadDialog();
            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();
            }
        }, 1+"");
    }


    private void onSubmit(String title, String realName, String QQ,
                          String transferNum, String transferPrice,String phone) {
        showLoadDialog();
        HashMap<String, String> map = new HashMap<>();
        map.put("title", title);
        map.put("realName", realName);
        map.put("txNO", QQ);
        map.put("phone", phone);
        map.put("transferNum", transferNum);
        map.put("transferPrice", transferPrice);
        map.put("transferType", TransferType.push.getType() + "");
        DataManager.getInstance().publishTransfer(new DefaultSingleObserver<HttpResult<Object>>() {
            @Override
            public void onSuccess(HttpResult<Object> object) {
                LogUtil.i(TAG, "--RxLog-Thread: onSuccess() = ");
                dissLoadDialog();
                ToastUtil.showToast(object.getMsg());
                if (object.getStatus() == 1) {
                    onBackPressed();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                LogUtil.i(TAG, "--RxLog-Thread: onError() = " );
                dissLoadDialog();
            }
        }, map);
    }

}
