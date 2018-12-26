package com.hendrabratanata.moviecatalog;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.hendrabratanata.moviecatalog.Activity.DetaiMovie;

/**
 * Implementation of App Widget functionality.
 */
public class FavoriteWidget extends AppWidgetProvider {

    public static final String TOAST_ACTION = "toast Action";
    public static final String EXTRA_ITEM = "extra Item";


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        Intent intent = new Intent(context,StackWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.favorite_widget);
        views.setRemoteAdapter(R.id.Stack_view,intent);
        views.setEmptyView(R.id.Stack_view,R.id.empetyView);

        Intent toastInten = new Intent(context,FavoriteWidget.class);
        toastInten.setAction(FavoriteWidget.TOAST_ACTION);
        toastInten.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        PendingIntent toastPending = PendingIntent.getBroadcast(context,0,toastInten,PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.Stack_view,toastPending);

        appWidgetManager.updateAppWidget(appWidgetId,views);

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String Data = intent.getAction();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        assert Data != null;
        int[] appWidgetIds = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context,FavoriteWidget.class));
        if(Data.equals(TOAST_ACTION)){
            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);
            int viewsIndex = intent.getIntExtra(EXTRA_ITEM,0);
            Toast.makeText(context,"Touched item"+viewsIndex,Toast.LENGTH_SHORT).show();
        }else if(Data.equals(DetaiMovie.UPDATE_WIDGET)){

            int[] widgetId = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context,FavoriteWidget.class));
            for(int id : widgetId){
                Log.d("Tag","id"+id);
                AppWidgetManager.getInstance(context).notifyAppWidgetViewDataChanged(id,R.id.Stack_view);
            }
            Toast.makeText(context,"Sukses",Toast.LENGTH_SHORT).show();
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

