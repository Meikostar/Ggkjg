package com.ggkjg.view.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ggkjg.R;
import com.ggkjg.base.BuildConfig;
import com.ggkjg.common.utils.GlideUtils;
import com.ggkjg.common.utils.TextUtil;
import com.ggkjg.dto.BusinessDto;
import com.ggkjg.dto.BusinessListDto;
import com.ggkjg.dto.CommodityDetailInfoDto;

import java.util.List;


/**
 * 产品列表
 * Created by dahai on 2019/01/18.
 */
public class BusinesstListAdapter extends BaseQuickAdapter<BusinessListDto, BaseViewHolder> {

    public BusinesstListAdapter() {
        super(R.layout.item_business_list_layout);
    }

    public BusinesstListAdapter(@Nullable List<BusinessListDto> data) {
        super(R.layout.item_product_list_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BusinessListDto item) {
        if (item != null) {

            if(TextUtil.isNotEmpty(item.cmsTitle)){
                helper.setText(R.id.tv_title, item.cmsTitle);
            }
            if(TextUtil.isNotEmpty(item.createTime)){
                helper.setText(R.id.tv_content, item.createTime);
            }

            String imgUrl = BuildConfig.BASE_IMAGE_URL + item.cmsMainImg;
            GlideUtils.getInstances().loadNormalImg(mContext, helper.getView(R.id.iv_item_product_img), imgUrl, R.mipmap.img_default_6);
        }
    }
}