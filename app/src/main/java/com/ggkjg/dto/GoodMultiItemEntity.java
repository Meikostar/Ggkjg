package com.ggkjg.dto;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class GoodMultiItemEntity implements MultiItemEntity {
    public int itemType;
    public static  final int GOOD_ORDER_INFO = 1;
    public static final int GOOD_ORDER_ITEM = 2;
    public static final int GOOD_ORDER_COUNT = 3;
    public static final int GOOD_ORDER_PAY = 4;
    String no;
    @Override
    public int getItemType() {
        return itemType;
    }

}
