package com.ggkjg.dto;

import java.io.Serializable;

/**
 * 获取精选好货
 */
public class HomeGoodsIndexDto implements Serializable {
//     "icon": "/static/uploads/image/goods_index.png",
//             "id": 9,
//             "citeName": "红酒",
//             "sort": 1,
//             "type": "2",
//             "isActive": "2",
//             "citeId": 1,
//             "gdPrice": 600

    private String icon;
    private long id;
    private String citeName;
    private String sort;
    private String type;
    private String isActive;
    private int citeId;
    private String gdPrice;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCiteName() {
        return citeName;
    }

    public void setCiteName(String citeName) {
        this.citeName = citeName;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public int getCiteId() {
        return citeId;
    }

    public void setCiteId(int citeId) {
        this.citeId = citeId;
    }

    public String getGdPrice() {
        return gdPrice;
    }

    public void setGdPrice(String gdPrice) {
        this.gdPrice = gdPrice;
    }
}
