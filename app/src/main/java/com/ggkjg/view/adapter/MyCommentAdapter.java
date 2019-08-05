package com.ggkjg.view.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ggkjg.R;
import com.ggkjg.base.BuildConfig;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.GlideUtils;
import com.ggkjg.dto.EvaluateDto;
import com.ggkjg.dto.EvaluateUserDto;
import com.ggkjg.dto.MyOrderItemDto;
import com.ggkjg.dto.ShopEvaluateImgListDto;
import com.ggkjg.dto.ShopEvaluateRowsDto;

import java.util.List;


public class MyCommentAdapter extends BaseQuickAdapter<ShopEvaluateRowsDto, BaseViewHolder> {
    private String type = "";

    public MyCommentAdapter(List<ShopEvaluateRowsDto> data, String type) {
        super(R.layout.item_comment_layout, data);
        this.type = type;
    }


    @Override
    protected void convert(BaseViewHolder helper, ShopEvaluateRowsDto item) {
        helper.setText(R.id.tv_create_time, item.getCreateTime())
                .addOnClickListener(R.id.img_commodity_1)
                .addOnClickListener(R.id.img_commodity_2)
                .addOnClickListener(R.id.img_commodity_3);
        String commentStr = item.getContent();
        if (!TextUtils.isEmpty(commentStr)) {
            helper.setText(R.id.tv_comment, commentStr)
                    .setGone(R.id.tv_comment, true);
        } else {
            helper.setGone(R.id.tv_comment, false);
        }
        List<ShopEvaluateImgListDto> imageList = item.getImgList();
        if (imageList != null && imageList.size() > 0) { //显示评论图
            helper.setGone(R.id.ll_comment_img, true);
            if (imageList.size() == 3) {
                String imgUrl_1 = BuildConfig.BASE_IMAGE_URL + imageList.get(0).getImgPath();
                String imgUrl_2 = BuildConfig.BASE_IMAGE_URL + imageList.get(1).getImgPath();
                String imgUrl_3 = BuildConfig.BASE_IMAGE_URL + imageList.get(2).getImgPath();
                helper.setVisible(R.id.img_commodity_1, true)
                        .setVisible(R.id.img_commodity_2, true)
                        .setVisible(R.id.img_commodity_3, true);
                GlideUtils.getInstances().loadNormalImg(mContext, helper.getView(R.id.img_commodity_1), imgUrl_1, R.mipmap.img_default_1);
                GlideUtils.getInstances().loadNormalImg(mContext, helper.getView(R.id.img_commodity_2), imgUrl_2, R.mipmap.img_default_1);
                GlideUtils.getInstances().loadNormalImg(mContext, helper.getView(R.id.img_commodity_3), imgUrl_3, R.mipmap.img_default_1);

            }
            if (imageList.size() == 2) {
                String imgUrl_1 = BuildConfig.BASE_IMAGE_URL + imageList.get(0).getImgPath();
                String imgUrl_2 = BuildConfig.BASE_IMAGE_URL + imageList.get(1).getImgPath();
                helper.setVisible(R.id.img_commodity_1, true)
                        .setVisible(R.id.img_commodity_2, true)
                        .setVisible(R.id.img_commodity_3, false);
                GlideUtils.getInstances().loadNormalImg(mContext, helper.getView(R.id.img_commodity_1), imgUrl_1, R.mipmap.img_default_1);
                GlideUtils.getInstances().loadNormalImg(mContext, helper.getView(R.id.img_commodity_2), imgUrl_2, R.mipmap.img_default_1);

            }
            if (imageList.size() == 1) {
                String imgUrl_1 = BuildConfig.BASE_IMAGE_URL + imageList.get(0).getImgPath();
                helper.setVisible(R.id.img_commodity_1, true)
                        .setVisible(R.id.img_commodity_2, false)
                        .setVisible(R.id.img_commodity_3, false);
                GlideUtils.getInstances().loadNormalImg(mContext, helper.getView(R.id.img_commodity_1), imgUrl_1, R.mipmap.img_default_1);
            }
        } else {
            helper.setGone(R.id.ll_comment_img, false);
        }

        helper.setText(R.id.tv_user_nickname, item.getNickName());
        String userAvatar = BuildConfig.BASE_IMAGE_URL + item.getHeadImg();
        GlideUtils.getInstances().loadNormalImg(mContext, helper.getView(R.id.iv_user_icon), userAvatar, R.mipmap.img_default_1);
         if(item.getGoodsList() != null && item.getGoodsList().size() > 0){
             MyOrderItemDto myOrderItemDto = item.getGoodsList().get(0);
             helper.setText(R.id.tv_commodity_des, myOrderItemDto.getGoodsName())
                        .setText(R.id.tv_commodity_money, myOrderItemDto.getGoodsPrice())
                        .setText(R.id.tv_commodity_number, myOrderItemDto.getGoodsSpecName());
             String imgUrl = BuildConfig.BASE_IMAGE_URL + myOrderItemDto.getGoodsImg();
             GlideUtils.getInstances().loadNormalImg(mContext, helper.getView(R.id.img_commodity_item), imgUrl, R.mipmap.img_default_1);

         }


    }
}
