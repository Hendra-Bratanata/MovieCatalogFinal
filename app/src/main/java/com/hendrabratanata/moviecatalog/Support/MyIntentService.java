package com.hendrabratanata.moviecatalog.Support;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.hendrabratanata.moviecatalog.POJO.MovieItem;
import com.hendrabratanata.moviecatalog.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class MyIntentService extends IntentService {
    public String  TAG="tag";
    public MyIntentService() {
        super("MyIntentService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent: "+intent);
        if (intent != null) {

            ArrayList<MovieItem> movieItems = new MyAsynTaskRelease(this,null,"up").loadInBackground();
            if(movieItems != null ){
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String tanggalRelease = dateFormat.format(calendar.getTime());
                Log.d(TAG, "Movie item "+movieItems.size());
                for (int i = 0 ; i < movieItems.size();i++ ){
                    Log.d(TAG, "Judul "+ i +" "+movieItems.get(i).getRilis());

                    String tanggalTest = "2018-06-01";
                    if(movieItems.get(i).getRilis().equalsIgnoreCase(tanggalRelease)){

                        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
                        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                                .setSmallIcon(R.mipmap.icon_movie)
                                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),R.mipmap.icon_icon))
                                .setContentTitle(movieItems.get(i).getJudul())
                                .setContentText("Release Hari ini "+tanggalRelease)
                                .setColor(ContextCompat.getColor(this,android.R.color.transparent))
                                .setVibrate(new long[]{1000,1000,1000,1000,1000})
                                .setSound(alarmSound);
                        notificationManager.notify(i,builder.build());
                    }
                }
            }
        }
    }
}