package com.ggkjg.dto;

import java.util.List;

public class SystemMessageDataDto {
  int total;
  List<SystemMessageDto> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<SystemMessageDto> getRows() {
        return rows;
    }

    public void setRows(List<SystemMessageDto> rows) {
        this.rows = rows;
    }
}
