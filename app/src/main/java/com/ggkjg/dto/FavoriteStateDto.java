package com.ggkjg.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 获取商品收藏状态
 */
public class FavoriteStateDto implements Serializable {
    private String isFavorite;

    public String getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(String isFavorite) {
        this.isFavorite = isFavorite;
    }
}
