package com.ggkjg.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ggkjg.R;
import com.ggkjg.common.utils.GlideUtils;
import com.ggkjg.common.utils.TextUtil;
import com.ggkjg.dto.SpikeDto;
import com.ggkjg.view.mainfragment.shop.CommodityDetailActivity;
import com.ggkjg.view.widgets.SaleProgressView;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;



/**
 * 购物车
 * Created by dahai on 2019/01/19.
 */
public class ShopSpikeAdapter extends BaseQuickAdapter<SpikeDto, BaseViewHolder> {

  private Context mContext;

    public ShopSpikeAdapter(Context context, List<SpikeDto> data) {
        super(R.layout.item_spike_view, data);
        mContext=context;
    }
    private int state=0;
    public void setState(int state){
       this.state=state;
    }
    @Override
    protected void convert(BaseViewHolder helper, SpikeDto item) {
        SaleProgressView spv = helper.getView(R.id.spv);

            spv.setTotalAndCurrentCount(100, item.sold);


        ImageView imageView = helper.getView(R.id.iv_item_shop_cart_cover);
        TextView tvTitle = helper.getView(R.id.tv_title);
        TextView tvDiscount = helper.getView(R.id.tv_discount);
        TextView tvPrice = helper.getView(R.id.tv_price);
        TextView tvFirstPrice = helper.getView(R.id.tv_first_price);
        TextView tvTobuy = helper.getView(R.id.tv_tobuy);
        View line = helper.getView(R.id.line);
        View lines = helper.getView(R.id.lines);
        if(item.isLast){
            lines.setVisibility(View.GONE);
        }else {
            lines.setVisibility(View.VISIBLE);
        }
        GlideUtils.getInstances().loadNormalImg(mContext, imageView, item.goodsImg);
        tvTobuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(mContext, CommodityDetailActivity.class);

                bundle.putLong(CommodityDetailActivity.PRODUCT_ID, Long.valueOf(item.id));
                if (bundle != null) {
                    intent.putExtras(bundle);
                }
                mContext.startActivity(intent);
            }
        });
        if (TextUtil.isNotEmpty(item.goodsName)) {
            tvTitle.setText(item.goodsName);
        }
        if (TextUtil.isNotEmpty(item.activePrice)) {
            tvPrice.setText(item.activePrice);
        }else {
            if (TextUtil.isNotEmpty(item.gdPrice)) {
                tvPrice.setText(item.gdPrice);
            }
        }
        if (TextUtil.isNotEmpty(item.gdPrice)) {
            String name = "$"+item.gdPrice;
            line.setVisibility(View.GONE);
            tvFirstPrice.setText(name);
            tvFirstPrice.getPaint().setAntiAlias(true);//抗锯齿

            tvFirstPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
        }

        if (TextUtil.isNotEmpty(item.activePrice)&&TextUtil.isNotEmpty(item.gdPrice)) {
         double discout=   Double.valueOf(item.activePrice)/Double.valueOf(item.gdPrice);
            DecimalFormat df = new DecimalFormat("0.0");
             String str = df.format(discout*10);
            tvDiscount.setText(str+"折");
        }
        if(item.sold==0){
            tvTobuy.setText("已售罄");
            tvTobuy.setBackgroundColor(mContext.getResources().getColor(R.color.my_color_999999));
        }else {
            if(state==1){
                tvTobuy.setText("去抢购");
                tvTobuy.setBackgroundColor(mContext.getResources().getColor(R.color.my_color_blue));
            }else if(state==2){
                tvTobuy.setText("去抢购");
                tvTobuy.setBackgroundColor(mContext.getResources().getColor(R.color.my_color_999999));
            }else {
                tvTobuy.setText("去看看");
                tvTobuy.setBackgroundColor(mContext.getResources().getColor(R.color.my_color_green));
            }
        }


    }

}