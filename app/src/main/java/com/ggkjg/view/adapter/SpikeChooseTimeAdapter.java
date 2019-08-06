package com.ggkjg.view.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ggkjg.R;
import com.ggkjg.dto.ChooseAddressDto;
import com.ggkjg.dto.TimeDataDto;
import com.ggkjg.view.widgets.autoview.AutoLocateHorizontalView;

import java.util.List;

/**
 * 自提地址列表适配器
 * Created by rzb on 2019/3/16.
 */
public class SpikeChooseTimeAdapter extends BaseQuickAdapter<TimeDataDto, BaseViewHolder> implements AutoLocateHorizontalView.IAutoLocateHorizontalView {
    public SpikeChooseTimeAdapter(@Nullable List<TimeDataDto> data) {
        super(R.layout.item_spike_text_view, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TimeDataDto item) {
        if(null != item){
            helper.setText(R.id.tv_time,item.getTime());
            helper.setText(R.id.tv_state,item.getState()==1?"抢购进行中":(item.getState()==2?"已开抢":"即将开始"));
        }
    }

    @Override
    public View getItemView() {
        return null;
    }

    @Override
    public void onViewSelected(boolean isSelected, int pos, RecyclerView.ViewHolder holder, int itemWidth) {

    }

}
