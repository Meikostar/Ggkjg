package com.ggkjg.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.donkingliang.labels.LabelsView;
import com.ggkjg.R;
import com.ggkjg.common.utils.DensityUtil;
import com.ggkjg.common.utils.LogUtil;
import com.ggkjg.dto.BaseType;
import com.ggkjg.dto.GoodsAttrDto;
import com.ggkjg.dto.GoodsAttrItemDto;
import com.ggkjg.dto.GoodsSpecDto;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.mainfragment.shop.CommodityDetailActivity;
import com.ggkjg.view.widgets.Custom_TagBtn;
import com.ggkjg.view.widgets.FlexboxLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * 获取商品颜色，规格...
 * Created by dahai on 2019/01/18.
 */
public class GoodsAttrAdapter extends BaseQuickAdapter<GoodsAttrDto, BaseViewHolder> {
    private Context mContext;
    private List<Long> colorIds = new ArrayList<>();
    private GoodsSpecListener goodsSpecListener;
    private long goodsId;

    public GoodsAttrAdapter(List<GoodsAttrDto> data,long goodsId,Context mContext) {
        super(R.layout.item_shop_product_type, data);
        this.mContext = mContext;
        this.goodsId = goodsId;
    }

    public void setInitColorIds(List<GoodsAttrDto> data) {
        if(data==null||data.size()==0){
            return;
        }
        String colorIdStr = null;
        for (int i = 0; i < data.size(); i++) {
            List<GoodsAttrItemDto> goodsAttrItemDtos = data.get(i).getAttrList();
            if (goodsAttrItemDtos != null && !goodsAttrItemDtos.isEmpty()) {
                long id = goodsAttrItemDtos.get(0).getId();
                colorIds.add(id);
                if (i == 0) {
                    colorIdStr = String.valueOf(id);
                } else {
                    colorIdStr += ("," + String.valueOf(id));
                }
            }
            LogUtil.i(TAG, "--colorIds[" + i + "]" + colorIds.get(i));
        }
        findGoodsSpec(goodsId,colorIdStr);
    }
    /**
     * 创建流式布局item
     *
     * @param content
     * @return
     */
    public Custom_TagBtn createBaseFlexItemTextView(GoodsAttrItemDto content) {
        FlexboxLayout.LayoutParams lp = new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.topMargin = DensityUtil.dip2px(mContext, 10);
        lp.leftMargin = DensityUtil.dip2px(mContext, 7);
        lp.rightMargin = DensityUtil.dip2px(mContext, 7);


        Custom_TagBtn view = (Custom_TagBtn) LayoutInflater.from(mContext).inflate(R.layout.dish_item, null);
        if (content.isChoos) {
            view.setBg(R.drawable.blue_rectangle_lines);
            view.setColors(R.color.my_color_blue);
        } else {

                view.setBg(R.drawable.black_rectangle_flag);
                view.setColors(R.color.my_color_666666);


        }
        int width = (int) DensityUtil.getWidth(mContext) / 3;
        String name = content.getColorCode();
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(15);
        int with = (int) textPaint.measureText(name);
        view.setSize(with + 15, 30, 13, 1);
        view.setLayoutParams(lp);
        view.setCustomText(content.getColorCode());

        return view;
    }

    /**
     * 初始化标签适配器
     */
    private void setTagAdapter(FlexboxLayout fblTag1,final List<GoodsAttrItemDto> tags,int poition) {
        fblTag1.removeAllViews();

        if (tags.size() > 0) {
            for (int i = 0; i <tags.size(); i++) {
                final Custom_TagBtn tagBtn = createBaseFlexItemTextView(tags.get(i));
                final int position = i;
                tagBtn.setCustom_TagBtnListener(new Custom_TagBtn.Custom_TagBtnListener() {
                    @Override
                    public void clickDelete(int type) {

                        if (type == 1) {
                            for (int j = 0; j < tags.size(); j++) {
                                if (position == j) {

                                            long id = tags.get(j).getId();
                                            colorIds.set(poition, id);
                                            String colorIdStr = null;
                                            if(colorIds != null && colorIds.size() >0) {
                                                for (int i = 0; i < colorIds.size(); i++) {
                                                    if (i == 0) {
                                                        colorIdStr = String.valueOf(colorIds.get(i));
                                                    } else {
                                                        colorIdStr += ("," + String.valueOf(colorIds.get(i)));
                                                    }
                                                }
                                                findGoodsSpec(goodsId, colorIdStr);
                                            }

                                        tags.get(j).isChoos = true;


                                }else {
                                    tags.get(j).isChoos = false;
                                }
                            }
                        }

                        setTagAdapter(fblTag1,tags,poition);
                    }
                });
                fblTag1.addView(tagBtn, i);
            }



        }

    }
    @Override
    protected void convert(BaseViewHolder helper, GoodsAttrDto item) {
        if (item != null) {
            helper.setText(R.id.tv_item_select_commodity_key, item.getKey());
            FlexboxLayout labelsView = helper.getView(R.id.tv_item_select_commodity_datas);

             List<GoodsAttrItemDto> tags = new ArrayList<>();//标签数据
            List<GoodsAttrItemDto> attrList = item.getAttrList();
            if(attrList!=null&&attrList.size()>0){
                attrList.get(0).isChoos=true;
            }
            tags.addAll(attrList);
            setTagAdapter(labelsView,tags,helper.getAdapterPosition());

        }
    }

    /**
     * 获取商品规格库存
     *@param colorIds 商品id
     * @param colorIds 所有属性的ID拼接成的字符串
     */
    private void findGoodsSpec(long goodsId,String colorIds) {
        DataManager.getInstance().findGoodsSpec(new DefaultSingleObserver<GoodsSpecDto>() {
            @Override
            public void onSuccess(GoodsSpecDto object) {
                LogUtil.i(TAG, "--RxLog-Thread: onSuccess()cacaca = " + object.getStockTotal());
                if(object.getStockTotal() != null) {
                    LogUtil.i(TAG, "--RxLog-Thread: onSuccess() = " + object.getStockTotal());
                    goodsSpecListener.callbackGoodsSpec(object.getId(),Integer.valueOf(object.getStockTotal()),CommodityDetailActivity.isSekill?(object.activePrice==null?"0":object.activePrice):object.getGdPrice());
                }else{
                    LogUtil.i(TAG, "--RxLog-Thread: onSuccess()cacaca = " + object.getStockTotal());
                    goodsSpecListener.callbackGoodsSpec(object.getId(),0,"0");
                }
            }

            @Override
            public void onError(Throwable throwable) {
                LogUtil.i(TAG, "--RxLog-Thread: onError() = " + throwable.toString());
            }
        }, goodsId, colorIds);
    }

    public interface GoodsSpecListener {
        void callbackGoodsSpec(long specId,int stockTotal,String pice);
    }

    public void setGoodsSpecListener(GoodsSpecListener listener){
        this.goodsSpecListener = listener;
    }


}