package com.ggkjg.view.adapter;

import android.view.View;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ggkjg.R;
import com.ggkjg.base.BuildConfig;
import com.ggkjg.common.utils.GlideUtils;
import com.ggkjg.dto.ShopEvaluateRowsDto;
import java.util.List;

/**
 * Created by dahai on 2019/01/21.
 */

public class ShopEvaluateAdapter extends BaseQuickAdapter<ShopEvaluateRowsDto, BaseViewHolder> {
    public ShopEvaluateAdapter(List<ShopEvaluateRowsDto> data) {
        super(R.layout.item_shop_evaluate_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopEvaluateRowsDto item) {
        if (null != item) {
            GlideUtils.getInstances().loadRoundImg(mContext, helper.getView(R.id.iv_user_icon), BuildConfig.BASE_URL + item.getHeadImg(),R.mipmap.user_default_icon);
            helper.setText(R.id.iv_user_name, item.getNickName());
            helper.setText(R.id.iv_user_comment, item.getContent());
            helper.setText(R.id.iv_user_specification, item.getCreateTime());
            ImageView img1 = helper.getView(R.id.iv_user_img1);
            ImageView img2 = helper.getView(R.id.iv_user_img2);
            ImageView img3 = helper.getView(R.id.iv_user_img3);
            if (null != item.getImgList() && !item.getImgList().isEmpty()) {
                switch (item.getImgList().size() - 1) {
                    case 0:
                        img1.setVisibility(View.VISIBLE);
                        img2.setVisibility(View.INVISIBLE);
                        img3.setVisibility(View.INVISIBLE);
                        GlideUtils.getInstances().loadNormalImg(mContext, img1, BuildConfig.BASE_IMAGE_URL + item.getImgList().get(0).getImgPath(), R.mipmap.img_default_1);
                        break;
                    case 1:
                        img1.setVisibility(View.VISIBLE);
                        img2.setVisibility(View.VISIBLE);
                        img3.setVisibility(View.INVISIBLE);
                        GlideUtils.getInstances().loadNormalImg(mContext, img1, BuildConfig.BASE_IMAGE_URL + item.getImgList().get(0).getImgPath(), R.mipmap.img_default_1);
                        GlideUtils.getInstances().loadNormalImg(mContext, img2, BuildConfig.BASE_IMAGE_URL + item.getImgList().get(1).getImgPath(), R.mipmap.img_default_1);
                        break;
                    case 2:
                        img1.setVisibility(View.VISIBLE);
                        img2.setVisibility(View.VISIBLE);
                        img3.setVisibility(View.VISIBLE);
                        GlideUtils.getInstances().loadNormalImg(mContext, img1, BuildConfig.BASE_IMAGE_URL + item.getImgList().get(0).getImgPath(), R.mipmap.img_default_1);
                        GlideUtils.getInstances().loadNormalImg(mContext, img2, BuildConfig.BASE_IMAGE_URL + item.getImgList().get(1).getImgPath(), R.mipmap.img_default_1);
                        GlideUtils.getInstances().loadNormalImg(mContext, img3, BuildConfig.BASE_IMAGE_URL + item.getImgList().get(2).getImgPath(), R.mipmap.img_default_1);
                        break;
                }
            } else {
                img1.setVisibility(View.GONE);
                img2.setVisibility(View.GONE);
                img3.setVisibility(View.GONE);
            }
        }
        helper.addOnClickListener(R.id.iv_user_img1);
        helper.addOnClickListener(R.id.iv_user_img2);
        helper.addOnClickListener(R.id.iv_user_img3);
    }
}