package com.hendrabratanata.moviecatalog.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.hendrabratanata.moviecatalog.Adapter.CardAdapter;
import com.hendrabratanata.moviecatalog.Adapter.GridAdapter;
import com.hendrabratanata.moviecatalog.Adapter.ListAdapter;
import com.hendrabratanata.moviecatalog.Activity.DetaiMovie;
import com.hendrabratanata.moviecatalog.Support.ItemClickSupport;
import com.hendrabratanata.moviecatalog.POJO.MovieItem;
import com.hendrabratanata.moviecatalog.Support.MyAsynTask;
import com.hendrabratanata.moviecatalog.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MovieItem>>{
@BindView(R.id.rv_catagory) RecyclerView    rvCatagory;
@BindView(R.id.tv_kosong)   TextView        tvKosong;
@BindView(R.id.progres_bar) ProgressBar     progressBar;
@BindView(R.id.tv_desc1)    TextView        tvDescHome;
@BindView(R.id.tv_Welcome)  TextView        tvWecome;


public ListAdapter listAdapter;
public GridAdapter gridAdapter;
public CardAdapter cardAdapter;
public ArrayList<MovieItem > listMovie;

public String pesanQuery ;
public  static final String EXTRA_MOVIE = "extra movie";
public static final  String EXTRA_TYPE = "extra type";
    Boolean status = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment,container,false);
        ButterKnife.bind(this,view);

        tvKosong.setVisibility(View.INVISIBLE);
        listMovie = new ArrayList<>();
        progressBar.setVisibility(View.INVISIBLE);
        String mov="";
        String ty="";
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_MOVIE,mov);
        bundle.putString(EXTRA_TYPE,ty);

        getLoaderManager().initLoader(0,bundle,this);

        // Inflate the layout for this fragment
        return view;
    }

    @NonNull
    @Override
    public Loader<ArrayList<MovieItem>> onCreateLoader(int id, @Nullable Bundle args) {
        String movie = null;
        String type = null;
        if (args != null) {
            movie = args.getString(EXTRA_MOVIE);
            type  = args.getString(EXTRA_TYPE);
        }

        return new MyAsynTask(getContext(),movie,type);

    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<MovieItem>> loader, ArrayList<MovieItem> data) {

        if(status) {
            Log.d("Data", "Data: " + data);
            if (data.isEmpty()) {

                String pesan = pesanQuery.toUpperCase();
                String pesanKosong  =getResources().getString(R.string.judulfilm)+" "+ pesan +" "+ getResources().getString(R.string.tidak_ditemukan);
                tvKosong.setText(pesanKosong);
                tvKosong.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                tvDescHome.setVisibility(View.INVISIBLE);
                tvWecome.setVisibility(View.INVISIBLE);

            } else {
//            adapter.setData(data);
                listMovie.addAll(data);
                rvCatagory.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                tvKosong.setVisibility(View.GONE);
                tvDescHome.setVisibility(View.INVISIBLE);
                tvWecome.setVisibility(View.INVISIBLE);
                showListView();


//            listView.setVisibility(View.VISIBLE);


            }
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<MovieItem>> loader) {

    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.cari));
        searchView.setQueryHint(getResources().getString(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                pesanQuery = query;
                listMovie.clear();
                rvCatagory.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                Bundle bundle = new Bundle();
                bundle.putString(EXTRA_TYPE,"find");
                bundle.putString(EXTRA_MOVIE,query);
                status =true;
                getLoaderManager().restartLoader(0,bundle,HomeFragment.this);
                searchView.onActionViewCollapsed();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
        super.onCreateOptionsMenu(menu,inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_list:

                showListView();
                return true;
            case R.id.action_grid:

                showGridView();

                return true;
            case R.id.action_CardView:

                showCardView();
                return true;
        }
        return false;
    }
    private void showGridView() {
        gridAdapter = new GridAdapter(getContext());
        gridAdapter.notifyDataSetChanged();
        rvCatagory.setLayoutManager(new GridLayoutManager(getContext(),2));
        gridAdapter.setListMovie(listMovie);
        rvCatagory.setAdapter(gridAdapter);
        ItemClickSupport.addTo(rvCatagory).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                halamanDetail(listMovie.get(position));

            }
        });
    }

    private void showListView() {
        listAdapter = new ListAdapter(getContext());
        listAdapter.notifyDataSetChanged();
        rvCatagory.setLayoutManager(new LinearLayoutManager(getContext()));
        listAdapter.setListMovie(listMovie);
        rvCatagory.setAdapter(listAdapter);
        ItemClickSupport.addTo(rvCatagory).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                halamanDetail(listMovie.get(position));
            }
        });
    }
    private void showCardView() {


        cardAdapter = new CardAdapter(getContext());
        cardAdapter.notifyDataSetChanged();

        rvCatagory.setLayoutManager(new LinearLayoutManager(getContext()));
        cardAdapter.setListMovie(listMovie);
        rvCatagory.setAdapter(cardAdapter);

        ItemClickSupport.addTo(rvCatagory).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {


                halamanDetail(listMovie.get(position));

            }
        });
    }
    private void halamanDetail(MovieItem item){
        Intent inten = new Intent(getActivity(),DetaiMovie.class);
        inten.putExtra(DetaiMovie.EXTRA_POSTER,item.getPoster());
        inten.putExtra(DetaiMovie.EXTRA_BACKDROP,item.getBackdrop());
        inten.putExtra(DetaiMovie.EXTRA_BAHASA,item.getBahasa());
        inten.putExtra(DetaiMovie.EXTRA_SCORE,item.getScore());
        inten.putExtra(DetaiMovie.EXTRA_DESC,item.getOverview());
        inten.putExtra(DetaiMovie.EXTRA_RILIS,item.getRilis());
        inten.putExtra(DetaiMovie.EXTRA_JUDUL,item.getJudul());
        inten.putExtra(DetaiMovie.EXTRA_POPULAR,item.getPopular());
        startActivity(inten);
    }
}
