package com.ggkjg.view.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ggkjg.R;
import com.ggkjg.base.BuildConfig;
import com.ggkjg.common.utils.GlideUtils;
import com.ggkjg.dto.CollectionDto;

import java.util.List;


public class CollectionAdapter extends BaseQuickAdapter<CollectionDto, BaseViewHolder> {

    public CollectionAdapter(List<CollectionDto> data) {
        super(R.layout.item_collection_layout, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, CollectionDto item) {
        helper.setText(R.id.tv_good_des, item.getGoodsName())
                .setText(R.id.tv_coin_number, item.getGdPrice())
                .addOnClickListener(R.id.right_item_del);
        GlideUtils.getInstances().loadNormalImg(mContext, helper.getView(R.id.img_good_logo), BuildConfig.BASE_IMAGE_URL + item.getGoodsImg());

    }
}
