package com.hendrabratanata.moviecatalog.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hendrabratanata.moviecatalog.Adapter.CursoListAdapter;
import com.hendrabratanata.moviecatalog.Database.DatabaseContrac;
import com.hendrabratanata.moviecatalog.Database.DbFavoriteHelper;
import com.hendrabratanata.moviecatalog.POJO.MovieItem;
import com.hendrabratanata.moviecatalog.R;
import com.hendrabratanata.moviecatalog.Support.ItemClickSupport;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hendrabratanata.moviecatalog.Database.DatabaseContrac.CONTEN_URI;

public class Favorite extends AppCompatActivity {
@BindView(R.id.rv_favorite)
    RecyclerView rvFavorite;
@BindView(R.id.toolbar2)
    Toolbar tollbar;
DbFavoriteHelper helper;
CursoListAdapter adapter;
Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        ButterKnife.bind(this);
        setSupportActionBar(tollbar);
        getSupportActionBar().setTitle("Favorite");
        rvFavorite.setLayoutManager(new LinearLayoutManager(this));
        rvFavorite.setHasFixedSize(true);
        adapter = new CursoListAdapter(this);
        adapter.setListMovie(cursor);
        rvFavorite.setAdapter(adapter);
        ItemClickSupport.addTo(rvFavorite).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                cursor.moveToPosition(position);
                MovieItem movieItem = new MovieItem(cursor);
                halamanDetail(movieItem);


            }
        });

        new LoadFavoriteData().execute();
    }
    private void halamanDetail(MovieItem item){
        Intent inten = new Intent(Favorite.this,DetaiMovie.class);
        inten.putExtra(DetaiMovie.EXTRA_ITEM,item);
        startActivity(inten);


    }


    private class LoadFavoriteData extends AsyncTask<Void,Void,Cursor>{
        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContentResolver().query(CONTEN_URI,null,null,null,null);
        }

        @Override
        protected void onPostExecute(Cursor data) {
            super.onPostExecute(cursor);
            cursor = data;
            adapter.setListMovie(cursor);
            adapter.notifyDataSetChanged();
            rvFavorite.setAdapter(adapter);

        }
    }
}
