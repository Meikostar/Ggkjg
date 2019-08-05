package com.ggkjg.view.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ggkjg.dto.GoodMultiItemEntity;

import java.util.List;



public class AfterSaleAdapter extends BaseMultiItemQuickAdapter<GoodMultiItemEntity, BaseViewHolder> {


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public AfterSaleAdapter(List<GoodMultiItemEntity> data) {
        super(data);
//        addItemType(GoodMultiItemEntity.GOOD_ORDER_INFO, R.layout.item_good_order_info_layout);
//        addItemType(GoodMultiItemEntity.GOOD_ORDER_ITEM, R.layout.item_good_order_item_layout);
//        addItemType(GoodMultiItemEntity.GOOD_ORDER_PAY, R.layout.item_after_sale_pay_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodMultiItemEntity item) {

    }

}