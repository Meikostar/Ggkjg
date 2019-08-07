package com.ggkjg.view.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ggkjg.R;
import com.ggkjg.dto.ShopCartDto;

import java.util.List;


/**
 * 购物车
 * Created by dahai on 2019/01/19.
 */
public class PopVoucherAdapter extends BaseQuickAdapter<ShopCartDto, BaseViewHolder> {

    public PopVoucherAdapter(List<ShopCartDto> data) {
        super(R.layout.item_pop_voucher_view, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopCartDto item) {


    }

}