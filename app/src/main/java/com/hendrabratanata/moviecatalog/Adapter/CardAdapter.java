package com.hendrabratanata.moviecatalog.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hendrabratanata.moviecatalog.POJO.MovieItem;
import com.hendrabratanata.moviecatalog.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {
    public CardAdapter(Context context) {
        this.context = context;
    }

    private Context context;

    public ArrayList<MovieItem> getListMovie() {
        return listMovie;
    }

    public void setListMovie(ArrayList<MovieItem> listMovie) {
        this.listMovie = listMovie;
    }

    private ArrayList<MovieItem> listMovie;


    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_movie,parent,false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        holder.tvjudulCard.setText(getListMovie().get(position).getJudul());
        holder.cardOverView.setText(getListMovie().get(position).getOverview());
        String urlPoster = "http://image.tmdb.org/t/p/w185"+getListMovie().get(position).getPoster();
        String urlGambar = "http://image.tmdb.org/t/p/w185"+getListMovie().get(position).getPoster();
        if(listMovie.get(position).getPoster().equalsIgnoreCase("null")){
            holder.imgCard.setImageResource(R.drawable.ic_broken_image_black_24dp);
        }else {
            Glide.with(context)
                    .load(urlPoster)
                    .override(350,550)
                    .into(holder.imgCard);
            holder.btnFavorite.setOnClickListener(new CostumeOnItemClickListener(position, new CostumeOnItemClickListener.onItemClickCallback() {
                @Override
                public void onItemClicked(View view, int position) {
                    Toast.makeText(context,"Favorite "+getListMovie().get(position).getJudul(),Toast.LENGTH_LONG).show();
                }
            }));
            holder.btnShare.setOnClickListener(new CostumeOnItemClickListener(position, new CostumeOnItemClickListener.onItemClickCallback() {
                @Override
                public void onItemClicked(View view, int position) {
                    Toast.makeText(context,"Share "+getListMovie().get(position).getJudul(),Toast.LENGTH_LONG).show();
                }
            }));
        }




    }

    @Override
    public int getItemCount() {
        return getListMovie().size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_photo_card)
        ImageView imgCard;
        @BindView(R.id.tv_cardJudul)
        TextView tvjudulCard;
        @BindView(R.id.tv_cardOverView)
        TextView cardOverView;
        @BindView(R.id.btn_favorite)
        Button btnFavorite;
        @BindView(R.id.btn_share)
        Button btnShare;
        public CardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
