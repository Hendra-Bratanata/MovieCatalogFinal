package com.hendrabratanata.moviecatalog.Adapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hendrabratanata.moviecatalog.Database.DatabaseContrac;
import com.hendrabratanata.moviecatalog.POJO.MovieItem;
import com.hendrabratanata.moviecatalog.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hendrabratanata.moviecatalog.Database.DatabaseContrac.FavoriteColumns.DESC;
import static com.hendrabratanata.moviecatalog.Database.DatabaseContrac.FavoriteColumns.JUDUL;
import static com.hendrabratanata.moviecatalog.Database.DatabaseContrac.FavoriteColumns.POSTER;
import static com.hendrabratanata.moviecatalog.Database.DatabaseContrac.FavoriteColumns.RELIS;
import static com.hendrabratanata.moviecatalog.Database.DatabaseContrac.getColumnsString;

public class CursoListAdapter extends RecyclerView.Adapter<CursoListAdapter.CategoryViewHolder> {

    private Activity activity;
    private Cursor listMovie;


    public CursoListAdapter(Activity activity){
        this.activity = activity;
    }

    public Cursor getListMovie() {
        return listMovie;
    }

    public void setListMovie(Cursor listMovie) {
        this.listMovie = listMovie;
        notifyDataSetChanged();
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_movie,parent,false);
        return new CategoryViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        MovieItem movie = getItem(position);
        holder.tvJudulList.setText(movie.getJudul());
        holder.tvOverViewList.setText(movie.getOverview());
        holder.tvRealisList.setText(movie.getRilis());
        String urlPoster = "http://image.tmdb.org/t/p/w185" +movie.getPoster();
        if (movie.getPoster().equalsIgnoreCase("null")) {
            holder.posterList.setImageResource(R.drawable.ic_broken_image_black_24dp);
        } else {
            Glide.with(activity)
                    .load(urlPoster)
                    .into(holder.posterList);
        }

    }

    private MovieItem getItem(int position) {
        if(!listMovie.moveToPosition(position)){
            throw new IllegalStateException("Position invalid");

        }
        return new MovieItem(listMovie);
    }

    @Override
    public int getItemCount() {
       if(listMovie == null) return  0;
       return  listMovie.getCount();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder{
        @BindView( R.id.tv_judul)
        TextView tvJudulList;
        @BindView( R.id.tv_overview)
        TextView tvOverViewList;
        @BindView( R.id.tv_relis)
        TextView tvRealisList;
        @BindView( R.id.img_poster)
        ImageView posterList;


        public CategoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
