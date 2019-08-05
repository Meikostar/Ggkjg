package com.ggkjg.view.adapter;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.annotation.ItemProviderTag;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.ggkjg.R;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.LogUtil;
import com.ggkjg.dto.FloorBean;
import com.ggkjg.dto.GoodsInfoDto;
import com.ggkjg.dto.GoodsOrderDetailDto;
import com.ggkjg.view.mainfragment.order.MyOrderDetailActivity;

import java.util.HashMap;
import java.util.List;

/**
 * Created by lihaoqi on 2019/1/30.
 */

@ItemProviderTag(
        viewType = 0,
        layout = R.layout.item_my_order_list
)
public class OrderListProvider extends BaseItemProvider<FloorBean,BaseViewHolder> {

    private HashMap<String,String> mHashMap = new HashMap<String,String>(){
        {
            this.put("1","待付款");
            this.put("2","待发货");
            this.put("3","待收货");
            this.put("4","待评价");
            this.put("5","已完成");
            this.put("0","已取消");
        }
    };

    @Override
    public void convert(BaseViewHolder helper, FloorBean data, int position) {
//        RecyclerView rvList = helper.getView(R.id.rv_list);
//        rvList.setLayoutManager(new LinearLayoutManager(mContext));
//        MyOrderAdapter orderAdapter = new MyOrderAdapter();
//        rvList.setAdapter(orderAdapter);
//        orderAdapter.setNewData(data.getOrderList());
        LogUtil.i("MyOrderAdapter:",helper.getPosition()+"");
        GoodsOrderDetailDto item = data.getOrderDetail();
        String orderStatus = item.getOrderStatus();
        String statusTxt = mHashMap.get(orderStatus);
        if (!TextUtils.isEmpty(statusTxt)){
            helper.setText(R.id.tv_status,statusTxt);
        }else {
            helper.setText(R.id.tv_status,"");
        }
        RecyclerView rvList = helper.getView(R.id.rv_list);
        LinearLayoutManager llManager = new LinearLayoutManager(mContext);
        llManager.setAutoMeasureEnabled(true);
        rvList.setLayoutManager(llManager);
        List<GoodsInfoDto> goodsInfoDtoList = item.getGoodsList();
        GoodsListAdapter goodsListAdapter = new GoodsListAdapter(goodsInfoDtoList);
        rvList.setAdapter(goodsListAdapter);
        goodsListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GoodsInfoDto goodsInfoDto = goodsListAdapter.getItem(position);
                Intent intent = new Intent(mContext,MyOrderDetailActivity.class);
                intent.putExtra(Constants.ORDER_ID,goodsInfoDto.getOrderId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(BaseViewHolder helper, FloorBean data, int position) {

    }

    @Override
    public boolean onLongClick(BaseViewHolder helper, FloorBean data, int position) {
        return false;
    }
}
