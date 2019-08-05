package com.ggkjg.view.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ggkjg.R;
import com.ggkjg.base.BuildConfig;
import com.ggkjg.common.utils.GlideUtils;
import com.ggkjg.dto.CommodityDetailInfoDto;
import java.util.List;


/**
 * 产品列表
 * Created by dahai on 2019/01/18.
 */
public class ProductListAdapter extends BaseQuickAdapter<CommodityDetailInfoDto, BaseViewHolder> {

    public ProductListAdapter() {
        super(R.layout.item_product_list_layout);
    }

    public ProductListAdapter(@Nullable List<CommodityDetailInfoDto> data) {
        super(R.layout.item_product_list_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommodityDetailInfoDto item) {
        if (item != null) {
            helper.setText(R.id.tv_item_product_title, item.getGoodsName());
            helper.setText(R.id.tv_item_product_price, item.getGdPrice());
            helper.setText(R.id.tv_item_product_comments, (item.getAppraiseTotal() == null ? 0 : item.getAppraiseTotal()) + "条评论");
            if(item.getAppraiseRate() != null){
                helper.setText(R.id.tv_item_product_comments_scale, item.getAppraiseRate() + "%好评");
            }else{
                helper.setText(R.id.tv_item_product_comments_scale,  "0%好评");
            }
            String imgUrl = BuildConfig.BASE_IMAGE_URL + item.getGoodsImg();
            GlideUtils.getInstances().loadNormalImg(mContext, helper.getView(R.id.iv_item_product_img), imgUrl, R.mipmap.img_default_6);
        }
    }
}