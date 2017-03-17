package com.example.administrator.qqplayer.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.qqplayer.R;
import com.example.administrator.qqplayer.adapter.MainAdapter;
import com.example.administrator.qqplayer.base.BaseActivity;
import com.example.administrator.qqplayer.fragment.ListFragment;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    public static final int MEDIA_VIDEO = 1;
    public static final int MEDIA_MUSIC = 2;
    @Bind(R.id.video)
    TextView video;
    @Bind(R.id.music)
    TextView music;
    @Bind(R.id.view)
    View view;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    private MainAdapter mainAdapter;
    private ArrayList<Fragment> fragments;
    private ArrayList<Fragment> fragments1;

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }


    @Override
    public void loadData() {
        fragments1 = new ArrayList<>();
        fragments1.add(ListFragment.newInstance(getBundle(MEDIA_VIDEO)));
        fragments1.add(ListFragment.newInstance(getBundle(MEDIA_MUSIC)));

        mainAdapter = new MainAdapter(fragments1, getSupportFragmentManager());
        viewPager.setAdapter(mainAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                float targetX=view.getWidth()*position+view.getWidth()*positionOffset;
                ViewCompat.setTranslationX(view,targetX);
            }

            @Override
            public void onPageSelected(int position) {
                changeTitle(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //手动改变标题状态；
        changeTitle(0);
        initIndicatorLine();

    }

    /**
     * 初始化线
     */
    private void initIndicatorLine() {
        int width = getWindowManager().getDefaultDisplay().getWidth();
        int lineWidth = width /fragments1.size();
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width=lineWidth;
        view.requestLayout();
    }

    /**
     * 改变标题
     */
    private void changeTitle(int position) {
        video.setSelected(position == 0);
        music.setSelected(position == 1);

        ViewCompat.animate(video)
                .scaleX(position == 0 ? 1f : 0.7f)
                .scaleY(position == 0 ? 1f : 0.7f)
                .setDuration(350)
                .start();
        ViewCompat.animate(music)
                .scaleX(position == 1 ? 1f : 0.7f)
                .scaleY(position == 1 ? 1f : 0.7f)
                .setDuration(350)
                .start();


    }

    private Bundle getBundle(int i) {
        Bundle bundle = new Bundle();
        bundle.putInt("key", i);
        return bundle;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.video, R.id.music})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.video:
                viewPager.setCurrentItem(0);
                break;
            case R.id.music:
                viewPager.setCurrentItem(1);
                break;
        }
    }




}
