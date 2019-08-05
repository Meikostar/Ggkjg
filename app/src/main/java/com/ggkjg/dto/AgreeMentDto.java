package com.ggkjg.dto;

import java.io.Serializable;

/**
 * 获取协议
 */
public class AgreeMentDto implements Serializable {
    private String title;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
