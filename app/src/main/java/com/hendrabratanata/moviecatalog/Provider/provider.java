package com.hendrabratanata.moviecatalog.Provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hendrabratanata.moviecatalog.Database.DatabaseContrac;
import com.hendrabratanata.moviecatalog.Database.DbFavoriteHelper;

import static com.hendrabratanata.moviecatalog.Database.DatabaseContrac.AUTH;
import static com.hendrabratanata.moviecatalog.Database.DatabaseContrac.CONTEN_URI;
import static com.hendrabratanata.moviecatalog.Database.DatabaseContrac.TABLE_FAFORITE;

public class provider extends ContentProvider {

    private static final int ID_1 = 1;
    private static final int ID_2 = 2;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        // content://com.hendrabratanata.moviecatalog/favorite
        sUriMatcher.addURI(AUTH, TABLE_FAFORITE, ID_1);

        // content://com.hendrabratanata.moviecatalog/favorite/id
        sUriMatcher.addURI(AUTH,TABLE_FAFORITE+ "/#", ID_2);
    }
    private DbFavoriteHelper helper;




    @Override
    public boolean onCreate() {
        helper = new DbFavoriteHelper(getContext());
        helper.open();
        return true;

}

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
      Cursor cursor;
      Log.d("uri provider",""+uri);
        switch(sUriMatcher.match(uri)){
            case ID_1:
                cursor = helper.queryProvider();
                break;
            case ID_2:
                cursor = helper.queryProviderById(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        if (cursor != null){
            cursor.setNotificationUri(getContext().getContentResolver(),uri);
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long added ;
        switch (sUriMatcher.match(uri)){
            case ID_1:
                added = helper.insertProvider(values);
                break;
            default:
                added = 0;
                break;
        }

        if (added > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(CONTEN_URI+ "/" + added);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case ID_2:
                deleted =  helper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }

        if (deleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int updated ;
        switch (sUriMatcher.match(uri)) {
            case ID_2:
                updated =  helper.updateProvider(uri.getLastPathSegment(),values);
                break;
            default:
                updated = 0;
                break;
        }

        if (updated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updated;
    }
}
