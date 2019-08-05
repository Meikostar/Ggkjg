package com.ggkjg.view.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ggkjg.R;
import com.ggkjg.dto.LogisticsItemDto;

import java.util.List;

public class MyOrderLogisticsAdapter extends BaseQuickAdapter<LogisticsItemDto, BaseViewHolder> {

    public MyOrderLogisticsAdapter() {
        super(R.layout.item_myorder_logistics_layout);
    }


    @Override
    protected void convert(BaseViewHolder helper, LogisticsItemDto item) {
        if (helper.getAdapterPosition() == 0) {
            helper.setBackgroundRes(R.id.tv_logistics_flag, R.drawable.bg_logistics_flag_sel_shape)
                    .setVisible(R.id.tv_logistics_line_end, false)
                    .setVisible(R.id.tv_logistics_line_start, true)
                    .setTextColor(R.id.tv_logistics_des, mContext.getResources().getColor(R.color.my_color_14CA2E));
        } else {
            helper.setBackgroundRes(R.id.tv_logistics_flag, R.drawable.bg_logistics_flag_default_shape)
                    .setVisible(R.id.tv_logistics_line_end, false)
                    .setVisible(R.id.tv_logistics_line_start, false)
                    .setTextColor(R.id.tv_logistics_des, mContext.getResources().getColor(R.color.my_color_8b8b8b));

        }
        helper.setText(R.id.tv_logistics_des, item.getContext())
                .setText(R.id.tv_logistics_time, item.getTime());
    }
}
