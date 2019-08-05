package com.ggkjg.view.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.ggkjg.dto.FloorBean;

import java.util.List;

/**
 * Created by lihaoqi on 2019/1/30.
 */

public class OrderListAdapter extends MultipleItemRvAdapter<FloorBean,BaseViewHolder> {


    public OrderListAdapter(@Nullable List<FloorBean> data) {
        super(data);
        finishInitialize();
    }

    @Override
    protected int getViewType(FloorBean orderMultiItemEntity) {
        return orderMultiItemEntity.getType();
    }

    @Override
    public void registerItemProvider() {
        mProviderDelegate.registerProvider(new OrderListProvider());
//        mProviderDelegate.registerProvider(new OrderPushListProvider());
    }
}
