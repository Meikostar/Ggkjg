package com.ggkjg.view.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ggkjg.R;
import com.ggkjg.base.BuildConfig;
import com.ggkjg.common.utils.GlideUtils;
import com.ggkjg.common.utils.TextUtil;
import com.ggkjg.dto.BusinessDto;
import com.ggkjg.dto.BusinessListDto;
import com.ggkjg.dto.CommodityDetailInfoDto;
import com.ggkjg.view.mainfragment.spike.ArticleActivity;

import java.util.List;


/**
 * 产品列表
 * Created by dahai on 2019/01/18.
 */
public class BusinesstListAdapter extends BaseQuickAdapter<BusinessListDto, BaseViewHolder> {

    public BusinesstListAdapter() {
        super(R.layout.item_business_list_layout);
    }


    @Override
    protected void convert(BaseViewHolder helper, BusinessListDto item) {
        if (item != null) {

            if(TextUtil.isNotEmpty(item.cmsTitle)){
                helper.setText(R.id.tv_title, item.cmsTitle);
            }
            if(TextUtil.isNotEmpty(item.createDate)){
                helper.setText(R.id.tv_content, item.createDate);
            }
            RelativeLayout view = helper.getView(R.id.ll_bg);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ArticleActivity.class);
                    intent.putExtra("id",item.id);
                    mContext.startActivity(intent);
                }
            });
            String imgUrl = BuildConfig.BASE_IMAGE_URL + item.cmsMainImg;
            GlideUtils.getInstances().loadNormalImg(mContext, helper.getView(R.id.iv_img), imgUrl, R.mipmap.img_default_6);
        }
    }
}