package com.hendrabratanata.moviecatalog.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hendrabratanata.moviecatalog.POJO.MovieItem;
import com.hendrabratanata.moviecatalog.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MovieAdapter extends BaseAdapter {

    private ArrayList<MovieItem> mData = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public MovieAdapter(Context context){
        this.mContext = context;
        mLayoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public void setData(ArrayList<MovieItem> item){
        this.mData = item;
        notifyDataSetChanged();
    }
    public void clearData(){
        mData.clear();
    }

    public void addItem(final MovieItem item){
        mData.add(item);
        notifyDataSetChanged();
    }
    public int getItemViewType(int position){
        return 0;

    }
    public int getViewTypeCount(){
        return  1;

    }

    @Override
    public int getCount() {
        if(mData == null)return 0;
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.item_list_movie,null);

            holder.mImg_poster  =(ImageView)convertView.findViewById(R.id.img_poster);
            holder.mJudul       =(TextView)convertView.findViewById(R.id.tv_judul);
            holder.mOverView    =(TextView)convertView.findViewById(R.id.tv_overview);
            holder.mRelis       =(TextView)convertView.findViewById(R.id.tv_relis);

            convertView.setTag(holder);


        }else {
            holder = (ViewHolder)convertView.getTag();

        }
        String rilis = mData.get(position).getRilis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = simpleDateFormat.parse(rilis);
            SimpleDateFormat new_date_format = new SimpleDateFormat("EEEE, dd/MM/yyyy");
            String date_of_release = new_date_format .format(date);
            holder.mRelis.setText(date_of_release);
        }catch (ParseException e){
            e.printStackTrace();
        }

        String urlGambar = "http://image.tmdb.org/t/p/w185"+mData.get(position).getPoster();
        holder.mJudul.setText(mData.get(position).getJudul());
        holder.mOverView.setText(mData.get(position).getOverview());


        if(mData.get(position).getPoster().equalsIgnoreCase("null")){
            Log.d("null ",""+mData.get(position).getPoster());
            holder.mImg_poster.setImageResource(R.drawable.ic_broken_image_black_24dp);
        }else {
            Log.d("not null ",""+mData.get(position).getPoster());
            Glide.with(mContext)
                    .load(urlGambar)
                    .into(holder.mImg_poster);
        }




        return convertView;
    }

    public static class ViewHolder{

        TextView mJudul,mRelis,mOverView;
        ImageView mImg_poster;

    }
}
