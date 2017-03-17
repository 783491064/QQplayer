package com.example.administrator.qqplayer.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.qqplayer.R;
import com.example.administrator.qqplayer.base.BaseActivity;
import com.example.administrator.qqplayer.bean.VideoItem;
import com.example.administrator.qqplayer.util.Utils;
import com.example.administrator.qqplayer.view.VideoView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/7/16.
 */
public class VideoPlayerActivity extends BaseActivity {

    private static final String TAG = "VideoPlayerActivity";


    @Bind(R.id.video_view)
    VideoView videoView;
    @Bind(R.id.shipinbiaoti)
    TextView shipinbiaoti;
    @Bind(R.id.dianchi)
    ImageView dianchi;
    @Bind(R.id.sytem_time)
    TextView sytemTime;
    @Bind(R.id.voice)
    ImageView voice;
    @Bind(R.id.sb_voice)
    SeekBar sbVoice;
    @Bind(R.id.top)
    LinearLayout top;
    @Bind(R.id.tv_current)
    TextView tvCurrent;
    @Bind(R.id.sb_video)
    SeekBar sbVideo;
    @Bind(R.id.total)
    TextView total;
    @Bind(R.id.btn_exit)
    ImageView btnExit;
    @Bind(R.id.btn_pause)
    ImageView btnPause;
    @Bind(R.id.btn_pre)
    ImageView btnPre;
    @Bind(R.id.btn_screen)
    ImageView btnScreen;
    @Bind(R.id.bottom)
    LinearLayout bottom;
    @Bind(R.id.ll)
    LinearLayout ll;
    @Bind(R.id.pb_loading)
    ProgressBar pb_loading;
    @Bind(R.id.tv_loading)
    LinearLayout tv_loading;
 @Bind(R.id.sb_video)
    SeekBar sb_video;



