package com.ggkjg.dto;

import java.io.Serializable;

public class MyOrderItemDto implements Serializable {

    /**
     * id : 28
     * orderId : 50
     * goodsId : 1
     * goodsNum : 1
     * goodsPrice : 600
     * goodsImg : /static/uploads/image/test_detail.jpg
     * goodsSpecName : 红色/100ML
     * goodsSpecId : 1
     * goodsName : 测试商品1
     * isComment : 2
     * createTime : 2019-01-29 17:17:43
     * modityTime : 2019-01-29 17:17:43
     * enableFlag : 1
     */

    private String id;
    private String orderId;
    private String goodsId;
    private String goodsNum;
    private String goodsPrice;
    private String goodsImg;
    private String goodsSpecName;
    private String goodsSpecId;
    private String goodsName;
    private String isComment;
    private String createTime;
    private String modityTime;
    private String enableFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(String goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public String getGoodsSpecName() {
        return goodsSpecName;
    }

    public void setGoodsSpecName(String goodsSpecName) {
        this.goodsSpecName = goodsSpecName;
    }

    public String getGoodsSpecId() {
        return goodsSpecId;
    }

    public void setGoodsSpecId(String goodsSpecId) {
        this.goodsSpecId = goodsSpecId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getIsComment() {
        return isComment;
    }

    public void setIsComment(String isComment) {
        this.isComment = isComment;
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
}
