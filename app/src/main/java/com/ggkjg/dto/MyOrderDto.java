package com.ggkjg.dto;

import java.util.List;

public class MyOrderDto {

    /**
     * id : 50
     * orderNo : 1548753463087
     * memberId : 8
     * contactName : 46545454
     * mobileNo : 13578945698
     * address : 天津天津市和平区154545454448494
     * deliverType : 2
     * deliverMoney : 0
     * goodsTotalMoney : 600
     * orderTotalMoney : 600
     * realOrderMoney : 0
     * orderStatus : 1
     * payStatus : 2
     * orderScore : 0
     * expressId : null
     * expressNo : null
     * tradeNo : null
     * receiveTime : null
     * deliveryTime : null
     * payTime : null
     * paymentId : null
     * remark : null
     * createTime : 2019-01-29 17:17:43
     * modityTime : null
     * enableFlag : 1
     */

    private String id;
    private String orderNo;
    private String memberId;
    private String contactName;
    private String mobileNo;
    private String address;
    private String deliverType;
    private String deliverMoney;
    private String goodsTotalMoney;
    private String orderTotalMoney;
    private String realOrderMoney;
    private String orderStatus;
    private String payStatus;
    private String orderScore;
    private String expressId;
    private String expressNo;
    private String tradeNo;
    private String receiveTime;
    private String deliveryTime;
    private String payTime;
    private String paymentId;
    private String remark;
    private String createTime;
    private String modityTime;
    private String enableFlag;
    private String weight;
    private String isComment;
    private List<MyOrderItemDto> goodsList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDeliverType() {
        return deliverType;
    }

    public void setDeliverType(String deliverType) {
        this.deliverType = deliverType;
    }

    public String getDeliverMoney() {
        return deliverMoney;
    }

    public void setDeliverMoney(String deliverMoney) {
        this.deliverMoney = deliverMoney;
    }

    public String getGoodsTotalMoney() {
        return goodsTotalMoney;
    }

    public void setGoodsTotalMoney(String goodsTotalMoney) {
        this.goodsTotalMoney = goodsTotalMoney;
    }

    public String getOrderTotalMoney() {
        return orderTotalMoney;
    }

    public void setOrderTotalMoney(String orderTotalMoney) {
        this.orderTotalMoney = orderTotalMoney;
    }

    public String getRealOrderMoney() {
        return realOrderMoney;
    }

    public void setRealOrderMoney(String realOrderMoney) {
        this.realOrderMoney = realOrderMoney;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getOrderScore() {
        return orderScore;
    }

    public void setOrderScore(String orderScore) {
        this.orderScore = orderScore;
    }

    public String getExpressId() {
        return expressId;
    }

    public void setExpressId(String expressId) {
        this.expressId = expressId;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModityTime() {
        return modityTime;
    }

    public void setModityTime(String modityTime) {
        this.modityTime = modityTime;
    }

    public String getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
    }

    public String getIsComment() {
        return isComment;
    }

    public void setIsComment(String isComment) {
        this.isComment = isComment;
    }

    public List<MyOrderItemDto> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<MyOrderItemDto> goodsList) {
        this.goodsList = goodsList;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
