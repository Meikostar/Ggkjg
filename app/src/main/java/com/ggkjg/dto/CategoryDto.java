package com.ggkjg.dto;

import java.util.List;

public class CategoryDto {

    private List<CategoryListDto> categoryList;
    private List<ColorListDto> colorList;

    public List<CategoryListDto> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<CategoryListDto> categoryList) {
        this.categoryList = categoryList;
    }

    public List<ColorListDto> getColorList() {
        return colorList;
    }

    public void setColorList(List<ColorListDto> colorList) {
        this.colorList = colorList;
    }
}
