package com.example.administrator.qqplayer.adapter;

import android.content.Context;
import android.database.Cursor;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.administrator.qqplayer.R;
import com.example.administrator.qqplayer.bean.VideoItem;
import com.example.administrator.qqplayer.util.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/14.
 */
public class VideoAdapter extends CursorAdapter {

    public VideoAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = View.inflate(context, R.layout.adapter_video_list, null);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        VideoItem videoItem = VideoItem.fromCursor(cursor);


        viewHolder.bindData(context, videoItem);
    }

    static class ViewHolder {
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.shichang)
        TextView shichang;
        @Bind(R.id.daxiao)
        TextView daxiao;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void bindData(Context context, VideoItem videoItem) {
            title.setText(videoItem.title);
            shichang.setText(Utils.formatDuration(videoItem.durition));
            daxiao.setText(Formatter.formatFileSize(context, videoItem.size));
        }
    }
}
