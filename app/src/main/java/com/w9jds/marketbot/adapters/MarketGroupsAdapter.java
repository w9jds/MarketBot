package com.w9jds.marketbot.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.w9jds.eveapi.Models.MarketGroup;
import com.w9jds.eveapi.Models.MarketItemBase;
import com.w9jds.marketbot.R;
import com.w9jds.marketbot.activities.GroupActivity;
import com.w9jds.marketbot.data.DataLoadingSubject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Jeremy on 2/18/2016.
 */
public class MarketGroupsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements DataLoadingSubject.DataLoadingCallbacks {

    private final Activity host;
    private final LayoutInflater layoutInflater;
    private final @Nullable DataLoadingSubject dataLoading;

    private List<MarketGroup> items;

    public MarketGroupsAdapter(Activity host, DataLoadingSubject dataLoading) {
        this.host = host;
        this.layoutInflater = LayoutInflater.from(host);
        this.dataLoading = dataLoading;
        this.items = new ArrayList<>();
    }

    static class MarketGroupHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.title) TextView title;
        @Bind(R.id.subtitle) TextView subtitle;

        public MarketGroupHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final MarketGroupHolder holder = new MarketGroupHolder(layoutInflater.inflate(
                R.layout.threeline_item_layout, parent, false));

        holder.itemView.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            }
        );

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindMarketGroup((MarketGroup) getItem(position), (MarketGroupHolder) holder);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public void dataStartedLoading() {

    }

    @Override
    public void dataFinishedLoading() {

    }

    private void bindMarketGroup(final MarketGroup group, MarketGroupHolder holder) {
        holder.title.setText(group.getName());
        holder.subtitle.setText(group.getDescription());
    }

    private void deduplicateAndAdd(List<? extends MarketItemBase> newItems) {
        final int count = getItemCount();
        for (MarketItemBase newItem : newItems) {
            boolean add = true;
            for (int i = 0; i < count; i++) {
                MarketGroup existingItem = (MarketGroup) getItem(i);
                if (existingItem.equals(newItem)) {
                    add = false;
                    break;
                }
            }
            if (add) {
                add((MarketGroup) newItem);
            }
        }
    }

    public void addAndResort(List<? extends MarketItemBase> newItems) {
        deduplicateAndAdd(newItems);
        sort();

        notifyDataSetChanged();
    }

    private void add(MarketGroup item) {
        items.add(item);
    }

    private void sort() {
        Collections.sort(items, new Comparitor());
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    private class Comparitor implements Comparator<MarketGroup> {

        @Override
        public int compare(MarketGroup lhs, MarketGroup rhs) {
            return lhs.getName().compareTo(rhs.getName());
        }
    }
}
