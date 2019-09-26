package com.ggkjg.view.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ggkjg.R;
import com.ggkjg.dto.BonusDetailItemDto;
import com.ggkjg.dto.MoneyRecordItemDto;


/**
 * Reward 记录适配器
 */
public class RewardAdapter extends BaseQuickAdapter<BonusDetailItemDto, BaseViewHolder> {


    public RewardAdapter() {
        super(R.layout.item_record_layout);
    }


    @Override
    protected void convert(BaseViewHolder helper, BonusDetailItemDto item) {
        String titleName = null;
        switch (item.getBonusType()) {
            case 1:
                titleName = "一级分销奖";
                break;
            case 2:
                titleName = "二级分销奖";
                break;
            case 3:
                titleName = "直推奖";
                break;
            case 4:
                titleName = "管理奖";
                break;
            default:
                break;
        }
        helper.setText(R.id.tv_des, titleName)
                .setText(R.id.tv_date, item.getCreateTime())
                .setText(R.id.tv_money, "+" + item.getBonusAmount());
//                .setTextColor(R.id.tv_money, mContext.getResources().getColor(R.color.my_color_14CA2E));


    }
}