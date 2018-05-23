package com.hendrabratanata.moviecatalog.POJO;

import org.json.JSONObject;

public class MovieItem {

    private String poster;
    private String judul;
    private String bahasa;
    private String score;
    private String rilis;
    private String overview;
    private String popular;

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

    private String backdrop;

    public MovieItem(JSONObject object){
        try {
            String mPoster      = object.getString("poster_path");
            String mJudul       = object.getString("title");
            String mBahasa      = object.getString("original_language");
            String mScore       = object.getString("vote_average");
            String mRilis       = object.getString("release_date");
            String mOverView    = object.getString("overview");
            String mBackDrop    = object.getString("backdrop_path");
            String mPopular     = object.getString("popularity");

            this.popular    = mPopular;
            this.poster     = mPoster;
            this.backdrop   = mBackDrop;
            this.judul      = mJudul;
            this.bahasa     = mBahasa;
            this.score      = mScore;
            this.rilis      = mRilis;
            this.overview   = mOverView;



        }catch (Exception e){
            e.printStackTrace();
        }
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
}
