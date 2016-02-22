package com.w9jds.marketbot.adapters;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.w9jds.marketbot.data.DataLoadingSubject;

/**
 * Created by Jeremy on 2/18/2016.
 */
public class MarketGroupsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements DataLoadingSubject.DataLoadingCallbacks {

    private final Activity host;
    private final LayoutInflater layoutInflater;
    private final @Nullable DataLoadingSubject dataLoading;

    public MarketGroupsAdapter(Activity host, DataLoadingSubject dataLoading) {
        this.host = host;
        this.layoutInflater = LayoutInflater.from(host);
        this.dataLoading = dataLoading;


    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch(viewType) {

        }


        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void dataStartedLoading() {

    }

    @Override
    public void dataFinishedLoading() {

    }
}
