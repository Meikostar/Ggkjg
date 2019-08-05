package com.ggkjg.view.adapter;

import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ggkjg.R;
import com.ggkjg.dto.ChooseAddressDto;
import java.util.List;

/**
 * 自提地址列表适配器
 * Created by rzb on 2019/3/16.
 */
public class AddressFilterAdapter extends BaseQuickAdapter<ChooseAddressDto, BaseViewHolder> {
    public AddressFilterAdapter(@Nullable List<ChooseAddressDto> data) {
        super(R.layout.item_pop_address_filter_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChooseAddressDto item) {
        if(null != item){
            helper.setText(R.id.tv_address,item.getTempName());
        }
    }
}
