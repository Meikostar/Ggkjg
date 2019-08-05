package com.ggkjg.dto;

import java.io.Serializable;

/**
 * Created by xld021 on 2018/4/12.
 */

public class LoginDto implements Serializable {
    private String JSESSIONID;//servlet会话token凭证
    private String JSESSIONID_SHIRO;//shiro会话token凭证
    private String id;//会员编号
    private String referralCode;//推荐码
    private String headImg;//会员头像
    private String nickName;//会员昵称
    private String mobileNo;//手机号码
    private String memberLevel;//会员等级 1-普通会员 2-店主 3-创客 4-城市运营中心
    private String authState;//实名制认证状态0-未上传 1-未审核 2-审核通过 3-审核未通过
    private String lastLoginTime;//最后登录时间
    private String sysPath;//系统路径拼接图片地址

    public String getJSESSIONID() {
        return JSESSIONID;
    }

    public void setJSESSIONID(String JSESSIONID) {
        this.JSESSIONID = JSESSIONID;
    }

    public String getJSESSIONID_SHIRO() {
        return JSESSIONID_SHIRO;
    }

    public void setJSESSIONID_SHIRO(String JSESSIONID_SHIRO) {
        this.JSESSIONID_SHIRO = JSESSIONID_SHIRO;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

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

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(String memberLevel) {
        this.memberLevel = memberLevel;
    }

    public String getAuthState() {
        return authState;
    }

    public void setAuthState(String authState) {
        this.authState = authState;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getSysPath() {
        return sysPath;
    }

    public void setSysPath(String sysPath) {
        this.sysPath = sysPath;
    }
}
