package com.ggkjg.dto;

public class CategoryListDto {
//"id": 2,
//        "categoryName": "手表",
//        "imgUrl": "/static/uploads/image/classDetailAd.png",
//        "parentId": 1,
//        "sort": 1,
//        "createTime": "2019-01-22 15:55:25",
//        "modityTime": "2019-01-22 15:55:27",
//        "enableFlag": "1"

    private long id;
    private String categoryName;
    private String imgUrl;
    private long parentId;
    private int sort;
    private String createTime;
    private String modityTime;
    private String enableFlag;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModityTime() {
        return modityTime;
    }

    public void setModityTime(String modityTime) {
        this.modityTime = modityTime;
    }

    public String getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
    }
}
