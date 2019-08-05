package com.ggkjg.dto;

import java.io.Serializable;

/**
 * Created by Dahai on 2018/12/18.
 * 获取账户余额
 */

public class AccountBalanceDto implements Serializable {
    private long id;
    private long memberId;
    private int fundType;
    private String fundName;
    private String fundAmount;
    private String availAmount;
    private String createTime;
    private String modifyTime;
    private int enableFlag;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public int getFundType() {
        return fundType;
    }

    public void setFundType(int fundType) {
        this.fundType = fundType;
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public String getFundAmount() {
        return fundAmount;
    }

    public void setFundAmount(String fundAmount) {
        this.fundAmount = fundAmount;
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

    public int getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(int enableFlag) {
        this.enableFlag = enableFlag;
    }

    public String getAvailAmount() {
        return availAmount;
    }

    public void setAvailAmount(String availAmount) {
        this.availAmount = availAmount;
    }
}
