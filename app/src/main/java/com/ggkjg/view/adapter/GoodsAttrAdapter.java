package com.ggkjg.view.adapter;

import android.content.Context;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.donkingliang.labels.LabelsView;
import com.ggkjg.R;
import com.ggkjg.common.utils.LogUtil;
import com.ggkjg.dto.GoodsAttrDto;
import com.ggkjg.dto.GoodsAttrItemDto;
import com.ggkjg.dto.GoodsSpecDto;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.subscribers.DefaultSingleObserver;

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

    @Override
    protected void convert(BaseViewHolder helper, GoodsAttrDto item) {
        if (item != null) {
            helper.setText(R.id.tv_item_select_commodity_key, item.getKey());
            LabelsView labelsView = helper.getView(R.id.tv_item_select_commodity_datas);
            labelsView.setSelectType(LabelsView.SelectType.SINGLE_IRREVOCABLY);
            labelsView.setLabels(item.getAttrList(), new LabelsView.LabelTextProvider<GoodsAttrItemDto>() {
                @Override
                public CharSequence getLabelText(TextView label, int position, GoodsAttrItemDto data) {
                    return data.getColorCode();
                }
            });
            labelsView.setOnLabelClickListener(new LabelsView.OnLabelClickListener() {
                @Override
                public void onLabelClick(TextView label, Object data, int index) {
                    long id = getData().get(helper.getAdapterPosition()).getAttrList().get(index).getId();
                    colorIds.set(helper.getAdapterPosition(), id);
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
                }
            });
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
                    goodsSpecListener.callbackGoodsSpec(object.getId(),Integer.valueOf(object.getStockTotal()));
                }else{
                    LogUtil.i(TAG, "--RxLog-Thread: onSuccess()cacaca = " + object.getStockTotal());
                    goodsSpecListener.callbackGoodsSpec(object.getId(),0);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                LogUtil.i(TAG, "--RxLog-Thread: onError() = " + throwable.toString());
            }
        }, goodsId, colorIds);
    }

    public interface GoodsSpecListener {
        void callbackGoodsSpec(long specId,int stockTotal);
    }

    public void setGoodsSpecListener(GoodsSpecListener listener){
        this.goodsSpecListener = listener;
    }


}