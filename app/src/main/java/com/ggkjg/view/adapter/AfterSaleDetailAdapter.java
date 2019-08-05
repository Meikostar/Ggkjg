package com.ggkjg.view.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ggkjg.R;
import com.ggkjg.common.Constants;

import java.util.List;

public class AfterSaleDetailAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public AfterSaleDetailAdapter(List<String> data) {
        super(R.layout.item_after_sale_detail_layout, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, String item) {
//        helper.setText(R.id.tv_after_sale_des, item.getTitle())
//                .setText(R.id.tv_after_sale_form, item.getExtra().getSku_title());
//        String imgUrl = Constants.WEB_IMG_URL_UPLOADS + item.getCover_image();
//        GlideUtils.getInstances().loadNormalImg(mContext, helper.getView(R.id.img_after_sale_logo), imgUrl, R.mipmap.img_default_1);
    }
}
