package com.ggkjg.view.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ggkjg.R;
import com.ggkjg.common.utils.TextUtil;
import com.ggkjg.common.utils.TimeUtil;
import com.ggkjg.common.utils.ToastUtil;
import com.ggkjg.dto.CartAtrrDto;
import com.ggkjg.dto.ShopCartDto;
import com.ggkjg.view.widgets.MCheckBox;

import java.util.List;


/**
 * 购物车
 * Created by dahai on 2019/01/19.
 */
public class PopVoucherAdapter extends BaseQuickAdapter<CartAtrrDto, BaseViewHolder> {

    public PopVoucherAdapter(List<CartAtrrDto> data) {
        super(R.layout.item_pop_voucher_view, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CartAtrrDto item) {
        if(TextUtil.isNotEmpty(item.fullPrice)){
            helper.setText(R.id.tv_detail,"满"+Double.valueOf(item.fullPrice).intValue()+"可用");
        }
        if(TextUtil.isNotEmpty(item.conponName)){
            helper.setText(R.id.tv_title,item.conponName);
        }

        if(TextUtil.isNotEmpty(item.subPrice)){
            helper.setText(R.id.tv_sum,Double.valueOf(item.subPrice).intValue()+"");
        }

        if(TextUtil.isNotEmpty(item.endTime)){
            helper.setText(R.id.tv_time,"有效期:"+ TimeUtil.formatYearTimes(TimeUtil.getStringToDate(item.endTime)));
        }
        MCheckBox mCheckBox=helper.getView(R.id.mcb_choose);
        if(item.isChoose){
            mCheckBox.setChecked(true);
        }else {
            mCheckBox.setChecked(false);

        }
        helper.setOnClickListener(R.id.ll_bg, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Double.valueOf(total).intValue()<Double.valueOf(item.fullPrice).intValue()){
                    ToastUtil.showToast("无法使用，订单金额还未到满减金额!");
                    return;
                }
                List<CartAtrrDto> data = getData();
                for(CartAtrrDto cartAtrrDto:data){
                   if(cartAtrrDto.id.equals(item.id)){
                       if(cartAtrrDto.isChoose){
                           cartAtrrDto.isChoose=false;
                       }else {
                           cartAtrrDto.isChoose=true;
                       }

                   }else {
                       cartAtrrDto.isChoose=false;
                   }

                }

                listener.choose();
                notifyDataSetChanged();

            }
        });
        if(item.conponStatus.equals("0")){//0未使用 1占用中 2已使用 3已过期 4已失效\
            helper.setTextColor(R.id.tv_moneys,mContext.getResources().getColor(R.color.my_color_white))
                    .setTextColor(R.id.tv_title,mContext.getResources().getColor(R.color.my_color_white))
                    .setTextColor(R.id.tv_detail,mContext.getResources().getColor(R.color.my_color_white))
                    .setTextColor(R.id.tv_sum,mContext.getResources().getColor(R.color.my_color_white))
                    .setTextColor(R.id.tv_time,mContext.getResources().getColor(R.color.my_color_aaaaaa))
                    .setBackgroundRes(R.id.iv_item_shop_cart_cover,R.drawable.voucher_blue_ritht)
                    .setBackgroundRes(R.id.rl_bg,R.drawable.voucher_black_left);

        }else {
            helper.setTextColor(R.id.tv_moneys,mContext.getResources().getColor(R.color.my_color_cccccc))
                    .setTextColor(R.id.tv_tle,mContext.getResources().getColor(R.color.my_color_cccccc))
                    .setTextColor(R.id.tv_detail,mContext.getResources().getColor(R.color.my_color_cccccc))
                    .setTextColor(R.id.tv_sum,mContext.getResources().getColor(R.color.my_color_cccccc))
                    .setTextColor(R.id.tv_time,mContext.getResources().getColor(R.color.my_color_cccccc))
                    .setBackgroundRes(R.id.iv_item_shop_cart_cover,R.drawable.voucher_blakc_ringt)
                    .setBackgroundRes(R.id.rl_bg,R.drawable.voucher_black_left);
        }

    }

    public interface  ChooseListener{
        void choose();
    }
    private ChooseListener listener;
    public void setChooseListener(ChooseListener listener){
        this.listener=listener;
    }
    private double total;
    public void setTotal(double total){
        this.total=total;
    }
}