package com.ggkjg.view.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.ggkjg.R;
import com.ggkjg.base.BuildConfig;
import com.ggkjg.common.utils.GlideUtils;
import com.ggkjg.dto.OrderPreviewSellersDto;
import com.ggkjg.dto.ShopCartDto;

import java.util.List;

/**
 * 确认订单
 */
public class GoodsOrderAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<OrderPreviewSellersDto> dataList;
    private ReceiveGoodListener goodListener;

    public GoodsOrderAdapter(Context context) {
        this.mContext = context;
    }

    public void setNewData(List<OrderPreviewSellersDto> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public void setGoodListener(ReceiveGoodListener goodListener) {
        this.goodListener = goodListener;
    }

    @Override //获取分组的个数(也就是这里的文件夹个数)
    public int getGroupCount() {
        return dataList != null ? dataList.size() : 0;
    }

    @Override//获取指定分组中子选项的个数
    public int getChildrenCount(int groupPosition) {
        int childCount = 0;
        if (dataList.get(groupPosition).getProduct() != null && dataList.get(groupPosition).getProduct() != null) {
            childCount = dataList.get(groupPosition).getProduct().size();
        }
        return childCount;
    }

    @Override//获取指定的分组数据
    public OrderPreviewSellersDto getGroup(int groupPosition) {
        return dataList.get(groupPosition);
    }

    @Override //获取指定分组中指定子选项的数据
    public ShopCartDto getChild(int groupPosition, int childPosition) {
        return dataList.get(groupPosition).getProduct().get(childPosition);
    }

    @Override//获取指定分组的ID, 这个ID必须是唯一的
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override//获取子选项的ID, 这个ID必须是唯一的
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override//分组和子选项是否持有稳定的ID, 就是说底层数据的改变会不会影响到它们。
    public boolean hasStableIds() {
        return true;
    }

    @Override//获取显示指定分组的视图
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        HeaderViewHolder headerViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_header_layout, parent, false);
            headerViewHolder = new HeaderViewHolder(convertView);
            convertView.setTag(headerViewHolder);
        } else {
            headerViewHolder = (HeaderViewHolder) convertView.getTag();
        }
        OrderPreviewSellersDto item = dataList.get(groupPosition);
//        String imgUrl = Constants.getInstance().get(Constants.SYS_PATH);
//        GlideUtils.getInstances().loadNormalImg(mContext, headerViewHolder.iv_header_img, imgUrl, R.mipmap.img_default_4);
        headerViewHolder.tv_header_title.setText(item.getTitle());
        return convertView;
    }

    @Override//获取显示指定分组中的指定子选项的视图
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_goods_order_layout, parent, false);
            childViewHolder = new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }

        ShopCartDto item = getChild(groupPosition, childPosition);
        GlideUtils.getInstances().loadNormalImg(mContext, childViewHolder.iv_goods_order_img, BuildConfig.BASE_IMAGE_URL + item.getGoodsImg(), R.mipmap.img_default_1);
        childViewHolder.tv_goods_order_title.setText(item.getGoodsName());
        childViewHolder.tv_goods_order_qty.setText("X"+item.getCartNum());
        childViewHolder.tv_goods_order_price.setText(item.getGdPrice() + "");
        childViewHolder.tv_goods_order_type.setText(item.getSpecName());
        return convertView;
    }

    @Override//指定位置上的子元素是否可选中
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class ChildViewHolder {
        ImageView iv_goods_order_img;
        TextView tv_goods_order_title;
        TextView tv_goods_order_type;
        TextView tv_goods_order_price;
        TextView tv_goods_order_qty;

        ChildViewHolder(View view) {
            iv_goods_order_img = view.findViewById(R.id.iv_goods_order_img);
            tv_goods_order_title = view.findViewById(R.id.tv_goods_order_title);
            tv_goods_order_type = view.findViewById(R.id.tv_goods_order_type);
            tv_goods_order_price = view.findViewById(R.id.tv_goods_order_price);
            tv_goods_order_qty = view.findViewById(R.id.tv_goods_order_qty);
        }
    }

    class HeaderViewHolder {
        TextView tv_header_title;
        ImageView iv_header_img;

        HeaderViewHolder(View view) {
            tv_header_title = view.findViewById(R.id.tv_header_title);
            iv_header_img = view.findViewById(R.id.iv_header_img);
        }
    }

    //设置展开收起的指示器
    public void setIndicator(int position, boolean isExpanded) {

    }

    public interface ReceiveGoodListener {
        public void receiveGood(String id);
    }
}

