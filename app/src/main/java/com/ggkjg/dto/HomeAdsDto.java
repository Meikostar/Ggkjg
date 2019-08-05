package com.ggkjg.dto;

import java.util.List;

/**
 * Created by lihaoqi on 2019/1/28.
 */

public class HomeAdsDto {

    private String positionName;

    private String positionType;

    private int id;

    private List<SlidersDto> adsList;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<SlidersDto> getAdsList() {
        return adsList;
    }

    public void setAdsList(List<SlidersDto> adsList) {
        this.adsList = adsList;
    }
}
