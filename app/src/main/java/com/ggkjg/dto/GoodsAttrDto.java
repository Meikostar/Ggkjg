package com.ggkjg.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 商品颜色，规格等属性
 * Created by rzb on 2019/3/13
 */

public class GoodsAttrDto implements Serializable {
    private String key;
    private List<GoodsAttrItemDto> attrList;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<GoodsAttrItemDto> getAttrList() {
        return attrList;
    }

    public void setAttrList(List<GoodsAttrItemDto> attrList) {
        this.attrList = attrList;
    }
}
