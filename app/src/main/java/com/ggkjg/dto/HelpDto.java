package com.ggkjg.dto;

import java.util.List;

public class HelpDto {
     String total;
     List<HelpItemDto> rows;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<HelpItemDto> getRows() {
        return rows;
    }

    public void setRows(List<HelpItemDto> rows) {
        this.rows = rows;
    }
}
