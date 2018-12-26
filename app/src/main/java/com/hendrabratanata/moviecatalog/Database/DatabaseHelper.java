package com.hendrabratanata.moviecatalog.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.hendrabratanata.moviecatalog.Database.DatabaseContrac.FavoriteColumns.BACKDROP;
import static com.hendrabratanata.moviecatalog.Database.DatabaseContrac.FavoriteColumns.BAHASA;
import static com.hendrabratanata.moviecatalog.Database.DatabaseContrac.FavoriteColumns.DESC;
import static com.hendrabratanata.moviecatalog.Database.DatabaseContrac.FavoriteColumns.JUDUL;
import static com.hendrabratanata.moviecatalog.Database.DatabaseContrac.FavoriteColumns.POPULAR;
import static com.hendrabratanata.moviecatalog.Database.DatabaseContrac.FavoriteColumns.POSTER;
import static com.hendrabratanata.moviecatalog.Database.DatabaseContrac.FavoriteColumns.RELIS;
import static com.hendrabratanata.moviecatalog.Database.DatabaseContrac.FavoriteColumns.SCORE;
import static com.hendrabratanata.moviecatalog.Database.DatabaseContrac.TABLE_FAFORITE;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "dbMovie";
    private static final int DATABASE_VERSION = 1;


    private static final String SQL_CREATE_TABLE_FAVORITE = String.format("CREATE TABLE %s"
                    +"(%s INTEGER PRIMARY KEY AUTOINCREMENT,"//_ID
                    +" %s TEXT NOT NULL,"//JUDUL
                    +" %s TEXT NOT NULL,"//DESC
                    +" %s TEXT NOT NULL,"//RELIS
                    +" %s TEXT NOT NULL,"//POSTER
                    +" %s TEXT NOT NULL,"//SCORE
                    +" %s TEXT NOT NULL,"//BAHASA
                    +" %s TEXT NOT NULL,"//BACKDROP
                    +" %s TEXT NOT NULL)"//POPULAR
            ,TABLE_FAFORITE,_ID,JUDUL,DESC,RELIS,POSTER,SCORE,BAHASA,BACKDROP,POPULAR );


    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FAVORITE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAFORITE);
        onCreate(db);
    }
}
