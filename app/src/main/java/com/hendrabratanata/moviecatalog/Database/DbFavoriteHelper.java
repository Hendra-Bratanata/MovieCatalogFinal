package com.hendrabratanata.moviecatalog.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.hendrabratanata.moviecatalog.POJO.MovieItem;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.hendrabratanata.moviecatalog.Database.DatabaseContrac.FavoriteColumns.JUDUL;
import static com.hendrabratanata.moviecatalog.Database.DatabaseContrac.TABLE_FAFORITE;

public class DbFavoriteHelper {
    private static String DATABASE_TABLE = TABLE_FAFORITE;
    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public DbFavoriteHelper(Context context){
        this.context = context;

    }
    public DbFavoriteHelper open() throws SQLException{
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }
    public void close(){
        databaseHelper.close();
    }
    public Cursor queryProvider(){
        return database.query(DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID+ " DESC");
    }
    public Cursor queryProviderById(String id){
        return database.query(DATABASE_TABLE,
                null,
                _ID+" = ?",
                new String[]{id},
                null,
                null,
                null,
                null);
    }
    public Cursor query(String id){
        return database.query(DATABASE_TABLE,
                null,
                JUDUL+" = ?",
                new String[]{id},
                null,
                null,
                null,
                null);
    }
    public long insertProvider(ContentValues values){
        return database.insert(DATABASE_TABLE,null,values);

    }
    public int updateProvider(String id,ContentValues values){
        return database.update(DATABASE_TABLE,values,_ID+" = ?",new String[]{id});

    }
    public int deleteProvider(String id){
        return database.delete(DATABASE_TABLE,_ID+" = ?",new String[]{id});
    }
}
