package com.hendrabratanata.moviecatalog.Activity;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hendrabratanata.moviecatalog.Database.DatabaseContrac;
import com.hendrabratanata.moviecatalog.Database.DbFavoriteHelper;
import com.hendrabratanata.moviecatalog.FavoriteWidget;
import com.hendrabratanata.moviecatalog.POJO.MovieItem;
import com.hendrabratanata.moviecatalog.R;
import com.hendrabratanata.moviecatalog.StackWidgetService;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.provider.BaseColumns._ID;
import static com.hendrabratanata.moviecatalog.Database.DatabaseContrac.CONTEN_URI;
import static com.hendrabratanata.moviecatalog.Database.DatabaseContrac.FavoriteColumns.BACKDROP;
import static com.hendrabratanata.moviecatalog.Database.DatabaseContrac.FavoriteColumns.BAHASA;
import static com.hendrabratanata.moviecatalog.Database.DatabaseContrac.FavoriteColumns.DESC;
import static com.hendrabratanata.moviecatalog.Database.DatabaseContrac.FavoriteColumns.JUDUL;
import static com.hendrabratanata.moviecatalog.Database.DatabaseContrac.FavoriteColumns.POPULAR;
import static com.hendrabratanata.moviecatalog.Database.DatabaseContrac.FavoriteColumns.POSTER;
import static com.hendrabratanata.moviecatalog.Database.DatabaseContrac.FavoriteColumns.RELIS;
import static com.hendrabratanata.moviecatalog.Database.DatabaseContrac.FavoriteColumns.SCORE;

public class DetaiMovie extends AppCompatActivity implements View.OnClickListener {
public  static final  String UPDATE_WIDGET ="update Widget";
    @BindView(R.id.img_BackDrop)
    ImageView imgBackDrop;
    @BindView(R.id.tv_bahasa)
    TextView tv_bahasa;
    @BindView(R.id.tv_desc)
    TextView tv_desc;
    @BindView(R.id.tv_judul)
    TextView tv_judul;
    @BindView(R.id.tv_popular)
    TextView tv_popular;
    @BindView(R.id.tv_rilis)
    TextView tv_rilis;
    @BindView(R.id.tv_reting)
    TextView tv_score;
    @BindView(R.id.btn_addFavorite)
    ImageButton btnAddFavorite;
    public final static String EXTRA_ITEM = "ITEM extra";

    private ContentValues values;
    DbFavoriteHelper helper;
    Cursor cursor;
    Boolean fav=false;
    final int ALERT_DIALOG_DELETE = 20;
    final int ALERT_DIALOG_CLOSE = 10;
    MovieItem item1;
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detai_activity);
        ButterKnife.bind(this);
            helper = new DbFavoriteHelper(this);

        MovieItem item = getIntent().getParcelableExtra(EXTRA_ITEM);
        String mPoster      = item.getPoster();
        String mBackdrop    = item.getBackdrop();
        String mBahasa      = item.getBahasa();
        String mDesc        = item.getOverview();
        String mJudul       = item.getJudul();
        String mPopular     = item.getPopular();
        String mRilis       = item.getRilis();
        String mScore       = item.getScore();



        values = new ContentValues();
        values.put(POSTER,mPoster);
        values.put(BAHASA,mBahasa);
        values.put(BACKDROP,mBackdrop);
        values.put(DESC,mDesc);
        values.put(JUDUL,mJudul);
        values.put(POPULAR,mPopular);
        values.put(RELIS,mRilis);
        values.put(SCORE,mScore);
        helper.open();
        cursor = helper.query(mJudul);

        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            item1 = new MovieItem(cursor);
            Log.d("judul","curso get count "+ cursor.getCount());
            Log.d("judul","MJudul adalah"+ mJudul);
            Log.d("judul","Id"+ item1.getId());
            fav = true;
            Glide.with(DetaiMovie.this)
                    .load("http://image.tmdb.org/t/p/w342"+mBackdrop)
                    .into(imgBackDrop);
            tv_bahasa.setText("BAHASA : "+mBahasa);
            tv_desc.setText(mDesc);
            tv_judul.setText(mJudul);
            tv_popular.setText("VIEW :  "+mPopular);
            tv_rilis.setText("RILIS :  "+mRilis);
            tv_score.setText("SCORE    : "+mScore);
            btnAddFavorite.setOnClickListener(this);
            btnAddFavorite.setImageResource(R.drawable.ic_favorite_red_150dp);
        }else if(cursor.getCount() == 0) {
            fav = false;
            Glide.with(DetaiMovie.this)
                    .load("http://image.tmdb.org/t/p/w342" + mBackdrop)
                    .into(imgBackDrop);
            tv_bahasa.setText("BAHASA : " + mBahasa);
            tv_desc.setText(mDesc);
            tv_judul.setText(mJudul);
            tv_popular.setText("VIEW :  " + mPopular);
            tv_rilis.setText("RILIS :  " + mRilis);
            tv_score.setText("SCORE    : " + mScore);
            btnAddFavorite.setOnClickListener(this);
            btnAddFavorite.setImageResource(R.drawable.ic_favorite_mist_120dp);
        }
    }

    @Override
    public void onClick(View v) {

        helper = new DbFavoriteHelper(this);
        helper.open();
        if(!fav) {
            getContentResolver().insert(CONTEN_URI, values);
            Toast.makeText(this, "Film Telah di tambahkan ke favorite", Toast.LENGTH_SHORT).show();
            btnAddFavorite.setImageResource(R.drawable.ic_favorite_red_150dp);
            helper.close();
            updateWidget();

        }else{

            Toast.makeText(this,"Film Ini sudah difavoritkan",Toast.LENGTH_SHORT).show();
            showAlertDialog(ALERT_DIALOG_DELETE);
        }
    }
    public void updateWidget(){

            Intent i = new Intent(this,FavoriteWidget.class);
            i.setAction(UPDATE_WIDGET);
            sendBroadcast(i);
    }

    private void   showAlertDialog(int type){

        final boolean isDialogClose = type == ALERT_DIALOG_CLOSE;
        String dialogTitle = null, dialogMessage = null;

        if(isDialogClose){
            dialogTitle = "Batal";
            dialogMessage = "Apahkah anda ingin membatalkan peubahan pada form?";
        }else {
            dialogMessage = "Apahkah anda yakin ingin menghapus item ini?";
            dialogTitle ="Hapus Favorite";
        }
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton("ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(isDialogClose){
                            finish();
                        }else {
                            Uri uri = Uri.parse(CONTEN_URI+"/"+item1.getId());
                            getContentResolver().delete(uri,null,null);
                            Log.d("Uri","uri "+uri);
                            updateWidget();

                        }
                    }
                }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
}
