package com.ggkjg.dto;

public class CollectionDto {

    /**
     * goodsName : 商品名
     * goodsImg : 商品图片
     * gdPrice : 价格
     * goodsId : 商品id
     */

    private String goodsName;
    private String goodsImg;
    private String gdPrice;
    private long goodsId;

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

    public String getGdPrice() {
        return gdPrice;
    }

    public void setGdPrice(String gdPrice) {
        this.gdPrice = gdPrice;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }
}
