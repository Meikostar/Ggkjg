package com.ggkjg.view.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ggkjg.R;
import com.ggkjg.base.BuildConfig;
import com.ggkjg.common.utils.GlideUtils;
import com.ggkjg.common.utils.TextUtil;
import com.ggkjg.dto.SquareRowsDto;

import java.util.List;


/**
 * 港豆出让
 * Created by dahai on 2019/01/18.
 */
public class GoldOutAdapter extends BaseQuickAdapter<SquareRowsDto, BaseViewHolder> {
    private Context mContext;

    public GoldOutAdapter(List<SquareRowsDto> data, Context mContext) {
        super(R.layout.item_gold_out_layout, data);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseViewHolder helper, SquareRowsDto item) {
        if (item != null) {
            helper.setText(R.id.iv_item_gold_user_name, item.getNickName());
            helper.setText(R.id.iv_item_gold_time, "发布时间:" + item.getCreateTime());
            helper.setText(R.id.iv_item_gold_user_msg, item.getTitle());
            helper.setText(R.id.iv_item_gold_user_name_, "姓名:" + item.getRealName());
            if(TextUtil.isNotEmpty(item.getTxNo())){
                helper.setText(R.id.iv_item_gold_user_qq, "微信:" + item.getTxNo());
            }else {
                helper.setText(R.id.iv_item_gold_user_qq, "微信:" );
            }
            if(TextUtil.isNotEmpty(item.phone)){
                helper.setText(R.id.iv_item_gold_user_phone, "手机号:" + item.phone);
            }else {
                helper.setText(R.id.iv_item_gold_user_phone, "手机号:" );
            }

            helper.setText(R.id.iv_item_gold_buy_num, item.getTransferNum());
            helper.setText(R.id.iv_item_gold_num, item.getTransferPrice()+"港币/港豆");
            String imgUrl = BuildConfig.BASE_IMAGE_URL + item.getHeadImg();
            GlideUtils.getInstances().loadNormalImg(mContext, helper.getView(R.id.iv_item_gold_user_icon), imgUrl, R.mipmap.user_default_icon);
        }
    }
}