package com.example.administrator.qqplayer.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Video.Media;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.administrator.qqplayer.R;
import com.example.administrator.qqplayer.activity.MainActivity;
import com.example.administrator.qqplayer.activity.VideoPlayerActivity;
import com.example.administrator.qqplayer.adapter.VideoAdapter;
import com.example.administrator.qqplayer.base.BaseFragment;
import com.example.administrator.qqplayer.bean.VideoItem;
import com.example.administrator.qqplayer.handler.MediaQueyHandler;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/13.
 */
public class ListFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private static final String TAG = "ListFragment";
    private ListView listView;
    private VideoAdapter videoAdapter;

    /**
     * 静态方法创建实例对象；
     */
    public static ListFragment newInstance(Bundle args) {
        ListFragment listFragment = new ListFragment();
        listFragment.setArguments(args);
        return listFragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_list;
    }

    @Override
    public void loadData() {



        Bundle arguments = getArguments();
        int key = arguments.getInt("key", 0);
        loadMediaData(key);
    }

    @Override
    public void setListener() {
        listView = (ListView) getView();
        listView.setOnItemClickListener(this);

    }

    private void loadMediaData(int key) {
        Uri uri = null;
        String[] projection = null;//要查新的列；
        String selection = null;
        String[] selectionArgs = null;
        String orderBy = Media.TITLE + " ASC";

        if (key == MainActivity.MEDIA_VIDEO) {
            videoAdapter = new VideoAdapter(getActivity(), null);
            listView.setAdapter(videoAdapter);

            uri = Media.EXTERNAL_CONTENT_URI;
            projection = new String[]{Media._ID, Media.TITLE, Media.DURATION, Media.SIZE, Media.DATA};
//
            //异步查询数据库的处理器；
            MediaQueyHandler mediaQueyHandler = new MediaQueyHandler(getActivity().getContentResolver());
            mediaQueyHandler.startQuery(0, videoAdapter, uri, projection, selection, selectionArgs, orderBy);
        } else {
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(getActivity(),VideoPlayerActivity.class);
        intent.putExtra("currentItem",position);
        Cursor cursor= (Cursor) videoAdapter.getItem(position);
        intent.putExtra("videoList",cursorToList(cursor));
        startActivity(intent);
    }

    private ArrayList<VideoItem> cursorToList(Cursor cursor) {
        ArrayList<VideoItem> list=new ArrayList<>();
        //先让cursor移动到最上方；就可以获取全部的视频个数了；
        cursor.moveToFirst();

        do{
            list.add(VideoItem.fromCursor(cursor));
        }while(cursor.moveToNext());
        return list;
    }


}
