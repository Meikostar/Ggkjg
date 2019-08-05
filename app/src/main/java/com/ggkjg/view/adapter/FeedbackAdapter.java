package com.ggkjg.view.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ggkjg.R;
import com.ggkjg.dto.FeedBackTypeDto;

import java.util.List;

public class FeedbackAdapter extends BaseQuickAdapter<FeedBackTypeDto,BaseViewHolder> {
    private int selPosition = 0;

    public void setSelPosition(int selPosition) {
        this.selPosition = selPosition;
    }

    public int getSelPosition() {
        return selPosition;
    }

    public FeedbackAdapter() {
        super(R.layout.item_feedback_type_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, FeedBackTypeDto item) {
        helper.setText(R.id.tv_title,item.getName());
        if(helper.getAdapterPosition() == selPosition){
            helper.getView(R.id.img_status).setSelected(true);
        }else {
            helper.getView(R.id.img_status).setSelected(false);
        }
    }
}
