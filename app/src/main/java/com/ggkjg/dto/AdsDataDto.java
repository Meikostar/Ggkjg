package com.ggkjg.dto;

import java.util.List;

public class AdsDataDto {


    /**
     * positionName : 个人中心广告位
     * positionType : 2
     * positionCode : center
     * id : 7
     */

    private String positionName;
    private String positionType;
    private String positionCode;
    private long id;
    List<AdsDto> adsList;

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getPositionType() {
        return positionType;
    }

    public void setPositionType(String positionType) {
        this.positionType = positionType;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<AdsDto> getAdsList() {
        return adsList;
    }

    public void setAdsList(List<AdsDto> adsList) {
        this.adsList = adsList;
    }
}
