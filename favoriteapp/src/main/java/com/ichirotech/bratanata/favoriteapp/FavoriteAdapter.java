package com.ichirotech.bratanata.favoriteapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideContext;
import com.bumptech.glide.load.model.GlideUrl;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.provider.BaseColumns._ID;
import static com.ichirotech.bratanata.favoriteapp.DatabaseContrac.FavoriteColumns.DESC;
import static com.ichirotech.bratanata.favoriteapp.DatabaseContrac.FavoriteColumns.JUDUL;
import static com.ichirotech.bratanata.favoriteapp.DatabaseContrac.FavoriteColumns.POSTER;
import static com.ichirotech.bratanata.favoriteapp.DatabaseContrac.FavoriteColumns.RELIS;
import static com.ichirotech.bratanata.favoriteapp.DatabaseContrac.getColumnsInt;
import static com.ichirotech.bratanata.favoriteapp.DatabaseContrac.getColumnsString;

public class FavoriteAdapter extends CursorAdapter {
//Card Item
//    @BindView(R.id.Poster_favorite)
//    ImageView PosterFavorite;
//    @BindView(R.id.tv_cardJudul_fav)
//    TextView tvjudulCard;
//    @BindView(R.id.tv_cardOverView_fav)
//    TextView cardOverView;
//    @BindView(R.id.btn_Detail_fav)
//    Button btnFavorite;
//    @BindView(R.id.btn_share_fav)
//    Button btnShare;
//    List Item
    @BindView(R.id.img_poster_list)
    ImageView imgPoster;
    @BindView(R.id.tv_judul_list)
    TextView tvJudul;
    @BindView(R.id.tv_overview_list)
    TextView tvdesc;
    @BindView(R.id.tv_relis_list)
    TextView tvrelis;


    public FavoriteAdapter(Context context, Cursor c,boolean autoQuery) {
        super(context, c,autoQuery);
    }



    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_movie,parent,false);
        return view;

    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    @Override
    public void bindView(final View view, Context context, final Cursor cursor) {
        ButterKnife.bind(this,view);

        String urlPoster = "http://image.tmdb.org/t/p/w185"+getColumnsString(cursor,POSTER);
        Log.d("tag","img url : "+urlPoster);
        Glide.with(context)
                .load(urlPoster)
                .into(imgPoster);
        tvJudul.setText(getColumnsString(cursor,JUDUL));
        tvdesc.setText(getColumnsString(cursor,DESC));
        tvrelis.setText(getColumnsString(cursor,RELIS));


    }



}
