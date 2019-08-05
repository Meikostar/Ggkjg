package com.ggkjg.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 评价
 */
public class HotSearchDto implements Serializable {
    //         "id": 1,
//         "searchName": "测试商品1",
//         "searchNum": 1001,
//         "createTime": "2019-02-16 10:31:26",
//         "modifyTime": "2019-02-16 10:31:28",
//         "enableFlag": "1"
    private long id;
    private String searchName;
    private long searchNum;
    private String createTime;
    private String modifyTime;
    private String enableFlag;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public long getSearchNum() {
        return searchNum;
    }

    public void setSearchNum(long searchNum) {
        this.searchNum = searchNum;
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
