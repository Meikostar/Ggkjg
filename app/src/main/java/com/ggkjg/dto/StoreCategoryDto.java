package com.ggkjg.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 返回商城分类(全部)
 */
public class StoreCategoryDto implements Serializable {
    private long id;
    private String categoryName;
    private String imgUrl;
    private String adUrl;
    private String adClickUrl;
    private long parentId;
    private String sort;
    private List<StoreCategoryDto> cateList;

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

    public String getAdUrl() {
        return adUrl;
    }

    public void setAdUrl(String adUrl) {
        this.adUrl = adUrl;
    }

    public String getAdClickUrl() {
        return adClickUrl;
    }

    public void setAdClickUrl(String adClickUrl) {
        this.adClickUrl = adClickUrl;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public List<StoreCategoryDto> getCateList() {
        return cateList;
    }

    public void setCateList(List<StoreCategoryDto> cateList) {
        this.cateList = cateList;
    }
}
