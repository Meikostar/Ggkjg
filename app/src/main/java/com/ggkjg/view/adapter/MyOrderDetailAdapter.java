package com.ggkjg.view.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ggkjg.BuildConfig;
import com.ggkjg.R;
import com.ggkjg.common.utils.GlideUtils;
import com.ggkjg.dto.MyOrderItemDto;

import java.util.List;


public class MyOrderDetailAdapter extends BaseQuickAdapter<MyOrderItemDto, BaseViewHolder> {
    private Context mContext;
    private String status;

    public MyOrderDetailAdapter(Context mContext, List<MyOrderItemDto> data) {
        super(R.layout.item_order_detail_layout, data);
        this.mContext = mContext;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    protected void convert(BaseViewHolder helper, MyOrderItemDto item) {
        helper.setText(R.id.tv_me_order_des, item.getGoodsName())
                .setText(R.id.tv_me_order_branch, "" + item.getGoodsPrice())
                .setText(R.id.tv_me_order_number, "X" + item.getGoodsNum())
                .setText(R.id.tv_me_order_form, item.getGoodsSpecName())
                .addOnClickListener(R.id.btn_comment);
        String imgUrl = com.ggkjg.base.BuildConfig.BASE_IMAGE_URL + item.getGoodsImg();
        GlideUtils.getInstances().loadNormalImg(mContext, helper.getView(R.id.img_me_order_logo), imgUrl, R.mipmap.img_default_1);
        if ("4".equals(status) && "2".equals(item.getIsComment())) {//待评价，未评价
            helper.setGone(R.id.rl_comment, true);
        } else {
            helper.setGone(R.id.rl_comment, false);
        }
    }
}
