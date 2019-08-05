package com.ggkjg.dto;

import java.io.Serializable;

/**
 * 获取首页活动商品
 */
public class HomeActiveIndexDto implements Serializable {
//        "id": 15,
//        "citeId": 2,
//        "citeName": "活动1",
//        "icon": "/static/uploads/image/active_index_1.png",
//        "type": "2",
//        "sort": 1,
//        "isActive": "1",
//        "createTime": "2019-02-11 12:22:58",
//        "modifyTime": "2019-02-11 12:23:00",
//        "enableFlag": "1"

    private long id;
    private int citeId;
    private String citeName;
    private String icon;
    private String type;
    private String sort;
    private String isActive;
    private String createTime;
    private String modifyTime;
    private String enableFlag;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCiteId() {
        return citeId;
    }

    public void setCiteId(int citeId) {
        this.citeId = citeId;
    }

    public String getCiteName() {
        return citeName;
    }

    public void setCiteName(String citeName) {
        this.citeName = citeName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
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
}
