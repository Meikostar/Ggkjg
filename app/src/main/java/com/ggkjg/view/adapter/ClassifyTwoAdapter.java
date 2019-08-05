package com.ggkjg.view.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ggkjg.R;
import com.ggkjg.dto.StoreCategoryDto;
import com.ggkjg.view.widgets.autoview.FullyGridLayoutManager;

import java.util.List;

/**
 * Created by lihaoqi on 2019/1/25.
 */

public class ClassifyTwoAdapter extends BaseQuickAdapter<StoreCategoryDto,BaseViewHolder> {
    private Context mContext;

    public ClassifyTwoAdapter(Context context) {
        super(R.layout.item_classify_two);
        this.mContext = context;
    }


    public ClassifyTwoAdapter(Context context,@Nullable List<StoreCategoryDto> data) {
        super(R.layout.item_classify_two, data);
        this.mContext = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, StoreCategoryDto item) {
        helper.setText(R.id.tv_name,item.getCategoryName());
        RecyclerView rvlist = helper.getView(R.id.rv_three);
        FullyGridLayoutManager gridLayoutManager = new FullyGridLayoutManager(mContext,3);
        rvlist.setLayoutManager(gridLayoutManager);
        ClassifyThreeAdapter classifyThreeAdapter  = new ClassifyThreeAdapter(mContext,item.getCateList());
        classifyThreeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //TODO 实现 跳转
            }
        });
        rvlist.setAdapter(classifyThreeAdapter);
    }


}
