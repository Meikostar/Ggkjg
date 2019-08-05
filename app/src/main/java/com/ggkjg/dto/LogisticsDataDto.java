package com.ggkjg.dto;

import java.util.List;

public class LogisticsDataDto {
    List<LogisticsItemDto> contentList;
    LogisticsExpressDto express;

    public List<LogisticsItemDto> getContentList() {
        return contentList;
    }

    public void setContentList(List<LogisticsItemDto> contentList) {
        this.contentList = contentList;
    }

    public LogisticsExpressDto getExpress() {
        return express;
    }

    public void setExpress(LogisticsExpressDto express) {
        this.express = express;
    }
}
