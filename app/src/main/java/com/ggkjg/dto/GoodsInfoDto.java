package com.ggkjg.dto;

/**
 * Created by lihaoqi on 2019/1/30.
 */

public class GoodsInfoDto {

    private int id;
    private String orderId;
    private int goodsId;
    private int goodsNum;
    private String goodsPrice;
    private String goodsImg;
    private String goodsSpecName;
    private int goodsSpecId;
    private String goodsName;
    private String isComment;
    private String createTime;
    private String modityTime;
    private String enableFlag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getGoodsPrice() {
        return goodsPrice;
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

    public int getGoodsSpecId() {
        return goodsSpecId;
    }

    public void setGoodsSpecId(int goodsSpecId) {
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
