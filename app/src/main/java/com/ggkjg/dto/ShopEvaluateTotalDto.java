package com.ggkjg.dto;

import java.io.Serializable;
import java.util.List;

public class ShopEvaluateTotalDto implements Serializable {
    private long total;
    private List<ShopEvaluateRowsDto> rows;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<ShopEvaluateRowsDto> getRows() {
        return rows;
    }

    public void setRows(List<ShopEvaluateRowsDto> rows) {
        this.rows = rows;
    }
}
