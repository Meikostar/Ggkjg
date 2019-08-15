package com.ggkjg.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ggkjg.R;
import com.ggkjg.common.utils.GlideUtils;
import com.ggkjg.common.utils.TextUtil;
import com.ggkjg.dto.HomeDto;
import com.ggkjg.dto.HomeGoodsIndexDto;
import com.ggkjg.view.mainfragment.shop.CommodityDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mykar on 161/4/13.
 */

public class HomeZoneAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<HomeDto> data;
    private int type = 0;//0 表示默认使用list数据
    private String types;


    private String[] names;

    public void setType(int type) {
        this.type = type;
        notifyDataSetChanged();
    }

    public void setTypes(String types) {
        this.types = types;
        notifyDataSetChanged();
    }

    public HomeZoneAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<HomeDto> data) {
        this.data = data;

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

            view = inflater.inflate(R.layout.item_home_zone_view, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        GlideUtils.getInstances().loadNormalImg(context,holder.tvItemHomeGoodShopImg,data.get(i).goodsImg);
        if(TextUtil.isNotEmpty(data.get(i).goodsName)){
            holder.tvTitle.setText(data.get(i).goodsName);
        }
        if(TextUtil.isNotEmpty(data.get(i).goodsName)){
            holder.tvTitle.setText(data.get(i).goodsName);
        }
        if(TextUtil.isNotEmpty(data.get(i).gdPrice)){
            holder.tvItemHomeGoodShopPrice.setText(data.get(i).gdPrice);
        }
        if(data.get(i).isConpon.equals("1")){
            holder.llZone.setVisibility(View.VISIBLE);
            if(TextUtil.isNotEmpty(data.get(i).conponPrice)){
                holder.tv_cout.setText(Double.valueOf(data.get(i).conponPrice).intValue()+"港券");
            }
        }else {
            holder.llZone.setVisibility(View.GONE);
        }
        holder.ll_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(context,CommodityDetailActivity.class);

                bundle.putLong(CommodityDetailActivity.PRODUCT_ID, Long.valueOf(data.get(i).id));
                if (bundle != null) {
                    intent.putExtras(bundle);
                }
                context.startActivity(intent);
            }
        });
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


    static
    class ViewHolder {
        @BindView(R.id.tv_item_home_good_shop_img)
        ImageView tvItemHomeGoodShopImg;
        @BindView(R.id.ll_zone)
        LinearLayout llZone;
        @BindView(R.id.ll_bg)
        LinearLayout ll_bg;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_cout)
        TextView tv_cout;
        @BindView(R.id.tv_item_home_good_shop_price)
        TextView tvItemHomeGoodShopPrice;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
