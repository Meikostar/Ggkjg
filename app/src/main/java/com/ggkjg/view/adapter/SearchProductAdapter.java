package com.ggkjg.view.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ggkjg.R;
import com.ggkjg.db.bean.SearchHistory;
import java.util.List;


/**
 * 搜索商品
 * Created by dahai on 2019/01/18.
 */
public class SearchProductAdapter extends BaseQuickAdapter<SearchHistory, BaseViewHolder> {
    private Context mContext;

    public SearchProductAdapter(List<SearchHistory> data, Context mContext) {
        super(R.layout.item_search_product_layout, data);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchHistory item) {
        if (item != null) {
            helper.setText(R.id.tv_search_item_title, item.getName());
        }
        helper.addOnClickListener(R.id.tv_search_item_clean);
        helper.addOnClickListener(R.id.tv_search_item_title);
    }
}