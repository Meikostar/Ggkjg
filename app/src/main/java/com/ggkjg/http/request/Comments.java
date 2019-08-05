package com.ggkjg.http.request;

/**
 * 某个对象的评价列表
 * * Created by dahai on 2019/01/07.
 */
public class Comments extends BaseRequestModel {

    private String areaNo;
    private String mobileNo;
    private String pictureCode;

    public String getAreaNo() {
        return areaNo;
    }

    public void setAreaNo(String areaNo) {
        this.areaNo = areaNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPictureCode() {
        return pictureCode;
    }

    public void setPictureCode(String pictureCode) {
        this.pictureCode = pictureCode;
    }
}
