package com.ggkjg.dto;

import java.io.Serializable;
import java.util.List;

public class ShopEvaluateRowsDto implements Serializable {
    //    "id": 35,
//            "orderId": 124,
//            "memberId": 5,
//            "nickName": "Ficks",
//            "headImg": "/static/uploads/image/head/abdd1eb5-605c-4935-bb2d-59e9b2419633.png",
//            "goodsId": 1,
//            "specId": 1,
//            "goodsScore": 5.00,
//            "serviceScore": 5.00,
//            "timeScore": 5.00,
//            "content": "逛公园",
//            "replay": null,
//            "replayTime": null,
//            "isImg": "2",
//            "createTime": "2019-02-15 17:49:33",
//            "modifyTime": "2019-02-15 17:49:33",
//            "enableFlag": "1",
//            "imgList": []
    private long id;
    private long orderId;
    private long memberId;
    private String nickName;
    private String headImg;
    private long goodsId;
    private long specId;
    private double goodsScore;
    private double serviceScore;
    private double timeScore;
    private String content;
    private Object replay;
    private String replayTime;
    private String isImg;
    private String createTime;
    private String modifyTime;
    private String enableFlag;
    private List<ShopEvaluateImgListDto> imgList;

    private List<MyOrderItemDto> goodsList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }

    public long getSpecId() {
        return specId;
    }

    public void setSpecId(long specId) {
        this.specId = specId;
    }

    public double getGoodsScore() {
        return goodsScore;
    }

    public void setGoodsScore(double goodsScore) {
        this.goodsScore = goodsScore;
    }

    public double getServiceScore() {
        return serviceScore;
    }

    public void setServiceScore(double serviceScore) {
        this.serviceScore = serviceScore;
    }

    public double getTimeScore() {
        return timeScore;
    }

    public void setTimeScore(double timeScore) {
        this.timeScore = timeScore;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Object getReplay() {
        return replay;
    }

    public void setReplay(Object replay) {
        this.replay = replay;
    }

    public String getReplayTime() {
        return replayTime;
    }

    public void setReplayTime(String replayTime) {
        this.replayTime = replayTime;
    }

    public String getIsImg() {
        return isImg;
    }

    public void setIsImg(String isImg) {
        this.isImg = isImg;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
    }

    public List<ShopEvaluateImgListDto> getImgList() {
        return imgList;
    }

    public void setImgList(List<ShopEvaluateImgListDto> imgList) {
        this.imgList = imgList;
    }

    public List<MyOrderItemDto> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<MyOrderItemDto> goodsList) {
        this.goodsList = goodsList;
    }
}
