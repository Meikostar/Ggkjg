package com.ggkjg.view.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ggkjg.R;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.GlideUtils;
import com.lzy.imagepicker.bean.ImageItem;

import java.util.List;


public class ReasonUploadPicAdapter extends BaseQuickAdapter<ImageItem, BaseViewHolder> {
    private int maxImgCount;
    private String mType;

    public ReasonUploadPicAdapter(List<ImageItem> list, int maxImgCount, String type) {
        super(R.layout.item_reason_upload_pic_item, list);
        this.maxImgCount = maxImgCount;
        this.mType = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, ImageItem item) {
        if (!Constants.IMAGEITEM_DEFAULT_ADD.equals(item.path)) {
            helper.setVisible(R.id.ll_del, true)
                    .addOnClickListener(R.id.ll_del);
            GlideUtils.getInstances().loadNormalImg(mContext, helper.getView(R.id.iv_pic), item.path);
        } else {
            helper.setVisible(R.id.ll_del, false);
            helper.setImageResource(R.id.iv_pic, R.mipmap.icon_evaluate_add_pic);

        }

    }

    public void refresh() {
        if (getItemCount() > maxImgCount && Constants.IMAGEITEM_DEFAULT_ADD.equals(getData().get(getItemCount() - 1).path)) {
            //del last
            getData().remove(getItemCount() - 1);
        }
        if (getItemCount() < maxImgCount) {
            if (getItemCount() == 0 || !Constants.IMAGEITEM_DEFAULT_ADD.equals(getData().get(getItemCount() - 1).path)) {
                ImageItem imageItem = new ImageItem();
                imageItem.path = Constants.IMAGEITEM_DEFAULT_ADD;
                getData().add(imageItem);
            }
        }
        notifyDataSetChanged();
    }


}
