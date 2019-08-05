package com.ggkjg.view.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ggkjg.R;
import com.ggkjg.dto.AddressDto;

import java.util.List;


public class GoodAddressAdapter extends BaseQuickAdapter<AddressDto, BaseViewHolder> {
    public GoodAddressAdapter(List<AddressDto> data) {
        super(R.layout.item_good_address_layout, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, AddressDto item) {
        helper.setText(R.id.tv_good_address_username, "姓名：" + item.getContactName());
        helper.setText(R.id.tv_good_address_phone, item.getMobileNo());
        helper.setText(R.id.tv_good_address_des, item.getProvince() + item.getCity() + item.getArea() + item.getAddrDetail());
        if ("1".equals(item.getIsDef())) {
            helper.setVisible(R.id.tv_good_address_default, true);
        } else {
            helper.setVisible(R.id.tv_good_address_default, false);
        }
        helper.addOnClickListener(R.id.tv_good_address_del)
                .addOnClickListener(R.id.tv_good_address_edit);
    }
}
