package com.ichirotech.bratanata.favoriteapp;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import static android.provider.BaseColumns._ID;

import static com.ichirotech.bratanata.favoriteapp.DatabaseContrac.FavoriteColumns.BACKDROP;
import static com.ichirotech.bratanata.favoriteapp.DatabaseContrac.FavoriteColumns.BAHASA;
import static com.ichirotech.bratanata.favoriteapp.DatabaseContrac.FavoriteColumns.DESC;
import static com.ichirotech.bratanata.favoriteapp.DatabaseContrac.FavoriteColumns.JUDUL;
import static com.ichirotech.bratanata.favoriteapp.DatabaseContrac.FavoriteColumns.POPULAR;
import static com.ichirotech.bratanata.favoriteapp.DatabaseContrac.FavoriteColumns.POSTER;
import static com.ichirotech.bratanata.favoriteapp.DatabaseContrac.FavoriteColumns.RELIS;
import static com.ichirotech.bratanata.favoriteapp.DatabaseContrac.FavoriteColumns.SCORE;
import static com.ichirotech.bratanata.favoriteapp.DatabaseContrac.getColumnsInt;
import static com.ichirotech.bratanata.favoriteapp.DatabaseContrac.getColumnsString;

public class MovieItem implements Parcelable {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private String poster;
    private String judul;
    private String bahasa;
    private String score;
    private String rilis;
    private String overview;
    private String popular;
    private String backdrop;

    public String getPopular() {
        return popular;
    }

    public void setPopular(String popular) {
        this.popular = popular;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }



    public MovieItem(JSONObject object) {
        try {

            String mPoster = object.getString("poster_path");
            String mJudul = object.getString("title");
            String mBahasa = object.getString("original_language");
            String mScore = object.getString("vote_average");
            String mRilis = object.getString("release_date");
            String mOverView = object.getString("overview");
            String mBackDrop = object.getString("backdrop_path");
            String mPopular = object.getString("popularity");

            this.popular = mPopular;
            this.poster = mPoster;
            this.backdrop = mBackDrop;
            this.judul = mJudul;
            this.bahasa = mBahasa;
            this.score = mScore;
            this.rilis = mRilis;
            this.overview = mOverView;


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public MovieItem(Cursor curso){
        this.popular    = getColumnsString(curso,POPULAR);
        this.id         = getColumnsInt  (curso, _ID);
        this.poster     = getColumnsString(curso,POSTER);
        this.backdrop   = getColumnsString(curso,BACKDROP);
        this.judul      = getColumnsString(curso, JUDUL);
        this.bahasa     = getColumnsString(curso,BAHASA);
        this.score      = getColumnsString(curso,SCORE);
        this.overview   = getColumnsString(curso, DESC);
        this.rilis      = getColumnsString(curso, RELIS);
    }


    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getBahasa() {
        return bahasa;
    }

    public void setBahasa(String bahasa) {
        this.bahasa = bahasa;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getRilis() {
        return rilis;
    }

    public void setRilis(String rilis) {
        this.rilis = rilis;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.poster);
        dest.writeString(this.judul);
        dest.writeString(this.bahasa);
        dest.writeString(this.score);
        dest.writeString(this.rilis);
        dest.writeString(this.overview);
        dest.writeString(this.popular);
        dest.writeString(this.backdrop);
    }

    protected MovieItem(Parcel in) {
        this.id = in.readInt();
        this.poster = in.readString();
        this.judul = in.readString();
        this.bahasa = in.readString();
        this.score = in.readString();
        this.rilis = in.readString();
        this.overview = in.readString();
        this.popular = in.readString();
        this.backdrop = in.readString();
    }

    public static final Creator<MovieItem> CREATOR = new Creator<MovieItem>() {
        @Override
        public MovieItem createFromParcel(Parcel source) {
            return new MovieItem(source);
        }

        @Override
        public MovieItem[] newArray(int size) {
            return new MovieItem[size];
        }
    };
}
