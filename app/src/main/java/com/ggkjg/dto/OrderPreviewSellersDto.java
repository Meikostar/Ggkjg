package com.ggkjg.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 预览订单 商铺
 * Created by Dahai on 2018/12/18.
 */

public class OrderPreviewSellersDto implements Serializable {
    private String data;
    private String title;
    private List<ShopCartDto> product;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<ShopCartDto> getProduct() {
        return product;
    }

    public void setProduct(List<ShopCartDto> product) {
        this.product = product;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
