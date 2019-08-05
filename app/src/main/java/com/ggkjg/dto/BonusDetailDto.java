package com.ggkjg.dto;

import java.util.List;

public class BonusDetailDto {
    int total;
    List<BonusDetailItemDto> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<BonusDetailItemDto> getRows() {
        return rows;
    }

    public void setRows(List<BonusDetailItemDto> rows) {
        this.rows = rows;
    }
}