    //    @Bind(R.id.liangdu)
//    LinearLayout liangdu;
    private int currentItem;
    private BettaryReceiver bettaryReceiver;
    public final int MSG_UPDATE_TIME = 1;
    private static final int MSG_UPDATE_PROGRESS = 2;
    private static final int MSG_HIDE_LAYOUT = 3;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_UPDATE_TIME:
                    updateTime();
                    break;
                case MSG_UPDATE_PROGRESS:
                    updateVideoPress();
                    break;
                case MSG_HIDE_LAYOUT:
                    hideLayout();
                    break;
            }
        }
    };
    private AudioManager audioManager;
    private AudioManager manager;
    private int currentVolume;
    private ArrayList<VideoItem> videoList;
    private int maxVolume;
    private FrameLayout liangdu;
    private ImageView btn_next;

    private void updateTime() {
        sytemTime.setText(DateFormat.format("HH:mm:ss", System.currentTimeMillis()));
        handler.sendEmptyMessageDelayed(MSG_UPDATE_TIME, 1000);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_video_palyer;
    }

    private int width;

    @Override
    public void loadData() {


        liangdu = (FrameLayout) findViewById(R.id.liangdu);
        btn_next = (ImageView) findViewById(R.id.btn_next);

        width = getWindowManager().getDefaultDisplay().getWidth();
        //更新系统的时间；
        updateTime();
        registBettaryChangeResiver();
        initVoice();
        initLayout();

        Uri uri = getIntent().getData();
        if (uri != null) {
            videoView.setVideoURI(uri);
            videoView.setOnPreparedListener(mOnPrepareedListener);
            videoView.setOnCompletionListener(mOnCompletioListener);
            shipinbiaoti.setText(uri.getPath());
            btn_next.setEnabled(false);
            btnPre.setEnabled(false);

        } else {
            currentItem = getIntent().getIntExtra("currentItem", 0);
            videoList = (ArrayList<VideoItem>) getIntent().getSerializableExtra("videoList");
            playVideo();
        }





    }

    private void initLayout() {
        top.measure(0, 0);
        top.setTranslationY(-top.getMeasuredHeight());

        bottom.measure(0, 0);
        bottom.setTranslationY(bottom.getMeasuredHeight());
    }

    private void initVoice() {
        manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        currentVolume = manager.getStreamVolume(AudioManager.STREAM_MUSIC);
        maxVolume = manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        sbVoice.setMax(maxVolume);
        sbVoice.setProgress(currentVolume);
    }


    private void playVideo() {
        //判断当前视频
        checkFirstOrLast();
        VideoItem videoItem = videoList.get(currentItem);
        videoView.setVideoPath(videoItem.path);
        //异步准备
        videoView.setOnPreparedListener(mOnPrepareedListener);
        videoView.setOnCompletionListener(mOnCompletioListener);

        //标题
        shipinbiaoti.setText(videoItem.title);


        //设置系统面板
        //videoView.setMediaController(new MediaController(this));一般不用
    }

    private void checkFirstOrLast() {
        if (currentItem == 0) {
            btnPre.setEnabled(false);
        } else if (currentItem > 0 && currentItem < videoList.size()) {
            btnPre.setEnabled(true);
            btn_next.setEnabled(true);

        } else if (currentItem == (videoList.size() - 1)) {
            btn_next.setEnabled(false);
        }
    }

    MediaPlayer.OnCompletionListener mOnCompletioListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            handler.removeMessages(MSG_UPDATE_PROGRESS);
            btnPause.setBackgroundResource(R.drawable.selector_btn_play);
        }
    };


    @Override
    public void setListener() {
        sbVoice.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    isMute = false;
                    currentVolume = progress;
                    manager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                handler.removeMessages(MSG_HIDE_LAYOUT);

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                handler.sendEmptyMessageDelayed(MSG_HIDE_LAYOUT, 4000);

            }
        });
        sbVideo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && videoView.canSeekForward()) {
                    videoView.seekTo(progress);
                    tvCurrent.setText(Utils.formatDuration(progress));


                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                handler.removeMessages(MSG_UPDATE_PROGRESS);
                handler.removeMessages(MSG_HIDE_LAYOUT);

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                handler.sendEmptyMessage(MSG_UPDATE_PROGRESS);
                handler.sendEmptyMessageDelayed(MSG_HIDE_LAYOUT, 4000);
            }
        });
        //设置缓存的监听器；
        videoView.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                float floatPercent =percent/100f;
                float secondPrcess= floatPercent* videoView.getDuration();
                sb_video.setSecondaryProgress((int)secondPrcess);
            }
        });

        videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                switch (what) {
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                        //显示进度条

                        break;
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                        //开始播放；

                        break;
                }
                return false;
            }
        });

        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                switch (what){
                    case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                        Toast.makeText(VideoPlayerActivity.this,"视频格式不正确",Toast.LENGTH_LONG).show();
                        showErrayDialog();
                        break;
                }
                 return false;
            }
        });

    }

    private void showErrayDialog() {
        AlertDialog alertDialog =new AlertDialog.Builder(this).create();
        alertDialog.setTitle("提示");
        alertDialog.setMessage("视频格式不正确");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "退出界面", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alertDialog.show();
    }


    MediaPlayer.OnPreparedListener mOnPrepareedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            Log.d(TAG, "onPrepared: onprepared");
            videoView.start();
            hideLoadingLayout();
            total.setText(Utils.formatDuration(videoView.getDuration()));
            sbVideo.setMax(videoView.getDuration());
            sbVideo.setProgress(0);
            //设置暂停的按钮
            btnPause.setBackgroundResource(R.drawable.selector_btn_pause);

            updateVideoPress();

        }
    };

    private void hideLoadingLayout() {

        ViewCompat.animate(ll).alpha(0).setDuration(800).setListener(new ViewPropertyAnimatorListener() {
            @Override
            public void onAnimationStart(View view) {
                ll.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(View view) {

            }

            @Override
            public void onAnimationCancel(View view) {

            }
        }).start();
    }

    private void updateVideoPress() {
        sbVideo.setProgress(videoView.getCurrentPosition());
        tvCurrent.setText(Utils.formatDuration(videoView.getCurrentPosition()));
        handler.sendEmptyMessageDelayed(MSG_UPDATE_PROGRESS, 100);

    }

    @OnClick({R.id.voice, R.id.btn_exit, R.id.btn_pause, R.id.btn_pre, R.id.btn_screen})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.voice:
                toggleMute();
                break;
            case R.id.btn_exit:
                finish();
                break;
            case R.id.btn_next:
                playNext();

                break;
            case R.id.btn_pause:
                toogglePlay();
                break;
            case R.id.btn_pre:
                playPre();
                break;
            case R.id.btn_screen:

                videoView.toogleFullScreen();
                btnScreen.setBackgroundResource(videoView.isFullScreen() ? R.drawable.selector_btn_defaultscreen
                        : R.drawable.selector_btn_fullscreen);
                break;
        }
    }

    private void showLayout() {
        isShow = true;
        ViewCompat.animate(top).translationY(0).setDuration(350).start();
        ViewCompat.animate(bottom).translationY(0).setDuration(350).start();


    }

    private void playPre() {
        if (currentItem > 0) {
            currentItem--;
            playVideo();
        } else {
            Toast.makeText(this, "已经是第一个了", Toast.LENGTH_SHORT);
        }

    }

    private void playNext() {
        if (currentItem < videoList.size()) {
            currentItem++;
            playVideo();
        } else {
            Toast.makeText(this, "已经是最后一个了", Toast.LENGTH_SHORT);
        }


    }

    private void toogglePlay() {
        if (videoView.isPlaying()) {
            videoView.pause();
            btnPause.setBackgroundResource(R.drawable.selector_btn_play);
        } else {
            videoView.start();
            btnPause.setBackgroundResource(R.drawable.selector_btn_pause);
            handler.removeMessages(MSG_UPDATE_PROGRESS);
            handler.sendEmptyMessage(MSG_UPDATE_PROGRESS);
        }
    }

    private boolean isMute = false;

    private void toggleMute() {
        isMute = !isMute;
        if (isMute) {
            manager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
            sbVoice.setProgress(0);
        } else {
            manager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0);
            sbVoice.setProgress(currentVolume);

        }
    }

    private void registBettaryChangeResiver() {
        bettaryReceiver = new BettaryReceiver();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(bettaryReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bettaryReceiver != null) {
            unregisterReceiver(bettaryReceiver);
        }
        //释放handler
        handler.removeCallbacksAndMessages(null);//内存的优化；
        videoView.stopPlayback();//释放资源；
    }

    private void changeBettayrImage(int leve) {

        if (leve == 0) {
            dianchi.setBackgroundResource(R.mipmap.ic_battery_0);
        } else if (leve > 0 && leve <= 10) {
            dianchi.setBackgroundResource(R.mipmap.ic_battery_10);
        } else if (leve > 10 && leve <= 20) {
            dianchi.setBackgroundResource(R.mipmap.ic_battery_20);
        } else if (leve > 20 && leve <= 40) {
            dianchi.setBackgroundResource(R.mipmap.ic_battery_40);
        } else if (leve > 40 && leve <= 60) {
            dianchi.setBackgroundResource(R.mipmap.ic_battery_60);
        } else if (leve > 60 && leve <= 80) {
            dianchi.setBackgroundResource(R.mipmap.ic_battery_80);
        } else if (leve > 80 && leve <= 100) {
            dianchi.setBackgroundResource(R.mipmap.ic_battery_100);
        }
    }

    public class BettaryReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int intExtra = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            changeBettayrImage(intExtra);
        }
    }

    private float downX;
    private float downY;
    private Boolean isShow = false;

    private void togglerContralLayout() {
        if (isShow) {
            hideLayout();
        } else {
            showLayout();
        }
    }

    private void hideLayout() {
        isShow = false;
        ViewCompat.animate(top).translationY(-top.getMeasuredHeight()).setDuration(350).start();
        ViewCompat.animate(bottom).translationY(bottom.getMeasuredHeight()).setDuration(350).start();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                togglerContralLayout();


                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX();
                float moveY = event.getY();
                float deltaX = moveX - downX;
                float deltaY = moveY - downY;
                if (moveX < width / 2) {
                    //亮度的处理
                    changeScreenLight(deltaY);

                } else {
                    //音量的处理
                    changeVoice(deltaY);

                }
                downX = moveX;
                downY = moveY;


                break;
            case MotionEvent.ACTION_UP:
                handler.removeMessages(MSG_HIDE_LAYOUT);
                handler.sendEmptyMessageDelayed(MSG_HIDE_LAYOUT, 5000);
                break;

        }
        return super.onTouchEvent(event);
    }

    private void changeVoice(float deltaY) {
        if (deltaY > 0) {
            currentVolume -= 1;
        } else {
            currentVolume += 1;
        }
        if (currentVolume < 0) currentVolume = 0;
        if (currentVolume > maxVolume) currentVolume = maxVolume;
        sbVoice.setProgress(currentVolume);
        manager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0);
    }

    private void changeScreenLight(float deltaY) {
        float alpha = liangdu.getAlpha();
        alpha += deltaY / 200;
        if (alpha < 0) alpha = 0;
        if (alpha > 1) alpha = 1;
        liangdu.setAlpha(alpha);
        //0--1
    }
}
