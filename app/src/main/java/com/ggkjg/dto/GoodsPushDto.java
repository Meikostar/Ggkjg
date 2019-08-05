package com.ggkjg.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 商品推荐
 * Created by Dahai on 2018/12/18.
 */

public class GoodsPushDto implements Serializable {
    private long total;
    private List<GoodsPushRowsDto> rows;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<GoodsPushRowsDto> getRows() {
        return rows;
    }

    public void setRows(List<GoodsPushRowsDto> rows) {
        this.rows = rows;
    }
}
