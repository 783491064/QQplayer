package com.example.administrator.qqplayer.handler;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.widget.CursorAdapter;

import com.example.administrator.qqplayer.util.Utils;

/**
 * Created by Administrator on 2016/7/14.
 */
public class MediaQueyHandler extends AsyncQueryHandler {


    public MediaQueyHandler(ContentResolver cr) {
        super(cr);
    }

    @Override
    protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
        Utils.printCursor(cursor);
        if(cookie!=null&&cookie instanceof CursorAdapter){
            CursorAdapter cursorAdapter = (CursorAdapter) cookie;
            cursorAdapter.changeCursor(cursor);

        }
    }
}
