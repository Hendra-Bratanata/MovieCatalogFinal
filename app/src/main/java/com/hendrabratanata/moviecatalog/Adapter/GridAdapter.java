package com.hendrabratanata.moviecatalog.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hendrabratanata.moviecatalog.POJO.MovieItem;
import com.hendrabratanata.moviecatalog.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.GridViewHolder> {

    public GridAdapter(Context context) {
        this.context = context;
    }

    private Context context;
    private ArrayList<MovieItem> listMovie;

    public ArrayList<MovieItem> getListMovie() {
        return listMovie;
    }

    public void setListMovie(ArrayList<MovieItem> listMovie) {
        this.listMovie = listMovie;
    }


    @Override
    public GridViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_movie,parent,false);

        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GridViewHolder holder, int position) {
        if (listMovie.get(position).getPoster().equalsIgnoreCase("null")) {
            holder.imgPhotoGrid.setImageResource(R.drawable.ic_broken_image_black_24dp);
        } else {
            String urlGambar = "http://image.tmdb.org/t/p/w185" + getListMovie().get(position).getPoster();
            Glide.with(context)
                    .load(urlGambar)
                    .override(350, 550)
                    .into(holder.imgPhotoGrid);
        }
    }
    @Override
    public int getItemCount() {
        return getListMovie().size();
    }

    public class GridViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgPhotoGrid)
        ImageView imgPhotoGrid;
        public GridViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
