package com.ggkjg.dto;

public class SystemMessageDto {

    private String createDate;
    private String title;
    private int classType;
    private String contentz;
    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getClassType() {
        return classType;
    }

    public void setClassType(int classType) {
        this.classType = classType;
    }


    public String getContentz() {
        return contentz;
    }

    public void setContentz(String contentz) {
        this.contentz = contentz;
    }
}
