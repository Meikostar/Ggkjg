package com.ggkjg.dto;

import java.util.List;

public class MoneyFundFlowDto {
    long total;
    List<MoneyRecordItemDto> rows;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<MoneyRecordItemDto> getRows() {
        return rows;
    }

    public void setRows(List<MoneyRecordItemDto> rows) {
        this.rows = rows;
    }
}
