package com.ggkjg.view.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ggkjg.R;
import com.ggkjg.base.BuildConfig;
import com.ggkjg.common.utils.GlideUtils;
import com.ggkjg.dto.ShopCartDto;
import com.ggkjg.view.widgets.SaleProgressView;

import java.util.List;


/**
 * 购物车
 * Created by dahai on 2019/01/19.
 */
public class ShopSpikeAdapter extends BaseQuickAdapter<ShopCartDto, BaseViewHolder> {

    public ShopSpikeAdapter(List<ShopCartDto> data) {
        super(R.layout.item_spike_view, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopCartDto item) {
        SaleProgressView spv = helper.getView(R.id.spv);
        spv.setTotalAndCurrentCount(100,69);

    }

}