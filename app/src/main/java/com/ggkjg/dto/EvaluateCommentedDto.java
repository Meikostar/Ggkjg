package com.ggkjg.dto;

import java.io.Serializable;

/**
 * 评价商品
 */
public class EvaluateCommentedDto implements Serializable {
    String cover_image;
    String title;
    String price;

    public String getCover_image() {
        return cover_image;
    }

    public void setCover_image(String cover_image) {
        this.cover_image = cover_image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
