package com.ggkjg.dto;

public class AdsDto {
    /**
     * id : 13
     * positionId : 7
     * adsName : null
     * imgUrl : /static/uploads/image/center_position_1.jpg
     * clickUrl : null
     * sort : 1
     * createTime : 1548597620000
     * modifyTime : 1548597622000
     * enableFlag : 1
     */

    private long id;
    private int positionId;
    private Object adsName;
    private String imgUrl;
    public String clickType;
    private String clickUrl;
    private int sort;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public Object getAdsName() {
        return adsName;
    }

    public void setAdsName(Object adsName) {
        this.adsName = adsName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getClickUrl() {
        return clickUrl;
    }

    public void setClickUrl(String clickUrl) {
        this.clickUrl = clickUrl;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
