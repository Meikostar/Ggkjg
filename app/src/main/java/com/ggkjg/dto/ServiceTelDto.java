package com.ggkjg.dto;

import java.io.Serializable;

/**
 * 获取客服电话
 */
public class ServiceTelDto implements Serializable {
    private String serviceTel;

    public String getServiceTel() {
        return serviceTel;
    }

    public void setServiceTel(String serviceTel) {
        this.serviceTel = serviceTel;
    }
}
