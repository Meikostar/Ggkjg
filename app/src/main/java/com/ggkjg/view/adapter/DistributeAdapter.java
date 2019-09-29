package com.ggkjg.view.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ggkjg.R;
import com.ggkjg.base.BuildConfig;
import com.ggkjg.common.type.MemberLevelType;
import com.ggkjg.common.utils.GlideUtils;
import com.ggkjg.common.utils.TextUtil;
import com.ggkjg.dto.RecommendDto;
import com.ggkjg.dto.ShopCartDto;
import com.ggkjg.view.widgets.MCheckBox;

import java.util.List;
import java.util.Map;


/**
 * 购物车
 * Created by dahai on 2019/01/19.
 */
public class DistributeAdapter extends BaseQuickAdapter<RecommendDto, BaseViewHolder> {

    public DistributeAdapter(List<RecommendDto> data) {
        super(R.layout.item_distribute_view, data);
    }
    public void setMapInfo(Map<String,String> map){
        maps=map;
    }
    private Map<String,String> maps;
    @Override
    protected void convert(BaseViewHolder helper, RecommendDto item) {
        if(TextUtil.isNotEmpty(item.getNickName())){
            helper.setText(R.id.tv_phone, item.getNickName());


        }
        MCheckBox view = helper.getView(R.id.mcb_choose);
        LinearLayout llbg = helper.getView(R.id.ll_balance);
        llbg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(item.isChoose){
                    item.isChoose=false;
                }else {
                    item.isChoose=true;
                }
                listener.choose();
                notifyDataSetChanged();
            }
        });
        if(item.isChoose){
            view.setChecked(true);
        }else {
            view.setChecked(false);
        }
        if(TextUtil.isNotEmpty(item.getPushNum())){
            helper.setText(R.id.tv_sum, item.getPushNum());
        }

        if(!TextUtils.isEmpty(item.getProvince())){
            helper.setText(R.id.tv_detail, "区域：" + item.getProvince() + item.getCity() + item.getArea());
        }else {
            helper.setText(R.id.tv_detail, "区域：未设置" );
        }
//        1-普通会员 2-店主 3-创客 4-城市运营中心
        String memberName = "";//	会员等级 0-普通会员 1-VIP1 2-VIP2 3-VIP3 4-VIP4 5-VIP5 6-VIP6
        helper.setText(R.id.tv_content, maps.get(item.getMemberLevel())==null?"普通会员": maps.get(item.getMemberLevel()));
//        if (item.getMemberLevel().equals("0") ) {
//            helper.setText(R.id.tv_content, "普通会员");
//        } else if (item.getMemberLevel().equals("1") ) {
//            helper.setText(R.id.tv_content, "店主");
//        } else if (item.getMemberLevel().equals("2")  ) {
//            helper.setText(R.id.tv_content, "创客");
//        } else if (item.getMemberLevel().equals("3") ) {
//            helper.setText(R.id.tv_content, "城市运营中心");
//        }else if (item.getMemberLevel().equals("4")  ) {
//            helper.setText(R.id.tv_content, "VIP4");
//        }else if (item.getMemberLevel().equals("5") ) {
//            helper.setText(R.id.tv_content, "VIP5");
//        }else if (item.getMemberLevel().equals("6") ) {
//            helper.setText(R.id.tv_content, "VIP6");
//        }

        GlideUtils.getInstances().loadNormalImg(mContext, helper.getView(R.id.iv_img), BuildConfig.BASE_IMAGE_URL + item.getHeadImg());



    }
    public interface  ChooseListener{
        void choose();
    }
    private ChooseListener listener;
    public void  setChooseListener(ChooseListener listener){
        this.listener=listener;
    }
}