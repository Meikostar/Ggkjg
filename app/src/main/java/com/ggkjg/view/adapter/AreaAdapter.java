package com.ggkjg.view.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ggkjg.R;
import com.ggkjg.dto.AreaDto;


public class AreaAdapter extends BaseQuickAdapter<AreaDto, BaseViewHolder> {
    private String name;

    public AreaAdapter(Context mContext, String name) {
        super(R.layout.item_arean_layout, null);
        this.name = name;
    }


    @Override
    protected void convert(BaseViewHolder helper, AreaDto item) {

        helper.setText(R.id.tv_area_name, item.getAreaName());

    }
}
