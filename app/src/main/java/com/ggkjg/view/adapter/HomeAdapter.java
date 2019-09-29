package com.ggkjg.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ggkjg.R;
import com.ggkjg.base.BuildConfig;
import com.ggkjg.common.utils.GlideUtils;
import com.ggkjg.common.utils.TextUtil;
import com.ggkjg.db.SeachHistotyDBUtil;
import com.ggkjg.db.bean.SearchHistory;
import com.ggkjg.dto.FloorBean;
import com.ggkjg.dto.GoodsPushRowsDto;
import com.ggkjg.dto.HomeGoodsIndexDto;
import com.ggkjg.view.mainfragment.shop.CommodityDetailActivity;

import java.util.Collections;
import java.util.List;


/**
 * 主页新品推荐
 * Created by dahai on 2019/01/18.
 */
public class HomeAdapter extends BaseQuickAdapter<GoodsPushRowsDto, BaseViewHolder> {
    private Context mContext;

    public HomeAdapter(List<GoodsPushRowsDto> data, Context mContext) {
        super(R.layout.item_home_layout, data);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsPushRowsDto item) {
        if (item != null) {
            helper.setText(R.id.tv_item_home_title, item.getGoodsName());
            helper.setText(R.id.tv_item_home_price, item.getGdPrice());
            helper.setText(R.id.tv_item_home_comments, (item.getAppraiseTotal() == null ? 0 : item.getAppraiseTotal()) + "条评论");
            helper.setText(R.id.tv_item_home_comments_scale, (item.getAppraiseRate() == null ? 0 : item.getAppraiseRate()) + "%好评");
            String imgUrl = BuildConfig.BASE_IMAGE_URL + item.getGoodsImg();
            GlideUtils.getInstances().loadNormalImg(mContext, helper.getView(R.id.iv_item_home_img), imgUrl, R.mipmap.img_default_6);
            //多处调用新品推荐，统一添加点击
            helper.getView(R.id.iv_item_home_push).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putLong(CommodityDetailActivity.PRODUCT_ID, item.getId());
                    gotoActivity(CommodityDetailActivity.class, bundle);
                }
            });
            LinearLayout view = helper.getView(R.id.ll_zone);
            if(item.isConpon.equals("1")){
                view.setVisibility(View.VISIBLE);

                if(TextUtil.isNotEmpty(item.conponPrice)&&Double.valueOf(item.conponPrice)!=0){
                    helper.setText(R.id.tv_cout,Double.valueOf(item.conponPrice).intValue()+"港券");
                }else {
                    view.setVisibility(View.GONE);
                }
            }else {
                view.setVisibility(View.GONE);
            }
        }
    }

    public void gotoActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(mContext, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        mContext.startActivity(intent);
    }

}