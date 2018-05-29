package com.ichirotech.bratanata.favoriteapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ichirotech.bratanata.favoriteapp.DatabaseContrac.FavoriteColumns;

import java.net.URI;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ichirotech.bratanata.favoriteapp.DatabaseContrac.CONTEN_URI;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    @BindView(R.id.lv_notes)
    ListView lvMovie;

    private FavoriteAdapter adapter;

    private final int LOAD = 110;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("Favorite App");
        adapter = new FavoriteAdapter(this,null,true);
        lvMovie.setAdapter(adapter);
        lvMovie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) adapter.getItem(position);
                int ids = cursor.getInt(cursor.getColumnIndexOrThrow(FavoriteColumns._ID));
                Intent intent = new Intent(MainActivity.this,DetaiMovie.class);


                intent.setData(Uri.parse(CONTEN_URI+"/"+ids ));
                Log.d("tagUri","uri"+intent.getData());
                startActivity(intent);
            }
        });
        getSupportLoaderManager().initLoader(LOAD,null,this);


    }


    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(LOAD,null,this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

        return new CursorLoader(this,
                CONTEN_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adapter.swapCursor(null);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportLoaderManager().destroyLoader(LOAD);

    }

}
