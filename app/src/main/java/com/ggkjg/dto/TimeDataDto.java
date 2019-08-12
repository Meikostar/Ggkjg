package com.ggkjg.dto;


public class TimeDataDto {



    private String time;
    private long star;
    private long end;
    private int state;
    public String id;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
