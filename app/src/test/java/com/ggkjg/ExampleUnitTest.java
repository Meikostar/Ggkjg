package com.ggkjg;

import android.util.Log;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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

        List<List<Integer>> datas=new ArrayList<>();
       for(int a=0;a<10;a++){
           List<Integer> data=new ArrayList<>();
           for(int i=0;i<20;i++){
               data.add(i);
           }
           datas.add(data);
       }

       String [] a={"a","b"};
        String s = datas.toString();
        Log.e("loglog",s);
    }
    public String changeTime(String time){
        String year = time.substring(0, 4);
        String month = time.substring(5, 7);
        String times=year+month;
       return times;
    }
}