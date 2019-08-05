package com.ggkjg.view.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ggkjg.R;

import java.util.List;

public class PopRecommendFilterAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public PopRecommendFilterAdapter(@Nullable List<String> data) {
        super(R.layout.item_pop_recommend_filter_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_title,item);
    }
}
