package com.ggkjg.view.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ggkjg.R;
import com.ggkjg.base.BuildConfig;
import com.ggkjg.common.type.MemberLevelType;
import com.ggkjg.common.utils.GlideUtils;
import com.ggkjg.dto.RecommendDto;

import java.util.List;


/**
 * 推荐列表
 */
public class RecommendAdapter extends BaseQuickAdapter<RecommendDto, BaseViewHolder> {

    public RecommendAdapter(List<RecommendDto> data) {
        super(R.layout.item_recommend_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecommendDto item) {
        helper.setText(R.id.tv_user_nickname, item.getNickName())
                .setGone(R.id.img_level, true);
        if(!TextUtils.isEmpty(item.getProvince())){
            helper.setText(R.id.tv_level_area, "区域：" + item.getProvince() + item.getCity() + item.getArea());
        }else {
            helper.setText(R.id.tv_level_area, "区域：未设置" );
        }
        String memberName = "";
        if (item.getMemberLevel() == MemberLevelType.level_1.getType()) {
            memberName = MemberLevelType.level_1.getValue();
            helper.setGone(R.id.img_level, false)
                    .setTextColor(R.id.tv_level_name, mContext.getResources().getColor(R.color.my_color_666666));
        } else if (item.getMemberLevel() == MemberLevelType.level_2.getType()) {
            memberName = MemberLevelType.level_2.getValue();
            helper.setImageResource(R.id.img_level, R.mipmap.icon_member_level_2)
                    .setTextColor(R.id.tv_level_name, mContext.getResources().getColor(R.color.my_color_ED444B));
            ;
        } else if (item.getMemberLevel() == MemberLevelType.level_3.getType()) {
            memberName = MemberLevelType.level_3.getValue();
            helper.setImageResource(R.id.img_level, R.mipmap.icon_member_level_3)
                    .setTextColor(R.id.tv_level_name, mContext.getResources().getColor(R.color.my_color_FE9112));
            ;
        } else if (item.getMemberLevel() == MemberLevelType.level_4.getType()) {
            memberName = MemberLevelType.level_4.getValue();
            helper.setImageResource(R.id.img_level, R.mipmap.icon_member_level_4)
                    .setTextColor(R.id.tv_level_name, mContext.getResources().getColor(R.color.my_color_52B5E7));
            ;
        }
        helper.setText(R.id.tv_level_name, memberName)
                .setText(R.id.tv_push_number, "总业绩: " + item.getPushNum());
        GlideUtils.getInstances().loadNormalImg(mContext, helper.getView(R.id.img_user_header), BuildConfig.BASE_IMAGE_URL + item.getHeadImg());
    }
}