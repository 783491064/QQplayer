package com.example.administrator.qqplayer.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.view.KeyEvent;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.qqplayer.R;
import com.example.administrator.qqplayer.base.BaseActivity;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/7/12.
 */
public class SplishActivity extends BaseActivity {

    @Bind(R.id.iv_icon)
    ImageView ivIcon;
    @Bind(R.id.tv)
    TextView tv;

    @Override
    public int getLayout() {
        return R.layout.activity_splish;
    }

    @Override
    public void loadData() {
        //初始化
        //检查版本更新，数据路初始化，创建文件夹目录，第三方初始化
        //unmeng
        //一般不应超过3秒；一般第三方耗时；

        //下边
        int screenHeight=getWindowManager().getDefaultDisplay().getHeight();
        ViewCompat.setTranslationY(ivIcon, screenHeight);
        ViewCompat.setTranslationY(tv, screenHeight);
        //属性动画；
        ViewCompat.animate(ivIcon).translationY(0)
                  .setDuration(1000)
                .setInterpolator(new OvershootInterpolator())
                .setStartDelay(800)
                .start();
        ViewCompat.animate(tv).translationY(0)
                .setDuration(1000)
                .setInterpolator(new OvershootInterpolator())
                .setStartDelay(800)
                .start();

        //延时计入证据界面；
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplishActivity.this,MainActivity.class));
                finish();
            }
        },2500);


    }

   /* @Override
    public void onBackPressed() {

        //super.onBackPressed();
    }*/

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //当按下的建的表示
        if (keyCode==KeyEvent.KEYCODE_BACK){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
