package com.example.administrator.qqplayer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/13.
 */
public class MainAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> fragments;
    public MainAdapter(ArrayList<Fragment> fragments,FragmentManager fm) {
        super(fm);
        this.fragments=fragments;
    }



    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
