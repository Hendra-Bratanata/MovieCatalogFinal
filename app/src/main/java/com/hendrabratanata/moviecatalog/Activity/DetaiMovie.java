package com.hendrabratanata.moviecatalog.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hendrabratanata.moviecatalog.R;

public class DetaiMovie extends AppCompatActivity {


    public final static String EXTRA_POSTER ="extra_poster";
    public final static String EXTRA_BACKDROP ="extra_BACKDROP";
    public final static String EXTRA_BAHASA ="extra_BAHASA";
    public final static String EXTRA_SCORE ="extra_SCORE";
    public final static String EXTRA_DESC ="extra_DESC";
    public final static String EXTRA_RILIS ="extra_RILIS";
    public final static String EXTRA_JUDUL ="extra_JUDUL";
    public final static String EXTRA_POPULAR = "Extra Popular";

    TextView tv_bahasa,tv_score,tv_desc,tv_rilis,tv_judul,tv_popular;
    ImageView imgBackDrop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detai_activity);
        imgBackDrop = (ImageView)findViewById(R.id.img_BackDrop);
        tv_bahasa =(TextView)findViewById(R.id.tv_bahasa);
        tv_desc =(TextView)findViewById(R.id.tv_desc);
        tv_judul=(TextView)findViewById(R.id.tv_judul);
        tv_popular=(TextView)findViewById(R.id.tv_popular);
        tv_rilis = (TextView)findViewById(R.id.tv_rilis);
        tv_score = (TextView)findViewById(R.id.tv_reting);

        String mBackdrop = getIntent().getStringExtra(EXTRA_BACKDROP);
        String mBahasa =getIntent().getStringExtra(EXTRA_BAHASA);
        String mDesc = getIntent().getStringExtra(EXTRA_DESC);
        String mJudul = getIntent().getStringExtra(EXTRA_JUDUL);
        String mPopular = getIntent().getStringExtra(EXTRA_POPULAR);
        String mRilis = getIntent().getStringExtra(EXTRA_RILIS);
        String mScore=getIntent().getStringExtra(EXTRA_SCORE);


        Glide.with(DetaiMovie.this)
                .load("http://image.tmdb.org/t/p/w342"+mBackdrop)
                .into(imgBackDrop);
        tv_bahasa.setText("BAHASA : "+mBahasa);
        tv_desc.setText(mDesc);
        tv_judul.setText(mJudul);
        tv_popular.setText("VIEW :  "+mPopular);
        tv_rilis.setText("RILIS :  "+mRilis);
        tv_score.setText("SCORE    : "+mScore);
    }
}
