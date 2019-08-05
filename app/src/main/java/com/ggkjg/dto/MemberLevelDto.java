package com.ggkjg.dto;

import java.io.Serializable;

/**
 * 获取会员等级
 * Created by Dahai on 2018/12/18.
 */

public class MemberLevelDto implements Serializable {
    private int memberLevel;
    private String levelName;

    public int getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(int memberLevel) {
        this.memberLevel = memberLevel;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }
}
