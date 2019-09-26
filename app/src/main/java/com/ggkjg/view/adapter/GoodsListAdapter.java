package com.ggkjg.view.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ggkjg.R;
import com.ggkjg.base.BuildConfig;
import com.ggkjg.common.utils.TextUtil;
import com.ggkjg.dto.GoodsInfoDto;

import java.util.List;

/**
 * Created by lihaoqi on 2019/1/30.
 */

public class GoodsListAdapter extends BaseQuickAdapter<GoodsInfoDto,BaseViewHolder> {

    public GoodsListAdapter(){
        super(R.layout.item_goods_order_layout);
    }

    public GoodsListAdapter(@Nullable List<GoodsInfoDto> data) {
        super(R.layout.item_goods_order_layout,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsInfoDto item) {
        helper.setText(R.id.tv_goods_order_title,item.getGoodsName());
        helper.setText(R.id.tv_goods_order_type,item.getGoodsSpecName());
        helper.setText(R.id.tv_goods_order_price,TextUtil.isNotEmpty(item.activePrice)?item.activePrice:item.getGoodsPrice()+"");
        helper.setText(R.id.tv_goods_order_qty,"x"+item.getGoodsNum());
        ImageView ivImg = helper.getView(R.id.iv_goods_order_img);
        Glide.with(mContext).load(BuildConfig.BASE_IMAGE_URL+item.getGoodsImg()).into(ivImg);
    }

}
