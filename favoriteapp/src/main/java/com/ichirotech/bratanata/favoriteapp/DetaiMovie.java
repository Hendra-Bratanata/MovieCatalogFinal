package com.ichirotech.bratanata.favoriteapp;

import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ichirotech.bratanata.favoriteapp.DatabaseContrac.FavoriteColumns.JUDUL;
import static com.ichirotech.bratanata.favoriteapp.DatabaseContrac.FavoriteColumns.POSTER;
import static com.ichirotech.bratanata.favoriteapp.DatabaseContrac.getColumnsString;

public class DetaiMovie extends AppCompatActivity{

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

    public final static String EXTRA_ITEM = "ITEM extra";

    MovieItem item1 =null;
    Uri uri;
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detai_activity);
        ButterKnife.bind(this);

        uri = getIntent().getData();
        Log.d("getdata","Data "+uri);

        if(uri != null){

            Cursor cursor = getContentResolver().query(uri,null,null,null,null,null);
            Log.d("curso load","ssssssssssssssssssssssssssssssss");
            Log.d("curso load","ssssssssssssssssssssssssssssssss "+cursor.getCount());
            if(cursor != null){
                    if(cursor.moveToFirst()) item1 = new MovieItem(cursor);
                    cursor.close();
                }
            }
            if(item1 != null) {
            String urlPoster = "http://image.tmdb.org/t/p/w342"+item1.getBackdrop();
            Glide.with(this)
                    .load(urlPoster)
                    .into(imgBackDrop);
                tv_judul.setText(item1.getJudul());
                tv_score.setText(item1.getScore());
                tv_rilis.setText(item1.getRilis());
                tv_popular.setText(item1.getPopular());
                tv_bahasa.setText(item1.getBahasa());
                tv_desc.setText(item1.getOverview());
            }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_form,menu);
            return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_delet){

                        delete();        }
            return super.onOptionsItemSelected(item);
    }

    private void delete(){
        String dialogMessage = "Apahkah anda yakin ingin menghapus item ini?";
        String dialogTitle ="Hapus Favorite";

    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder
                .setMessage(dialogMessage)
            .setCancelable(false)
                .setPositiveButton("ya", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

                getContentResolver().delete(uri,null,null);
                finish();
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
