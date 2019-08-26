package com.ggkjg.view.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ggkjg.R;
import com.ggkjg.dto.MoneyRecordItemDto;
import com.ggkjg.dto.MyccRecordDto;


/**
 * 钱包 记录适配器
 */
public class WalletAdapter extends BaseQuickAdapter<MoneyRecordItemDto, BaseViewHolder> {


    public WalletAdapter() {
        super(R.layout.item_record_layout);
    }


    @Override
    protected void convert(BaseViewHolder helper, MoneyRecordItemDto item) {
        helper.setText(R.id.tv_des, item.getTransferRemark())
                .setText(R.id.tv_date, item.getTransferTime());
        if ("1".equals(item.getTransferState())) {
            helper.setText(R.id.tv_money, "+" + item.getTransferAmount())
                    .setTextColor(R.id.tv_money, mContext.getResources().getColor(R.color.my_color_14CA2E));
        } else {
            helper.setText(R.id.tv_money, "-" +item.getTransferAmount())
                    .setTextColor(R.id.tv_money, mContext.getResources().getColor(R.color.my_color_fc262b));
            ;
        }
        if ("1".equals(item.fundType)) {
            helper.setText(R.id.tv_type, "通用账户" );

        } else  if ("4".equals(item.fundType)){
            helper.setText(R.id.tv_type, "专用账户");


        }

    }
}