package com.ggkjg.dto;

import java.util.List;

public class RecommendDto {
    String headImg;
    String nickName;
    String memberLevel;
    String salesTotal;
    String province;
    String city;
    String area;
    String id;
    String pushNum;
    public long total;
    public List<RecommendDto> rows;
    public boolean isChoose;

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(String memberLevel) {
        this.memberLevel = memberLevel;
    }

    public String getSalesTotal() {
        return salesTotal;
    }

    public void setSalesTotal(String salesTotal) {
        this.salesTotal = salesTotal;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPushNum() {
        return pushNum;
    }

    public void setPushNum(String pushNum) {
        this.pushNum = pushNum;
    }
}
