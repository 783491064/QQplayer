package com.example.administrator.qqplayer.base;

import android.view.View;

/**
 * Created by Administrator on 2016/7/12.
 */
public interface Uioperation  extends View.OnClickListener{
     abstract void loadData();


     abstract void setListener();


     abstract int getLayout();
}
