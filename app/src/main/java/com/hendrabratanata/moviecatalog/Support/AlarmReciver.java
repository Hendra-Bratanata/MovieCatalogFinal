package com.hendrabratanata.moviecatalog.Support;

import android.app.AlarmManager;
import android.app.LoaderManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.hendrabratanata.moviecatalog.Activity.Navigation;
import com.hendrabratanata.moviecatalog.Activity.NotifSetting;
import com.hendrabratanata.moviecatalog.POJO.MovieItem;
import com.hendrabratanata.moviecatalog.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AlarmReciver extends BroadcastReceiver{
    public static final String EXTRA_MESSAGE        = "EXTRA message";
    public static final String EXTRA_TYPE           = "Extra Type";
    public static final String DAILY_TITLE          = "Movie Catalog";
    public static final String RELEASE_TITLE        = "Release Title";
    private final int DAILY_ID= 10;
    private final int RELEASE_ID = 20;
    public static final String TYPE_DAILY       = "DAILY";
    public static final String TYPE_RELEASE       = "RELEASE";

    public  AlarmReciver(){

    }




    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("tag", "onReceive: " + intent.getAction());

        if (intent.getAction().equals(TYPE_DAILY)) {

            Log.d("tag", "dayly active");
            String message = intent.getStringExtra(EXTRA_MESSAGE);
            String type = intent.getStringExtra(EXTRA_TYPE);
            String title = type.equalsIgnoreCase(TYPE_DAILY) ? DAILY_TITLE : RELEASE_TITLE;
            int notifId = type.equalsIgnoreCase(TYPE_DAILY) ? DAILY_ID : RELEASE_ID;

            showAlarmNotification(context, title, message, notifId);

        } else if (intent.getAction().equals(TYPE_RELEASE)) {
        Intent intent1 = new Intent(context,MyIntentService.class);
        context.startService(intent1);

            Log.d("tag", "jalan release");

        }
    }
    public void showAlarmNotification(Context context, String title, String message,int notifId) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.icon_movie)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.icon_icon))
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context,android.R.color.transparent))
                .setVibrate(new long[]{1000,1000,1000,1000,1000})
                .setSound(alarmSound);
        notificationManager.notify(notifId,builder.build());
    }

    public void setRepeatingAlarmDaily(Context context, String time, String type, String message){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context,AlarmReciver.class);
        intent.putExtra(EXTRA_MESSAGE,message);
        intent.putExtra(EXTRA_TYPE,type);
        intent.setAction(TYPE_DAILY);
        int requestID=DAILY_ID;

        String timeArray[] = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE,Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND,0);

// pending item mengirim broadcast dengan data inten dan id
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,requestID,intent,0);
//      dijalanakan pada saat waktu telah sampai
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
        Toast.makeText(context,"Daily notifkasi set Up",Toast.LENGTH_SHORT).show();
        Log.d("tag","timeMilis: "+calendar.getTimeInMillis());

    }
    public void setRepeatingRelease(Context context, String time, String type, String message){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context,AlarmReciver.class);
        intent.putExtra(EXTRA_MESSAGE,message);
        intent.putExtra(EXTRA_TYPE,type);
        intent.setAction(TYPE_RELEASE);

        int requestID=RELEASE_ID;
        String timeArray[] = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE,Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND,0);

// pending item mengirim broadcast dengan data inten dan id
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,requestID,intent,0);
//      dijalanakan pada saat waktu telah sampai
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
        Toast.makeText(context,"Release notifkasi set Up",Toast.LENGTH_SHORT).show();
        Log.d("tag","timeMilis: "+calendar.getTimeInMillis());

    }
}
