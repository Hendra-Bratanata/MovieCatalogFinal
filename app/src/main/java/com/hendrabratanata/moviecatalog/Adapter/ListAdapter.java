package com.hendrabratanata.moviecatalog.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hendrabratanata.moviecatalog.POJO.MovieItem;
import com.hendrabratanata.moviecatalog.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.CategoryViewHolder> {

    private Context context;
    private ArrayList<MovieItem> listMovie = new ArrayList<>();

    public ListAdapter(Context context){
        this.context = context;
    }

    public ArrayList<MovieItem> getListMovie() {
        return listMovie;
    }

    public void setListMovie(ArrayList<MovieItem> listMovie) {
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
        holder.tvJudulList.setText(getListMovie().get(position).getJudul());
        holder.tvOverViewList.setText(getListMovie().get(position).getOverview());
        holder.tvRealisList.setText(getListMovie().get(position).getRilis());
        String urlPoster = "http://image.tmdb.org/t/p/w185" + getListMovie().get(position).getPoster();
        if (listMovie.get(position).getPoster().equalsIgnoreCase("null")) {
            holder.posterList.setImageResource(R.drawable.ic_broken_image_black_24dp);
        } else {
            Glide.with(context)
                    .load(urlPoster)
                    .into(holder.posterList);
        }

    }

    @Override
    public int getItemCount() {
        return getListMovie().size();
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
