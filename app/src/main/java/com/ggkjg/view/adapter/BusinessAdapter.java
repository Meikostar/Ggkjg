package com.ggkjg.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ggkjg.R;
import com.ggkjg.base.BuildConfig;
import com.ggkjg.common.utils.GlideUtils;
import com.ggkjg.common.utils.TextUtil;
import com.ggkjg.dto.BusinessDto;
import com.ggkjg.dto.GoodsPushRowsDto;
import com.ggkjg.view.mainfragment.shop.CommodityDetailActivity;

import java.util.List;


/**
 * 主页新品推荐
 * Created by dahai on 2019/01/18.
 */
public class BusinessAdapter extends BaseQuickAdapter<BusinessDto, BaseViewHolder> {
    private Context mContext;

    public BusinessAdapter(List<BusinessDto> data, Context mContext) {
        super(R.layout.item_business_layout, data);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseViewHolder helper, BusinessDto item) {
        if (item != null) {
            helper.setText(R.id.tv_content, item.nameType);
            String imgUrl = BuildConfig.BASE_IMAGE_URL + item.cmsMainImg;
            GlideUtils.getInstances().loadRoundCornerImg(mContext, helper.getView(R.id.iv_img), 3,imgUrl);
            RelativeLayout bg = helper.getView(R.id.iv_item_home_push);
            bg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    if(TextUtil.isNotEmpty(item.url)){
//                        Intent intent = new Intent(mContext, clz);
//                        intent.putExtra("url",item.url);
//                        mContext.startActivity(intent);
//                    }else {
//                        Intent intent = new Intent(mContext, clz);
//                        intent.putExtra("url","https://www.huya.com/g/lol");
//                        mContext.startActivity(intent);
//                    }

                }
            });
        }
    }

    public void gotoActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(mContext, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        mContext.startActivity(intent);
    }

}