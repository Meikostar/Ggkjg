package com.ggkjg.view.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ggkjg.R;
import com.ggkjg.base.BuildConfig;
import com.ggkjg.common.utils.GlideUtils;
import com.ggkjg.dto.CollectionDto;
import com.ggkjg.view.mainfragment.shop.CommodityDetailActivity;

import java.util.List;


public class CollectionAdapter extends BaseQuickAdapter<CollectionDto, BaseViewHolder> {

    public CollectionAdapter(List<CollectionDto> data) {
        super(R.layout.item_collection_layout, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, CollectionDto item) {
        helper.setText(R.id.tv_good_des, item.getGoodsName())
                .setText(R.id.tv_coin_number, item.getGdPrice())
                .addOnClickListener(R.id.right_item_del);
        GlideUtils.getInstances().loadNormalImg(mContext, helper.getView(R.id.img_good_logo), BuildConfig.BASE_IMAGE_URL + item.getGoodsImg());
        RelativeLayout view = helper.getView(R.id.ll_collection);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(mContext,CommodityDetailActivity.class);

                bundle.putLong(CommodityDetailActivity.PRODUCT_ID, Long.valueOf(item.getGoodsId()));
                if (bundle != null) {
                    intent.putExtras(bundle);
                }
                mContext.startActivity(intent);
            }
        });

    }
}
