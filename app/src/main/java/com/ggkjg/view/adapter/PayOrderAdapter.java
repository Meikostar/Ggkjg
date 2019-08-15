package com.ggkjg.view.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ggkjg.R;
import com.ggkjg.dto.AreaDto;
import com.ggkjg.dto.PayOrderDto;

import java.util.List;


public class PayOrderAdapter extends BaseQuickAdapter<PayOrderDto, BaseViewHolder> {

    private int selPosition = 0;

    public PayOrderAdapter() {
        super(R.layout.item_pay_order_layout);
    }


    @Override
    protected void convert(BaseViewHolder helper, PayOrderDto item) {

        if (helper.getAdapterPosition() == selPosition) {
            helper.getView(R.id.img_status).setSelected(true);
        } else {
            helper.getView(R.id.img_status).setSelected(false);
        }
        switch (item.getPaymentCode()) {
            case "gd":
                helper.setImageResource(R.id.img_pay_logo, R.mipmap.home_gold);
                break;
            case "wx":
                helper.setImageResource(R.id.img_pay_logo, R.mipmap.icon_pay_wx);
                break;
            case "zfb":
                helper.setImageResource(R.id.img_pay_logo, R.mipmap.icon_pay_zfb);
                break;
        }
        String money = item.getMoney();

        if (!TextUtils.isEmpty(money)) {
            helper.setText(R.id.tv_title, item.getPaymentName() + "(" + money + ")");
        } else {
            helper.setText(R.id.tv_title, item.getPaymentName());
        }

    }

    public int getSelPosition() {
        return selPosition;
    }

    public void setSelPosition(int selPosition) {
        this.selPosition = selPosition;
    }

    public void setMoney(String money) {
        if (TextUtils.isEmpty(money)) {
            return;
        }
        List<PayOrderDto> payOrderDtos = getData();
        if (payOrderDtos != null && payOrderDtos.size() > 0) {
            for (int i = 0; i < payOrderDtos.size(); i++) {
                PayOrderDto payOrderDto = payOrderDtos.get(i);
                if ("gd".equals(payOrderDto.getPaymentCode())) {
                    payOrderDto.setMoney(money);
                    payOrderDtos.set(i, payOrderDto);
                    notifyDataSetChanged();
                    return;
                }
            }
        }
    }
}
