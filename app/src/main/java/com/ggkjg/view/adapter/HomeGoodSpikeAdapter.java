package com.ggkjg.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ggkjg.R;
import com.ggkjg.dto.HomeGoodsIndexDto;


import java.util.List;

/**
 * Created by mykar on 161/4/13.
 */

public class HomeGoodSpikeAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<HomeGoodsIndexDto> data;
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

    public void setData(List<HomeGoodsIndexDto> data) {
       this.data=data;

    }

    @Override
    public int getCount() {
        return data!=null?data.size():0;
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
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.item_good_gab_view, null);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
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

    class ViewHolder {
        LinearLayout llbg;
        TextView txt_name;
        ImageView img_icon;

    }
}
