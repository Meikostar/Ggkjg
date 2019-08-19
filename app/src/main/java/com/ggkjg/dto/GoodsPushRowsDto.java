package com.ggkjg.dto;

import java.io.Serializable;

/**
 * 商品推荐
 * Created by Dahai on 2018/12/18.
 */

public class GoodsPushRowsDto implements Serializable  {
    private long id;
    private String barCode;
    private String goodsSn;
    private String productNo;
    public String conponPrice;
    public String isConpon;
    private String goodsName;
    private String goodsImg;
    private String marketPrice;
    private String gdPrice;
    private long oneCatsId;
    private long twoCatsId;
    private long threeCatsId;
    private int isBest;
    private int isHot;
    private int isNew;
    private int isRecom;
    private int isColor;
    private int isSpec;
    private String appraiseTotal;
    private String appraiseRate;
    private String guarantee;
    private String deliver;
    private String stockTotal;
    private String visitTotal;
    private String salesToatl;
    private long tempId;
    private String freight;
    private String freightType;
    private int isSales;
    private String salesTime;
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

    public int getIsBest() {
        return isBest;
    }

    public void setIsBest(int isBest) {
        this.isBest = isBest;
    }

    public int getIsHot() {
        return isHot;
    }

    public void setIsHot(int isHot) {
        this.isHot = isHot;
    }

    public int getIsNew() {
        return isNew;
    }

    public void setIsNew(int isNew) {
        this.isNew = isNew;
    }

    public int getIsRecom() {
        return isRecom;
    }

    public void setIsRecom(int isRecom) {
        this.isRecom = isRecom;
    }

    public int getIsColor() {
        return isColor;
    }

    public void setIsColor(int isColor) {
        this.isColor = isColor;
    }

    public int getIsSpec() {
        return isSpec;
    }

    public void setIsSpec(int isSpec) {
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

    public String getStockTotal() {
        return stockTotal;
    }

    public void setStockTotal(String stockTotal) {
        this.stockTotal = stockTotal;
    }

    public String getVisitTotal() {
        return visitTotal;
    }

    public void setVisitTotal(String visitTotal) {
        this.visitTotal = visitTotal;
    }

    public String getSalesToatl() {
        return salesToatl;
    }

    public void setSalesToatl(String salesToatl) {
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

    public int getIsSales() {
        return isSales;
    }

    public void setIsSales(int isSales) {
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
