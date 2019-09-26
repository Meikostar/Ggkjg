package com.ggkjg.view.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ggkjg.R;
import com.ggkjg.common.utils.TextUtil;
import com.ggkjg.common.utils.TimeUtil;
import com.ggkjg.dto.VoucherDto;
import com.ggkjg.view.mainfragment.spike.DistributeActivity;

import java.util.List;


/**
 * 购物车
 * Created by dahai on 2019/01/19.
 */
public class ShopManVoucherAdapter extends BaseQuickAdapter<VoucherDto, BaseViewHolder> {

    public ShopManVoucherAdapter(List<VoucherDto> data) {
        super(R.layout.item_voucher_view, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VoucherDto item) {
        if(TextUtil.isNotEmpty(item.fullPrice)){
            helper.setText(R.id.tv_detail,"满"+Double.valueOf(item.fullPrice).intValue()+"可用");
        }
        if(TextUtil.isNotEmpty(item.conponName)){
            helper.setText(R.id.tv_tle,item.conponName);
        }
        TextView view = helper.getView(R.id.tv_tobuy);
        view.setText("派发");
        if(item.isPayout.equals("1")){
            view.setBackground(mContext.getResources().getDrawable(R.drawable.bg_item_blue_buy_3));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, DistributeActivity.class);
                    intent.putExtra("id",item.conponBaseId);
                    mContext.startActivity(intent);
                }
            });
        }else {
            view.setBackground(mContext.getResources().getDrawable(R.drawable.bg_item_black_b3));

        }


        if(TextUtil.isNotEmpty(item.subPrice)){
            helper.setText(R.id.tv_sum,Double.valueOf(item.subPrice).intValue()+"");
        }
        if(TextUtil.isNotEmpty(item.endTime)){
            helper.setText(R.id.tv_time,"有效期:"+ TimeUtil.formatYearTimes(TimeUtil.getStringToDate(item.endTime)));
        }

    }

}