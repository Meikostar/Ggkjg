package com.ggkjg.dto;

import java.io.Serializable;

/**
 *  确认订单页面获取商品重量，总价，快递方式，数目
 *  Created by rzb on 2019/3/15
 */

public class CartAtrrDto implements Serializable {

    private String freight;
    private String weight;
    private String cartNum;
    private String gdPrice;

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getCartNum() {
        return cartNum;
    }

    public void setCartNum(String cartNum) {
        this.cartNum = cartNum;
    }

    public String getGdPrice() {
        return gdPrice;
    }

    public void setGdPrice(String gdPrice) {
        this.gdPrice = gdPrice;
    }
}
