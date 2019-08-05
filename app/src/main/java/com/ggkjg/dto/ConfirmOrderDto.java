package com.ggkjg.dto;

import java.io.Serializable;

public class ConfirmOrderDto implements Serializable {
    String orderNo;
    long orderId;
    String realOrderMoney;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getRealOrderMoney() {
        return realOrderMoney;
    }

    public void setRealOrderMoney(String realOrderMoney) {
        this.realOrderMoney = realOrderMoney;
    }
}
