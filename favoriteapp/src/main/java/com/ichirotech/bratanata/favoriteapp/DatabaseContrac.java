package com.ichirotech.bratanata.favoriteapp;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContrac {
    public static String TABLE_FAFORITE = "favorite";

    public static final class FavoriteColumns implements BaseColumns {
        public static String SCORE      = "score";
        public static String POPULAR    = "Popular";
        public static String BAHASA     = "bahasa";
        public static String POSTER     = "poster";
        public static String JUDUL      = "judul";
        public static String DESC       = "Desc";
        public static String RELIS      = "relis";
        public static String BACKDROP   = "backdrop";
    }

        public static String AUTH = "com.hendrabratanata.moviecatalog";
        public static Uri CONTEN_URI = new Uri.Builder().scheme("content")
                .authority(AUTH)
                .appendPath(TABLE_FAFORITE)
                .build();

        public static String getColumnsString(Cursor curso, String ColumnsName){
            return curso.getString(curso.getColumnIndex(ColumnsName));

        }
        public static int getColumnsInt (Cursor cursor,String ColumnsName){
            return cursor.getInt(cursor.getColumnIndex(ColumnsName));
        }
        public static long getColumnsLong(Cursor cursor,String ColumnsName){
            return cursor.getLong(cursor.getColumnIndex(ColumnsName));
        }


    }

