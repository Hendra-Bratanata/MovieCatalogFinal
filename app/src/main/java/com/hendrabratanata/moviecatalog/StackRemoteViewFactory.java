package com.hendrabratanata.moviecatalog;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hendrabratanata.moviecatalog.POJO.MovieItem;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.bumptech.glide.request.target.Target.SIZE_ORIGINAL;
import static com.hendrabratanata.moviecatalog.Database.DatabaseContrac.CONTEN_URI;

public class StackRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {
    private Cursor cursor;
    private Context context;
    MovieItem movieItem;
    private int AppwidgetId;

    public  StackRemoteViewFactory(Context context, Intent intent){
        this.context = context;
        AppwidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);
    }
    @Override
    public void onCreate() {

//        cursor = context.getContentResolver().query(CONTEN_URI,null,null,null,null);

    }


    @Override
    public void onDataSetChanged() {
        Log.d("TAG","ondatachange di jalankan");
        final long identityToken = Binder.clearCallingIdentity();
         cursor = context.getContentResolver().query(CONTEN_URI,null,null,null,null);
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews view = new RemoteViews(context.getPackageName(), R.layout.honney_buzz);
        movieItem = getItem(position);
            String url = "http://image.tmdb.org/t/p/w185" + movieItem.getPoster();

            Bitmap bitmap = null;

            try {
                bitmap = Glide.with(context)
                        .load(url)
                        .asBitmap()
                        .error(new ColorDrawable(context.getResources().getColor(R.color.colorAccent)))
                        .into(SIZE_ORIGINAL, SIZE_ORIGINAL).get();
            } catch (InterruptedException | ExecutionException e) {
                Log.d("Widget Load Error", "error");
            }

            view.setImageViewBitmap(R.id.imgView, bitmap);
            view.setImageViewBitmap(R.id.stackWidgetItemPicture, bitmap);
            view.setTextViewText(R.id.stackWidgetRelis,movieItem.getRilis());
            view.setTextViewText(R.id.stackWidgetDesc,movieItem.getOverview());
            view.setTextViewText(R.id.stackWidgetJudul,movieItem.getJudul());
 ;

            Bundle extras = new Bundle();
            extras.putInt(FavoriteWidget.EXTRA_ITEM, position);
            Intent fillInIntent = new Intent();
            fillInIntent.putExtras(extras);

            view.setOnClickFillInIntent(R.id.stackWidgetItemPicture, fillInIntent);

        return view;
    }
    private MovieItem getItem(int position) {
        if (!cursor.moveToPosition(position)) {
            Toast.makeText(context, "List Favorite not found", Toast.LENGTH_SHORT).show();
        }
        return new MovieItem(cursor);
    }
    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
