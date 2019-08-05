package com.ggkjg.view.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ggkjg.R;
import com.ggkjg.dto.SystemMessageDto;

import java.util.List;

public class MessageCenterAdapter extends BaseQuickAdapter<SystemMessageDto, BaseViewHolder> {
    public MessageCenterAdapter() {
        super(R.layout.item_message_center_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, SystemMessageDto item) {
        helper.setText(R.id.tv_date, item.getCreateDate())
                .setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_des, item.getContentz());

    }
}
