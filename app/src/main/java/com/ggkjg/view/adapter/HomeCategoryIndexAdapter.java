package com.ggkjg.view.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ggkjg.R;
import com.ggkjg.base.BuildConfig;
import com.ggkjg.common.utils.GlideUtils;
import com.ggkjg.dto.GoodsPushRowsDto;
import com.ggkjg.dto.HomeCategoryIndexDto;

import java.util.List;


/**
 * 主页商品分类
 * Created by dahai on 2019/01/18.
 */
public class HomeCategoryIndexAdapter extends BaseQuickAdapter<HomeCategoryIndexDto, BaseViewHolder> {
    private Context mContext;

    public HomeCategoryIndexAdapter(List<HomeCategoryIndexDto> data, Context mContext) {
        super(R.layout.item_home_category_index_layout, data);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeCategoryIndexDto item) {
        if (item != null) {
            helper.setText(R.id.tv_item_home_title, item.getCiteName());
            String imgUrl = BuildConfig.BASE_IMAGE_URL + item.getIcon();
            GlideUtils.getInstances().loadNormalImg(mContext, helper.getView(R.id.iv_item_home_img), imgUrl, R.mipmap.img_default_4);
        }
    }
}