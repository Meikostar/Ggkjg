package com.ggkjg.common.type;

public enum MemberLevelType {
    level_1("普通会员", 1),
    level_2("店主", 2),
    level_3("创客", 3),
    level_4("城市运营中心", 4);
    private int type;
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private MemberLevelType(String value, int type) {
        this.value = value;
        this.type = type;
    }
}
