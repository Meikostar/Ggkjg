package com.ggkjg.view.adapter;

import android.content.Context;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ggkjg.R;
import com.ggkjg.common.utils.GlideUtils;
import com.ggkjg.common.utils.TextUtil;
import com.ggkjg.dto.HomeDto;
import com.ggkjg.dto.HomeGoodsIndexDto;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mykar on 161/4/13.
 */

public class HomeGoodSpikeAdapter extends BaseAdapter {
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

    public HomeGoodSpikeAdapter(Context context) {
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

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_good_gab_view, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        GlideUtils.getInstances().loadNormalImg(context,holder.tvItemHomeGoodShopImg,data.get(i).goodsImg);
        if(TextUtil.isNotEmpty(data.get(i).goodsName)){
            holder.tvTitle.setText(data.get(i).goodsName);
        }
        if(TextUtil.isNotEmpty(data.get(i).gdPrice)){
            holder.tvItemHomeGoodShopPrice.setText(data.get(i).gdPrice);
        }
        if(TextUtil.isNotEmpty(data.get(i).marketPrice)){
            String name = data.get(i).marketPrice;
            TextPaint textPaint = new TextPaint();
            textPaint.setTextSize(12);
            int with = (int) textPaint.measureText(name);
            FrameLayout.LayoutParams linearParams =(FrameLayout.LayoutParams) holder.line.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20

            linearParams.width = with+5;// 控件的宽强制设成30

            holder.line.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
            holder.tvPrice.setText(data.get(i).marketPrice);
        }

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
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_item_home_good_shop_price)
        TextView tvItemHomeGoodShopPrice;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.line)
        View line;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
