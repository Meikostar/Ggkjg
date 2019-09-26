package com.ggkjg.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ggkjg.R;
import com.ggkjg.base.BuildConfig;
import com.ggkjg.common.utils.DensityUtil;
import com.ggkjg.common.utils.GlideUtils;
import com.ggkjg.common.utils.LogUtil;
import com.ggkjg.common.utils.TextUtil;
import com.ggkjg.dto.BusinessListDto;
import com.ggkjg.dto.GoodsAttrDto;
import com.ggkjg.dto.GoodsAttrItemDto;
import com.ggkjg.dto.GoodsSpecDto;
import com.ggkjg.dto.HomeGoodsIndexDto;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.mainfragment.shop.CommodityDetailActivity;
import com.ggkjg.view.mainfragment.spike.ArticleActivity;
import com.ggkjg.view.widgets.Custom_TagBtn;
import com.ggkjg.view.widgets.FlexboxLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mykar on 161/4/13.
 */

public class GoodsAtrrsAdapter extends BaseAdapter {
    private Context               context;
    private LayoutInflater        inflater;
    private List<GoodsAttrDto> data;
    private int                   type = 0;//0 表示默认使用list数据
    private String                types;

    private long goodsId;
    private String[] names;
    private List<Long> colorIds = new ArrayList<>();
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

        }
        findGoodsSpec(goodsId,colorIdStr);
    }
    private GoodsSpecListener goodsSpecListener;
    /**
     * 获取商品规格库存
     *@param colorIds 商品id
     * @param colorIds 所有属性的ID拼接成的字符串
     */
    private void findGoodsSpec(long goodsId,String colorIds) {
        DataManager.getInstance().findGoodsSpec(new DefaultSingleObserver<GoodsSpecDto>() {
            @Override
            public void onSuccess(GoodsSpecDto object) {

                if(object.getStockTotal() != null) {
                    goodsSpecListener.callbackGoodsSpec(object.getId(),Integer.valueOf(object.getStockTotal()),CommodityDetailActivity.isSekill?(object.activePrice==null?"0":object.activePrice):object.getGdPrice());
                }else{

                    goodsSpecListener.callbackGoodsSpec(object.getId(),0,"0");
                }
            }

            @Override
            public void onError(Throwable throwable) {

            }
        }, goodsId, colorIds);
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
        lp.topMargin = DensityUtil.dip2px(context, 10);
        lp.leftMargin = DensityUtil.dip2px(context, 7);
        lp.rightMargin = DensityUtil.dip2px(context, 7);


        Custom_TagBtn view = (Custom_TagBtn) LayoutInflater.from(context).inflate(R.layout.dish_item, null);
        if (content.isChoos) {
            view.setBg(R.drawable.blue_rectangle_lines);
            view.setColors(R.color.my_color_blue);
        } else {

            view.setBg(R.drawable.black_rectangle_flag);
            view.setColors(R.color.my_color_666666);


        }
        int width = (int) DensityUtil.getWidth(context) / 3;
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
    public GoodsAtrrsAdapter(Context context,long goodsId) {
        this.context = context;
        this.goodsId = goodsId;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<GoodsAttrDto> data) {
        this.data = data;
         notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data != null ? data.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {

            view = inflater.inflate(R.layout.item_shop_product_type, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        GoodsAttrDto item = data.get(i);
        holder.tv_item_select_commodity_key.setText(item.getKey());


        List<GoodsAttrItemDto> tags = new ArrayList<>();//标签数据
        List<GoodsAttrItemDto> attrList = item.getAttrList();
        if(attrList!=null&&attrList.size()>0){
            attrList.get(0).isChoos=true;
        }
        tags.addAll(attrList);
        setTagAdapter(holder.labelsView,tags,i);

        // PROFILE_ITEM item = list.get(i);
        return view;
    }

    //新建两个内部接口：
    public interface OnItemClickListener {
        void onItemClick(HomeGoodsIndexDto url);
    }

    //新建两个私有变量用于保存用户设置的监听器及其set方法：
    private OnItemClickListener mOnItemClickListener;


    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


    public interface GoodsSpecListener {
        void callbackGoodsSpec(long specId,int stockTotal,String pice);
    }

    public void setGoodsSpecListener(GoodsSpecListener listener){
        this.goodsSpecListener = listener;
    }
    static
    class ViewHolder {
        @BindView(R.id.tv_item_select_commodity_datas)
        FlexboxLayout labelsView;
        @BindView(R.id.tv_item_select_commodity_key)
        TextView tv_item_select_commodity_key;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
