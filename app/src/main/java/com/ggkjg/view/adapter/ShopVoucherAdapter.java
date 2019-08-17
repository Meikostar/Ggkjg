package com.ggkjg.view.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ggkjg.R;
import com.ggkjg.base.BuildConfig;
import com.ggkjg.common.utils.TextUtil;
import com.ggkjg.common.utils.TimeUtil;
import com.ggkjg.dto.ShopCartDto;
import com.ggkjg.dto.VoucherDto;
import com.ggkjg.view.mainfragment.spike.VoucherProductListActivity;
import com.ggkjg.view.widgets.SaleProgressView;

import java.util.List;


/**
 * 购物车
 * Created by dahai on 2019/01/19.
 */
public class ShopVoucherAdapter extends BaseQuickAdapter<VoucherDto, BaseViewHolder> {

    public ShopVoucherAdapter(List<VoucherDto> data) {
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

        if(TextUtil.isNotEmpty(item.subPrice)){
            helper.setText(R.id.tv_sum,Double.valueOf(item.subPrice).intValue()+"");
        }

        TextView view = helper.getView(R.id.tv_tobuy);


        if(state==0){
            view.setTextColor(mContext.getResources().getColor(R.color.my_color_white));
            view.setText("使用");
            view.setBackground(mContext.getResources().getDrawable(R.drawable.bg_item_blue_buy_3));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, VoucherProductListActivity.class);
                    mContext.startActivity(intent);
                }
            });

        }else if(state==2){
            view.setText("已使用");
            view.setTextColor(mContext.getResources().getColor(R.color.my_color_white));;
            view.setBackground(mContext.getResources().getDrawable(R.drawable.bg_item_black_b3));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }else {
            view.setText("已失效");
            view.setTextColor(mContext.getResources().getColor(R.color.my_color_white));
            view.setBackground(mContext.getResources().getDrawable(R.drawable.bg_item_black_b3));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
        if(TextUtil.isNotEmpty(item.endTime)){
            helper.setText(R.id.tv_time,"有效期:"+ TimeUtil.formatYearTimes(TimeUtil.getStringToDate(item.endTime)));
        }

    }
    public void setState(long state){
        this.state=state;
    }

    private long state;
}