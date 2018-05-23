package com.hendrabratanata.moviecatalog.Adapter;

import android.view.View;

class CostumeOnItemClickListener implements View.OnClickListener {
    private int position;
    private onItemClickCallback onItemClickCallback;

    public CostumeOnItemClickListener(int position, onItemClickCallback onItemClickCallback){
        this.position = position;
        this.onItemClickCallback = onItemClickCallback;
    }

    @Override
    public void onClick(View v) {
        onItemClickCallback.onItemClicked(v,position);
    }


    public interface onItemClickCallback{
        void onItemClicked(View view, int position);
    }
}
