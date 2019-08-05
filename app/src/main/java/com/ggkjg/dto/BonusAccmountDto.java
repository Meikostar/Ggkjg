package com.ggkjg.dto;

public class BonusAccmountDto {

    private String id;
    private String memberId;
    private String bonusAmount;
    private String bonusSalesAmount;
    private String bonusDirectAmount;
    private String bonusManageAmount;
    private String createTime;
    private String modifyTime;
    private String enableFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getBonusAmount() {
        return bonusAmount;
    }

    public void setBonusAmount(String bonusAmount) {
        this.bonusAmount = bonusAmount;
    }

    public String getBonusSalesAmount() {
        return bonusSalesAmount;
    }

    public void setBonusSalesAmount(String bonusSalesAmount) {
        this.bonusSalesAmount = bonusSalesAmount;
    }

    public String getBonusDirectAmount() {
        return bonusDirectAmount;
    }

    public void setBonusDirectAmount(String bonusDirectAmount) {
        this.bonusDirectAmount = bonusDirectAmount;
    }

    public String getBonusManageAmount() {
        return bonusManageAmount;
    }

    public void setBonusManageAmount(String bonusManageAmount) {
        this.bonusManageAmount = bonusManageAmount;
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
}
