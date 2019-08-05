package com.ggkjg.dto;

import java.io.Serializable;

public class ChooseAddressDto implements Serializable {
    private long id;
    private String tempName;
    private String firstHeavy;
    private String firstPrice;
    private String addHeavy;
    private String addPrice;
    private String isFree;
    private String createTime;
    private String modifyTime;
    private String enableFlag;
    private String isTake;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTempName() {
        return tempName;
    }

    public void setTempName(String tempName) {
        this.tempName = tempName;
    }

    public String getFirstHeavy() {
        return firstHeavy;
    }

    public void setFirstHeavy(String firstHeavy) {
        this.firstHeavy = firstHeavy;
    }

    public String getFirstPrice() {
        return firstPrice;
    }

    public void setFirstPrice(String firstPrice) {
        this.firstPrice = firstPrice;
    }

    public String getAddHeavy() {
        return addHeavy;
    }

    public void setAddHeavy(String addHeavy) {
        this.addHeavy = addHeavy;
    }

    public String getAddPrice() {
        return addPrice;
    }

    public void setAddPrice(String addPrice) {
        this.addPrice = addPrice;
    }

    public String getIsFree() {
        return isFree;
    }

    public void setIsFree(String isFree) {
        this.isFree = isFree;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
    }

    public String getIsTake() {
        return isTake;
    }

    public void setIsTake(String isTake) {
        this.isTake = isTake;
    }
}
