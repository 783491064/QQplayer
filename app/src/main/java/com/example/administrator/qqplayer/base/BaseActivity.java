package com.example.administrator.qqplayer.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/11.
 */
public  class BaseActivity extends AppCompatActivity implements  Uioperation {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view=View.inflate(this, getLayout(),null);
        setContentView(view);
        ButterKnife.bind(this);


        //用黄油刀



        setListener();


        loadData();

    }


    @Override
    public void onClick(View v) {

        
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
}
