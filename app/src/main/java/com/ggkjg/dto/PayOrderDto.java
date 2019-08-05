package com.ggkjg.dto;

public class PayOrderDto {


    /**
     * id : 1
     * paymentCode : gd
     * paymentIcon : /static/uploads/image/payment1.png
     * paymentName :
     * paymentDesc : null
     * sort : 1
     * createTime : 1548673308000
     * modifyTime : 1548673310000
     * enableFlag : 1
     */

    private long id;
    private String paymentCode;
    private String paymentIcon;
    private String paymentName;
    private String paymentDesc;
    private String enableFlag;
    private String money;
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public String getPaymentIcon() {
        return paymentIcon;
    }

    public void setPaymentIcon(String paymentIcon) {
        this.paymentIcon = paymentIcon;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public String getPaymentDesc() {
        return paymentDesc;
    }

    public void setPaymentDesc(String paymentDesc) {
        this.paymentDesc = paymentDesc;
    }

    public String getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
