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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.w9jds.eveapi.Models.MarketGroup;
import com.w9jds.eveapi.Models.MarketItemBase;
import com.w9jds.eveapi.Models.Type;
import com.w9jds.marketbot.R;
import com.w9jds.marketbot.activities.GroupActivity;
import com.w9jds.marketbot.data.DataLoadingSubject;

import java.util.ArrayList;
import java.util.Collection;
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

    private final int MARKET_GROUP_VIEW = 0;
    private final int MARKET_TYPE_VIEW = 1;


    private final Activity host;
    private final LayoutInflater layoutInflater;
    private final @Nullable DataLoadingSubject dataLoading;

    private onMarketGroupChanged groupChangedListener;
    private List<? extends MarketItemBase> items;

    public interface onMarketGroupChanged {
        void updateSelectedParentGroup(MarketGroup group);
    }

    public MarketGroupsAdapter(Activity host, DataLoadingSubject dataLoading, onMarketGroupChanged changed) {
        this.groupChangedListener = changed;
        this.host = host;
        this.layoutInflater = LayoutInflater.from(host);
        this.dataLoading = dataLoading;
        this.items = new ArrayList<>();
    }

    public static class MarketGroupHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.title) TextView title;
        @Bind(R.id.subtitle) TextView subtitle;

        public MarketGroupHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public static class MarketTypeHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.item_image)
        ImageView image;
        @Bind(R.id.item_title)
        TextView title;
//        @Bind(R.id.item_subtitle)
//        TextView subtitle;

        public MarketTypeHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch(viewType) {
            case MARKET_GROUP_VIEW:
                return createMarketGroupHolder(parent);
            case MARKET_TYPE_VIEW:
                return createMarketTypeHolder(parent);
        }

        return null;
    }

    private MarketGroupHolder createMarketGroupHolder(ViewGroup parent) {
        final MarketGroupHolder holder = new MarketGroupHolder(layoutInflater.inflate(
                R.layout.threeline_item_layout, parent, false));

        holder.itemView.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MarketGroup item = (MarketGroup) getItem(holder.getAdapterPosition());
                    groupChangedListener.updateSelectedParentGroup(item);
                }
            }
        );

        return holder;
    }

    private MarketTypeHolder createMarketTypeHolder(ViewGroup parent) {
        final MarketTypeHolder holder = new MarketTypeHolder(layoutInflater.inflate(
                R.layout.type_item_layout, parent, false));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//        final Intent intent = new Intent();
//        intent.setClass(host, GroupActivity.class);
//        intent.putExtra("MarketGroup", item);
//
//        final ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(host,
//                Pair.create(holder.itemView, host.getString(R.string.transition_background)),
//                Pair.create(holder.itemView, host.getString(R.string.transition_title_background)));
//        host.startActivity(intent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch(getItemViewType(position)) {
            case MARKET_GROUP_VIEW:
                bindMarketGroup((MarketGroup) getItem(position), (MarketGroupHolder) holder);
                break;
            case MARKET_TYPE_VIEW:
                bindMarketType((Type) getItem(position), (MarketTypeHolder) holder);
                break;
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof MarketGroup) {
            return MARKET_GROUP_VIEW;
        }
        else {
            return MARKET_TYPE_VIEW;
        }
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

    private void bindMarketType(final Type type, MarketTypeHolder holder) {
        holder.title.setText(type.getName());
//        holder.subtitle.setText(type.);

        Glide.with(host)
                .load(type.getIconLink())
//                .placeholder(R.drawable.ic_profile_none)
                .into(holder.image);
    }

//    private void deduplicateAndAdd(Collection<? extends MarketItemBase> newItems) {
//        final int count = getItemCount();
//        for (MarketItemBase newItem : newItems) {
//            boolean add = true;
//            for (int i = 0; i < count; i++) {
//                MarketGroup existingItem = (MarketGroup) getItem(i);
//                if (existingItem.equals(newItem)) {
//                    add = false;
//                    break;
//                }
//            }
//            if (add) {
//                add((MarketGroup) newItem);
//            }
//        }
//    }

    public void addAndResort(Collection<? extends MarketItemBase> newItems) {
        ArrayList<MarketItemBase> groups = new ArrayList<>(newItems);
        Collections.sort(groups, new Comparitor());
        items = groups;

        notifyItemRangeInserted(0, items.size() - 1);
    }

//    private void add(MarketGroup item) {
//        items.add(item);
//        notifyItemInserted(items.size() - 1);
//    }

    public void updateCollection(Collection<? extends MarketItemBase> newChildren) {
        ArrayList<MarketItemBase> groups = new ArrayList<>(newChildren);
        Collections.sort(groups, new Comparitor());

        int newSize = groups.size();
        int oldSize = items.size();

        items = groups;

        if (newSize < oldSize) {
            notifyItemRangeChanged(0, newSize - 1);
            notifyItemRangeRemoved(newSize, oldSize - 1);
        }
        if (newSize == oldSize) {
            notifyItemRangeChanged(0, newSize - 1);
        }
        if (newSize > oldSize) {
            notifyItemRangeChanged(0, oldSize - 1);
            notifyItemRangeInserted(oldSize, newSize - 1);
        }

//        for (int i = 0; i < oldSize; i++) {
//            if (i < newSize) {
//                MarketGroup group = (MarketGroup) groups.get(i);
//
//                items.add(group);
//                if (i < oldSize) {
//                    notifyItemChanged(i);
//                }
//                else {
//                    notifyItemInserted(i);
//                }
//            }
//            else {
//                notifyItemRemoved(i);
//            }
//        }
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    private class Comparitor implements Comparator<MarketItemBase> {
        @Override
        public int compare(MarketItemBase lhs, MarketItemBase rhs) {
            return lhs.getName().compareTo(rhs.getName());
        }
    }
}
