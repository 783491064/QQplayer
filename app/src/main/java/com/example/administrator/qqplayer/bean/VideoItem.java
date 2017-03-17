package com.example.administrator.qqplayer.bean;

import android.database.Cursor;
import android.provider.MediaStore;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/7/14.
 */
public class VideoItem implements Serializable {
    public String title;
    public long durition;
    public long size;
    public int id;
    public String path;
    public static VideoItem fromCursor(Cursor cursor){
        VideoItem videoItem=new VideoItem();
        videoItem.durition=cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
        videoItem.size=cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.SIZE));
        videoItem.id=cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media._ID));
        videoItem.title=cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE));
        videoItem.path=cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
        return videoItem;
    }

}
