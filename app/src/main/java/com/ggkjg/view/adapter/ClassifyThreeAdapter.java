package com.ggkjg.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ggkjg.R;
import com.ggkjg.base.BuildConfig;
import com.ggkjg.common.utils.GlideUtils;
import com.ggkjg.dto.StoreCategoryDto;
import com.ggkjg.view.mainfragment.shop.CommodityDetailActivity;
import com.ggkjg.view.mainfragment.shop.ShopProductListActivity;

import java.util.List;

/**
 * Created by lihaoqi on 2019/1/25.
 */

public class ClassifyThreeAdapter extends BaseQuickAdapter<StoreCategoryDto, BaseViewHolder> {
    private Context mContext;

    public ClassifyThreeAdapter(Context context, @Nullable List<StoreCategoryDto> data) {
        super(R.layout.item_classify_three, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, StoreCategoryDto item) {
        helper.setText(R.id.tv_name, item.getCategoryName());
        ImageView imageView = helper.getView(R.id.iv_img);
        GlideUtils.getInstances().loadNormalImg(mContext, imageView, BuildConfig.BASE_IMAGE_URL + item.getImgUrl(), R.mipmap.img_default_4);
        helper.getView(R.id.ll_item_classify_three).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
//                bundle.putLong(CommodityDetailActivity.PRODUCT_ID, item.getId());
//                gotoActivity(CommodityDetailActivity.class, bundle);
                bundle.putInt(ShopProductListActivity.PRODUCT_TYPE, (int)item.getId());
//                bundle.putString(ShopProductListActivity.ACTION_SEARCH_KEY, item.getCategoryName());
                gotoActivity(ShopProductListActivity.class, bundle);
            }
        });
    }

    public void gotoActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(mContext, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        mContext.startActivity(intent);
    }

}
