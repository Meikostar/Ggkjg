package com.ggkjg.view.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ggkjg.R;
import com.ggkjg.dto.MemberLevelDto;


import java.util.List;

public class PopWalletFilterAdapter extends BaseQuickAdapter<MemberLevelDto, BaseViewHolder> {
    public PopWalletFilterAdapter(@Nullable List<MemberLevelDto> data) {
        super(R.layout.item_pop_wallet_filter_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MemberLevelDto item) {
        if(null != item){
            helper.setText(R.id.tv_title,item.getLevelName());
        }
    }
}
