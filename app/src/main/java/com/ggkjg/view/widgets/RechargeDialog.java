package com.ggkjg.view.widgets;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.ggkjg.R;
import com.ggkjg.common.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 充值提示框
 */

public class RechargeDialog extends Dialog {
    @BindView(R.id.img_recharge_logo)
    ImageView imgLogo;
    @BindView(R.id.tv_recharge_tip)
    TextView tvTip;

    public String type = "";
    private Context mContext;
    private String tip;
    private RechargeListener listener;

    public RechargeDialog(Context context, String type) {
        super(context, R.style.transparent_style_dialog);
        this.mContext = context;
        this.type = type;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_recharge_layout);
        ButterKnife.bind(this);
        if (Constants.RECHARGE_TYPE_FAIL.equals(type)) {//失败
            imgLogo.setImageResource(R.mipmap.icon_recharge_fail);
            tvTip.setText("充值失败");
            tvTip.setTextColor(mContext.getResources().getColor(R.color.my_color_8b8b8b));
        }else if (Constants.PAY_TYPE_FAIL.equals(type)) {
            imgLogo.setImageResource(R.mipmap.icon_recharge_fail);
            tvTip.setText("支付失败");
            tvTip.setTextColor(mContext.getResources().getColor(R.color.my_color_8b8b8b));
        }else if (Constants.PAY_TYPE_SUCCESS.equals(type)) {
            imgLogo.setImageResource(R.mipmap.icon_recharge_success);
            tvTip.setText("支付成功");
            tvTip.setTextColor(mContext.getResources().getColor(R.color.my_color_8b8b8b));
        }
        if (!TextUtils.isEmpty(tip)) {
            tvTip.setText(tip);
        }
    }

    @OnClick(R.id.img_recharge_close)
    public void onClick() {
        dismiss();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if(null != listener){
            listener.callbackRecharge(Constants.RECHARGE_TYPE_FAIL.equals(type)?false:true);
        }
    }

    public void setRechargeListener(RechargeListener callback){
        this.listener = callback;
    }

    public interface RechargeListener {
        /**
         * 是否支付成功
         * @param isSuccess
         */
        void callbackRecharge(boolean isSuccess);
    }

}
