package com.ggkjg.dto;

import java.io.Serializable;

public class ShopCartDto  implements Serializable {

    /**
     * goodsImg : /static/uploads/image/test_detail.jpg
     * stockName : 100
     * specName : 红色/100ML
     * id : 17
     * goodsName : 测试商品1
     * cartNum : 1
     * gdPrice : 600.0
     */

    private String goodsImg;
    private int stockName;
    private String specName;
    private long id;
    private String goodsName;
    private int cartNum;
    public int conponCount;
    private double gdPrice;
    private double weight;
    private boolean isSelect; //是否选中

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public int getStockName() {
        return stockName;
    }

    public void setStockName(int stockName) {
        this.stockName = stockName;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getCartNum() {
        return cartNum;
    }

    public void setCartNum(int cartNum) {
        this.cartNum = cartNum;
    }

    public double getGdPrice() {
        return gdPrice;
    }

    public void setGdPrice(double gdPrice) {
        this.gdPrice = gdPrice;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
