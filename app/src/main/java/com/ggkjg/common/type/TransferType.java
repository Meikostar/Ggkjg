package com.ggkjg.common.type;

/**
 * <p>
 * 广场 1-出让 2-收购
 * Created by dahai on 2018/12/06.
 */
public enum TransferType {
    push("出让", 1), pull("收购", 2);

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

    private TransferType(String value, int type) {
        this.value = value;
        this.type = type;
    }
}
