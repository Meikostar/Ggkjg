package com.ggkjg.dto;

import java.io.Serializable;

/**
 * Created by Dahai on 2018/12/18.
 */

public class DataDto<T> implements Serializable {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
