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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ggkjg.R;
import com.ggkjg.base.BuildConfig;
import com.ggkjg.common.utils.GlideUtils;
import com.ggkjg.common.utils.TextUtil;
import com.ggkjg.dto.BusinessListDto;
import com.ggkjg.dto.HomeDto;
import com.ggkjg.dto.HomeGoodsIndexDto;
import com.ggkjg.view.mainfragment.shop.CommodityDetailActivity;
import com.ggkjg.view.mainfragment.spike.ArticleActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mykar on 161/4/13.
 */

public class BussListAdapter extends BaseAdapter {
    private Context               context;
    private LayoutInflater        inflater;
    private List<BusinessListDto> data;
    private int                   type = 0;//0 表示默认使用list数据
    private String                types;


    private String[] names;

    public void setType(int type) {
        this.type = type;
        notifyDataSetChanged();
    }

    public void setTypes(String types) {
        this.types = types;
        notifyDataSetChanged();
    }

    public BussListAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<BusinessListDto> data) {
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

            view = inflater.inflate(R.layout.item_business_list_layout, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        BusinessListDto item = data.get(i);
        if(TextUtil.isNotEmpty(item.cmsTitle)){
            holder.tv_title.setText(item.cmsTitle);
        }
        if(TextUtil.isNotEmpty(item.createDate)){
            holder.tv_content.setText(item.createDate);

        }

        holder.ll_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ArticleActivity.class);
                intent.putExtra("id",item.id);
                context.startActivity(intent);
            }
        });
        String imgUrl = BuildConfig.BASE_IMAGE_URL + item.cmsMainImg;
        GlideUtils.getInstances().loadNormalImg(context, holder.iv_img, imgUrl, R.mipmap.img_default_6);

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
        @BindView(R.id.iv_img)
        ImageView iv_img;
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_content)
        TextView tv_content;
        @BindView(R.id.ll_bg)
        RelativeLayout ll_bg;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
