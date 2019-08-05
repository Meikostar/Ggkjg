package com.ggkjg.view.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ggkjg.R;
import com.ggkjg.base.BuildConfig;
import com.ggkjg.common.utils.GlideUtils;
import com.ggkjg.dto.MessageDto;

import java.util.List;


/**
 * 商城快讯列表
 * Created by dahai on 2019/01/19.
 */
public class ShopMessageListAdapter extends BaseQuickAdapter<MessageDto, BaseViewHolder> {
    private Context mContext;

    public ShopMessageListAdapter(List<MessageDto> data, Context mContext) {
        super(R.layout.item_shop_message_layout, data);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageDto item) {
        if (item != null) {
            helper.setText(R.id.tv_item_shop_message_title, item.getCmsTitle());
            helper.setText(R.id.tv_item_shop_message_time, item.getCreateDate());
            helper.setText(R.id.tv_item_shop_message_msg, item.getSimpleDesc());
            String imgUrl = BuildConfig.BASE_IMAGE_URL + item.getCmsMainImg();
            GlideUtils.getInstances().loadNormalImg(mContext, helper.getView(R.id.iv_item_home_img), imgUrl, R.mipmap.img_default_1);
        }
    }


}