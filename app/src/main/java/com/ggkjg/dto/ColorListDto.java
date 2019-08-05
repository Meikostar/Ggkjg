package com.ggkjg.dto;

public class ColorListDto {
//"id": 1,
//        "colorName": "红色",
//        "colorCode": "red",
//        "createTime": "2019-01-26 11:12:03",
//        "modifyTime": "2019-01-26 11:12:05",
//        "enableFlag": "1"

    private long id;
    private String colorName;
    private String colorCode;
    private String createTime;
    private String modifyTime;
    private String enableFlag;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
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
