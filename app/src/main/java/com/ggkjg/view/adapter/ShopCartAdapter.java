package com.ggkjg.view.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ggkjg.R;
import com.ggkjg.base.BuildConfig;
import com.ggkjg.common.utils.GlideUtils;
import com.ggkjg.dto.ShopCartDto;
import com.ggkjg.view.mainfragment.shop.CommodityDetailActivity;

import java.util.List;


/**
 * 购物车
 * Created by dahai on 2019/01/19.
 */
public class ShopCartAdapter extends BaseQuickAdapter<ShopCartDto, BaseViewHolder> {

    public ShopCartAdapter(List<ShopCartDto> data) {
        super(R.layout.item_shop_cart_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopCartDto item) {
        helper.setText(R.id.tv_item_shop_cart_title, item.getGoodsName())
                .setText(R.id.tv_item_shop_cart_type, item.getSpecName())
                .setText(R.id.tv_item_shop_cart_price, item.isNowSedKill.equals("1")?(item.activePrice+""):item.getGdPrice() + "")
                .setText(R.id.count, item.getCartNum() + "")
                .addOnClickListener(R.id.tv_item_shop_cart_delete)
                .addOnClickListener(R.id.increase)
                .addOnClickListener(R.id.decrease)
                .addOnClickListener(R.id.cb_item_shop_cart);
        if(item.conponCount!=0){
            helper.setVisible(R.id.ll_gq,true);
            helper.setText(R.id.tv_voucher_cout,"x "+item.conponCount);
        }else {
            helper.setVisible(R.id.ll_gq,false);
        }
        helper.setChecked(R.id.cb_item_shop_cart, item.isSelect());
        GlideUtils.getInstances().loadNormalImg(mContext, helper.getView(R.id.iv_item_shop_cart_cover), BuildConfig.BASE_IMAGE_URL + item.getGoodsImg(), R.mipmap.img_default_1);
        LinearLayout linearLayout = helper.getView(R.id.ll_item_shop_cart_content_view);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(mContext, CommodityDetailActivity.class);

                bundle.putLong(CommodityDetailActivity.PRODUCT_ID, Long.valueOf(item.goodsId));
                if (bundle != null) {
                    intent.putExtras(bundle);
                }
                mContext.startActivity(intent);
            }
        });

    }

}