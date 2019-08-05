package com.ggkjg.dto;

import java.util.List;

public class AddreessDataDto {
    List<AddressDto> rows;

    public List<AddressDto> getRows() {
        return rows;
    }

    public void setRows(List<AddressDto> rows) {
        this.rows = rows;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    String total;

}
