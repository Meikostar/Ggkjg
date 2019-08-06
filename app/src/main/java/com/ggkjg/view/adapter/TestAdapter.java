package com.ggkjg.view.adapter;


import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ggkjg.R;
import com.ggkjg.common.utils.ScreenSizeUtil;
import com.ggkjg.dto.TimeDataDto;
import com.ggkjg.view.adapter.baseadapter.BaseRecycleViewAdapter;
import com.ggkjg.view.widgets.autoview.AutoLocateHorizontalView;

import butterknife.BindView;
import butterknife.ButterKnife;

public  class TestAdapter extends BaseRecycleViewAdapter implements AutoLocateHorizontalView.IAutoLocateHorizontalView{
    private View view;
    private Context context;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spike_text_view, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder holders = (ItemViewHolder) holder;
        TimeDataDto data= (TimeDataDto) datas.get(position);
        holders.tvTime.setText(data.getTime());
        holders.tvState.setText(data.getState()==1?"抢购进行中":(data.getState()==2?"已开抢":"即将开始"));
        holders.acbRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.itemClick(position,data);
            }
        });
    }

   public interface  ItemClickListener{
        void itemClick(int poition,TimeDataDto data);
   }
   public ItemClickListener listener;
   public void setItemClick(ItemClickListener listeners){
        listener=listeners;
   }
    @Override
    public int getItemCount() {
        return datas==null?0:datas.size();
    }

    @Override
    public View getItemView() {
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewSelected(boolean isSelected, int pos, RecyclerView.ViewHolder holder, int itemWidth) {
        ItemViewHolder holders = (ItemViewHolder) holder;

        if(isSelected){
            holders.tvTime.setTextColor(context.getColor(R.color.my_color_white));
            holders.tvTime.setTextSize(21);
            holders.tvState.setTextSize(13);
            holders.tvState.setTextColor(context.getColor(R.color.my_color_white));
        }else {
            holders.tvTime.setTextSize(19);
            holders.tvState.setTextSize(11);
            holders.tvTime.setTextColor(context.getColor(R.color.my_color_9f));
            holders.tvState.setTextColor(context.getColor(R.color.my_color_9f));
        }
    }
    public class ItemViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_state)
        TextView tvState;
        @BindView(R.id.acb_root_view)
        LinearLayout acbRootView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}