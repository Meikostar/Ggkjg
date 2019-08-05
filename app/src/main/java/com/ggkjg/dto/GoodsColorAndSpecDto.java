package com.ggkjg.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 商品颜色，规格等属性
 * Created by rzb on 2019/3/13.
 */

public class GoodsColorAndSpecDto implements Serializable {
    private List<GoodsAttrDto> attrMap;

    public List<GoodsAttrDto> getAttrMap() {
        return attrMap;
    }

    public void setAttrMap(List<GoodsAttrDto> attrMap) {
        this.attrMap = attrMap;
    }
}
