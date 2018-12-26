package com.hendrabratanata.moviecatalog.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.hendrabratanata.moviecatalog.R;
import com.hendrabratanata.moviecatalog.Support.AlarmReciver;
import com.hendrabratanata.moviecatalog.Support.NotifPrefe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotifSetting extends AppCompatActivity {
@BindView(R.id.chk_release)CheckBox cekRelease;
@BindView(R.id.chk_daily)CheckBox cekdaily;
public static final String NOTIF_DAILY = "08:00";
public static final String NOTIF_RELEASE= "07:00";
public static final String PESAN_DAILY = "Hi...hari ini jangan lupa kunjungi Movie Catalog ya..";
private NotifPrefe notifPrefe;
private AlarmReciver alarmReciver;
private String TAG = "tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif_setting);
        ButterKnife.bind(this);
        alarmReciver = new AlarmReciver();
        notifPrefe = new NotifPrefe(this);
        cekdaily.setChecked(notifPrefe.getChkDaily().equalsIgnoreCase("true"));
        cekRelease.setChecked(notifPrefe.getChkRelease().equalsIgnoreCase("true"));


    }
    public  void NotifSettingChaked(View viiew){
            boolean chek = ((CheckBox)viiew).isChecked();
            switch (viiew.getId()){
                case R.id.chk_daily:
                    if(chek){
                        notifPrefe.setChkDaily("true");
                        notifPrefe.setDailyTime(NOTIF_DAILY);
                        alarmReciver.setRepeatingAlarmDaily(this,notifPrefe.getDailyTime(),AlarmReciver.TYPE_DAILY,PESAN_DAILY);
                        Toast.makeText(this,"Anda Mendapatkan upadate setiap hari",Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onCreate: "+cekdaily.isChecked());

                    }else {
                        notifPrefe.setChkDaily("false");
                        Toast.makeText(this,"Anda tidak akan Mendapatkan upadate",Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onCreate: "+cekdaily.isChecked());
                    }
                    break;
                case R.id.chk_release:
                    if(chek){
                        notifPrefe.setChkRelease("true");
                        notifPrefe.setReleaseTime(NOTIF_RELEASE);
                        alarmReciver.setRepeatingRelease(NotifSetting.this,notifPrefe.getReleaseTime(),AlarmReciver.TYPE_RELEASE,"Pesan");
                        Toast.makeText(NotifSetting.this,"Anda Mendapatkan upadate setiap hari",Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onCreate: "+cekRelease.isChecked());

                    }else {
                        notifPrefe.setChkRelease("false");
                        Toast.makeText(this,"Anda tidak akan Mendapatkan upadate",Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onCreate: "+cekRelease.isChecked());
                    }
                    break;
            }
    }

}
