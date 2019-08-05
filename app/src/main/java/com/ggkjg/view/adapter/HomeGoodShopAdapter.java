package com.ggkjg.view.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ggkjg.R;
import com.ggkjg.base.BuildConfig;
import com.ggkjg.common.utils.GlideUtils;
import com.ggkjg.dto.HomeGoodsIndexDto;

import java.util.List;


/**
 * 主页精选好货
 * Created by dahai on 2019/01/18.
 */
public class HomeGoodShopAdapter extends BaseQuickAdapter<HomeGoodsIndexDto, BaseViewHolder> {
    private Context mContext;

    public HomeGoodShopAdapter(List<HomeGoodsIndexDto> data, Context mContext) {
        super(R.layout.item_home_good_shop_layout, data);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeGoodsIndexDto item) {
        if (item != null) {
            helper.setText(R.id.tv_item_home_good_shop_price, item.getGdPrice());
            String imgUrl = BuildConfig.BASE_IMAGE_URL + item.getIcon();
            GlideUtils.getInstances().loadNormalImg(mContext, helper.getView(R.id.tv_item_home_good_shop_img), imgUrl, R.mipmap.img_default_5);
        }
    }
}