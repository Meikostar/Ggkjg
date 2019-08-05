package com.ggkjg.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 返回广场列表
 */
public class SquareDto implements Serializable {
    private long total;
    private List<SquareRowsDto> rows;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<SquareRowsDto> getRows() {
        return rows;
    }

    public void setRows(List<SquareRowsDto> rows) {
        this.rows = rows;
    }
}
