package com.hendrabratanata.moviecatalog.Support;




import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.hendrabratanata.moviecatalog.POJO.MovieItem;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MyAsynTask extends AsyncTaskLoader<ArrayList<MovieItem>> {
    private ArrayList<MovieItem> mdata;
    private Boolean mHasResault = false;
    private String movies;
    private String type;
    private final static String API_KEY = "024d4ff8dc72a08fd9cbe68bb62148f6";


    public MyAsynTask(final Context context, String movie, String type) {
        super(context);
        onContentChanged();
        this.movies =movie;
        this.type = type;
    }

    @Override
    protected void onStartLoading() {
        if(takeContentChanged())
            forceLoad();
        else if(mHasResault)
        deliverResult(mdata);

    }

    @Override
    public void deliverResult(ArrayList<MovieItem> data) {
        mdata = data;
        mHasResault =true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if(mHasResault){
            onReleasResource(mdata);
            mdata =null;
            mHasResault =false;

        }
    }
    protected void onReleasResource(ArrayList<MovieItem> data){

    }

    @Override
    public ArrayList<MovieItem> loadInBackground() {
        SyncHttpClient client =new SyncHttpClient();
        final ArrayList<MovieItem> movieItemses =new ArrayList<>();
        String url="";
        if(type.equalsIgnoreCase("find")) {
            url = "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&language=en-US&query=" + movies;
            
        }else if(type.equalsIgnoreCase("now")){
            url = "https://api.themoviedb.org/3/movie/now_playing?api_key="+API_KEY+"&language=en-US";


        }else if(type.equalsIgnoreCase("up")){

            url = "https://api.themoviedb.org/3/movie/upcoming?api_key="+API_KEY+"&language=en-US";
        }

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String resault = new String(responseBody);
                    JSONObject responObject = new JSONObject(resault);
                    JSONArray list = responObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);
                        MovieItem movieItem = new MovieItem(movie);
                        movieItemses.add(movieItem);
                    }
                    }catch(Exception e){
                        e.printStackTrace();
                    }}


            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

        return movieItemses;
    }
}
