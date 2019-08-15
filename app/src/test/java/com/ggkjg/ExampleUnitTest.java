package com.ggkjg;

import android.util.Log;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {

        changeTime("20180801");
    }
    public String changeTime(String time){
        String year = time.substring(0, 4);
        String month = time.substring(5, 7);
        String times=year+month;
       return times;
    }
}