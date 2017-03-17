package com.example.administrator.qqplayer.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/7/11.
 */
public  class BaseFragment extends Fragment implements Uioperation {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view=inflater.inflate(getLayout(),null);
        return view ;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //黄油刀
        //设置监听器；
        setListener();
        //加载数据；
        loadData();
    }

    @Override
    public void loadData() {

    }

    @Override
    public void setListener() {

    }

    @Override
    public int getLayout() {
        return 0;
    }

    @Override
    public void onClick(View v) {

    }
}
