package com.ggkjg.dto;

import java.io.Serializable;

/**
 * 商品详细
 * Created by Dahai on 2018/12/18.
 */

public class CommodityDetailInfoDto implements Serializable {
    private long id;
    private String barCode;
    private String goodsSn;
    public String isConpon;
    private String productNo;
    private String goodsName;
    private String goodsImg;
    private String marketPrice;
    public String conponPrice;
    private String gdPrice;
    private long oneCatsId;
    private long twoCatsId;
    private long threeCatsId;
    private String isBest;
    private String isHot;
    private String isNew;
    private String isRecom;
    private String isColor;
    private String isSpec;
    private String appraiseTotal;
    private String appraiseRate;
    private String guarantee;
    private String deliver;
    private int stockTotal;
    private int visitTotal;
    private int salesToatl;
    private long tempId;
    private String freight;
    private String freightType;
    private String isSales;
    public String salesTime;
    public String startTime;
    public String activePrice;
    public String endTime;
    private String createTime;
    private String modifyTime;
    private String enableFlag;
    private String goodsDetail;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getGoodsSn() {
        return goodsSn;
    }

    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public String getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(String marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getGdPrice() {
        return gdPrice;
    }

    public void setGdPrice(String gdPrice) {
        this.gdPrice = gdPrice;
    }

    public long getOneCatsId() {
        return oneCatsId;
    }

    public void setOneCatsId(long oneCatsId) {
        this.oneCatsId = oneCatsId;
    }

    public long getTwoCatsId() {
        return twoCatsId;
    }

    public void setTwoCatsId(long twoCatsId) {
        this.twoCatsId = twoCatsId;
    }

    public long getThreeCatsId() {
        return threeCatsId;
    }

    public void setThreeCatsId(long threeCatsId) {
        this.threeCatsId = threeCatsId;
    }

    public String getIsBest() {
        return isBest;
    }

    public void setIsBest(String isBest) {
        this.isBest = isBest;
    }

    public String getIsHot() {
        return isHot;
    }

    public void setIsHot(String isHot) {
        this.isHot = isHot;
    }

    public String getIsNew() {
        return isNew;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }

    public String getIsRecom() {
        return isRecom;
    }

    public void setIsRecom(String isRecom) {
        this.isRecom = isRecom;
    }

    public String getIsColor() {
        return isColor;
    }

    public void setIsColor(String isColor) {
        this.isColor = isColor;
    }

    public String getIsSpec() {
        return isSpec;
    }

    public void setIsSpec(String isSpec) {
        this.isSpec = isSpec;
    }

    public String getAppraiseTotal() {
        return appraiseTotal;
    }

    public void setAppraiseTotal(String appraiseTotal) {
        this.appraiseTotal = appraiseTotal;
    }

    public String getAppraiseRate() {
        return appraiseRate;
    }

    public void setAppraiseRate(String appraiseRate) {
        this.appraiseRate = appraiseRate;
    }

    public String getGuarantee() {
        return guarantee;
    }

    public void setGuarantee(String guarantee) {
        this.guarantee = guarantee;
    }

    public String getDeliver() {
        return deliver;
    }

    public void setDeliver(String deliver) {
        this.deliver = deliver;
    }

    public int getStockTotal() {
        return stockTotal;
    }

    public void setStockTotal(int stockTotal) {
        this.stockTotal = stockTotal;
    }

    public int getVisitTotal() {
        return visitTotal;
    }

    public void setVisitTotal(int visitTotal) {
        this.visitTotal = visitTotal;
    }

    public int getSalesToatl() {
        return salesToatl;
    }

    public void setSalesToatl(int salesToatl) {
        this.salesToatl = salesToatl;
    }

    public long getTempId() {
        return tempId;
    }

    public void setTempId(long tempId) {
        this.tempId = tempId;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getFreightType() {
        return freightType;
    }

    public void setFreightType(String freightType) {
        this.freightType = freightType;
    }

    public String getIsSales() {
        return isSales;
    }

    public void setIsSales(String isSales) {
        this.isSales = isSales;
    }

    public String getSalesTime() {
        return salesTime;
    }

    public void setSalesTime(String salesTime) {
        this.salesTime = salesTime;
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

    public String getGoodsDetail() {
        return goodsDetail;
    }

    public void setGoodsDetail(String goodsDetail) {
        this.goodsDetail = goodsDetail;
    }
}
