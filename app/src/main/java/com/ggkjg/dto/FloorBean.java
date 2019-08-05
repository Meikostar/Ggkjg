package com.ggkjg.dto;

import java.util.List;

/**
 * Created by lihaoqi on 2019/1/30.
 */

public class FloorBean {

    private int type;

    private GoodsOrderDetailDto orderDetail;

    private List<GoodsPushRowsDto> goodsPushdetails;

    private boolean isHasTitle;

    public GoodsOrderDetailDto getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(GoodsOrderDetailDto orderDetail) {
        this.orderDetail = orderDetail;
    }

    public List<GoodsPushRowsDto> getGoodsPushdetails() {
        return goodsPushdetails;
    }

    public void setGoodsPushdetails(List<GoodsPushRowsDto> goodsPushdetails) {
        this.goodsPushdetails = goodsPushdetails;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isHasTitle() {
        return isHasTitle;
    }

    public void setHasTitle(boolean hasTitle) {
        isHasTitle = hasTitle;
    }
}
