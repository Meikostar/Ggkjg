package com.ggkjg.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by lihaoqi on 2019/1/29.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {

    private String[] mTitles;

    private ArrayList<Fragment> mFragments;

    public MyPagerAdapter(FragmentManager fm){
        super(fm);
    }


    public void setTitles(String[] titles) {
        this.mTitles = titles;
    }


    public void setFragments(ArrayList<Fragment> fragments) {
        this.mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
