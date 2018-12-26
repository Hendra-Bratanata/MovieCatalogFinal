package com.hendrabratanata.moviecatalog.Support;

import android.content.Context;
import android.content.SharedPreferences;

public class NotifPrefe {
    private final String PREF_NAME = "notifPref";
    private final String KEY_DAILY_TIME = "dailyTime";
    private final String KEY_RELEASE_TIME = "releaseTime";
    private final String firstRun = "frist ";
    private final String chkDaily = "daily ";
    private final String chkRelease = "release ";


    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    public NotifPrefe (Context context){
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setDailyTime(String time){
        editor.putString(KEY_DAILY_TIME,time);
        editor.apply();

    }
    public String getDailyTime() {
        return sharedPreferences.getString(KEY_DAILY_TIME,null);
    }
    public void setReleaseTime(String time){
        editor.putString(KEY_RELEASE_TIME,time);
        editor.apply();
    }


    public String getReleaseTime(){
        return sharedPreferences.getString(KEY_RELEASE_TIME,null);
    }
    public String getFirstRun() {
        return sharedPreferences.getString(firstRun,null);

    }
    public void setFirstRun(String FirstRun){
        editor.putString(firstRun,FirstRun);
        editor.apply();
    }

    public String getChkDaily() {
        return sharedPreferences.getString(chkDaily,null);
    }
    public void setChkDaily(String chk){
        editor.putString(chkDaily,chk);
        editor.apply();
    }

    public String getChkRelease() {
        return sharedPreferences.getString(chkRelease,null);
    }


    public  void setChkRelease(String chk){
        editor.putString(chkRelease,chk);
        editor.apply();
    }
}
