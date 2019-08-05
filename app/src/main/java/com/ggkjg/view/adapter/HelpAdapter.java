package com.ggkjg.view.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ggkjg.R;
import com.ggkjg.dto.HelpDto;
import com.ggkjg.dto.HelpItemDto;

import java.util.List;

public class HelpAdapter extends BaseQuickAdapter<HelpItemDto, BaseViewHolder> {
    public HelpAdapter(List<HelpItemDto> data) {
        super(R.layout.item_help_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HelpItemDto item) {
        if (item.isShow()) {
            helper.setGone(R.id.tv_des, true)
                    .setImageResource(R.id.img_arrow, R.mipmap.icon_arrow_up);
        } else {
            helper.setGone(R.id.tv_des, false)
                    .setImageResource(R.id.img_arrow, R.mipmap.icon_arrow_down);
        }
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_des, item.getContent());
    }
}
