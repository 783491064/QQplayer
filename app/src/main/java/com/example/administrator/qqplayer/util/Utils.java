package com.example.administrator.qqplayer.util;

import android.database.Cursor;
import android.util.Log;

/**
 * Created by Administrator on 2016/7/13.
 */
public class Utils {

    private static final String TAG = "Utils";

    public static void printCursor(Cursor cursor) {
        if (cursor != null) {
            Log.i(TAG, "共" + cursor.getCount() + "条记录");
            while (cursor.moveToNext()) {
                Log.i(TAG, "============================================");
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    String columenName = cursor.getColumnName(i);//列明
                    String string = cursor.getString(i);//列值；
                    Log.i(TAG, columenName + " = " + string);

                }

            }


        }

    }

    /**
     * 格式化时常；01:20:30
     *
     * @param durition
     * @return
     */
    public static String formatDuration(long durition) {
        long HOUR = 60 * 60 * 1000;
        long MINUTE = 60 * 1000;
        long SECOND = 1000;
        //XIAOSHI
        int hour = (int) (durition / HOUR);
        long remain = durition % HOUR;
        int minute = (int) (remain / MINUTE);
        remain = remain % MINUTE;
        int second = (int) (remain / SECOND);
        if(hour==0){
            return String.format("%02d:%02d",minute,second);
        }else{
            return String.format("%02d:%02d:%02d",hour,minute,second);
        }


    }
}
