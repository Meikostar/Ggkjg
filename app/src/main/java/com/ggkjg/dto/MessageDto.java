package com.ggkjg.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 返回资讯列表子项
 * Created by Dahai on 2018/12/18.
 */

public class MessageDto implements Serializable {
    private long id;
    private String createDate;
    private String createBy;
    private String modifyDate;
    private String modifyBy;
    private String commitUser;
    private String cmsMainImg;
    private String cmsTitle;
    private int statuz;
    private String readedCount;
    private String cmsContentz;
    private String simpleDesc;
    private String cmsMainImgFull;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }

    public String getCommitUser() {
        return commitUser;
    }

    public void setCommitUser(String commitUser) {
        this.commitUser = commitUser;
    }

    public String getCmsMainImg() {
        return cmsMainImg;
    }

    public void setCmsMainImg(String cmsMainImg) {
        this.cmsMainImg = cmsMainImg;
    }

    public String getCmsTitle() {
        return cmsTitle;
    }

    public void setCmsTitle(String cmsTitle) {
        this.cmsTitle = cmsTitle;
    }

    public int getStatuz() {
        return statuz;
    }

    public void setStatuz(int statuz) {
        this.statuz = statuz;
    }

    public String getReadedCount() {
        return readedCount;
    }

    public void setReadedCount(String readedCount) {
        this.readedCount = readedCount;
    }

    public String getCmsContentz() {
        return cmsContentz;
    }

    public void setCmsContentz(String cmsContentz) {
        this.cmsContentz = cmsContentz;
    }

    public String getSimpleDesc() {
        return simpleDesc;
    }

    public void setSimpleDesc(String simpleDesc) {
        this.simpleDesc = simpleDesc;
    }

    public String getCmsMainImgFull() {
        return cmsMainImgFull;
    }

    public void setCmsMainImgFull(String cmsMainImgFull) {
        this.cmsMainImgFull = cmsMainImgFull;
    }
}
