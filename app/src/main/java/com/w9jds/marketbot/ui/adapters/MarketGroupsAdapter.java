package com.w9jds.marketbot.ui.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.w9jds.marketbot.R;
import com.w9jds.marketbot.classes.models.MarketGroup;
import com.w9jds.marketbot.classes.models.MarketItemBase;
import com.w9jds.marketbot.classes.models.Type;
import com.w9jds.marketbot.ui.ItemActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import butterknife.Bind;
import butterknife.ButterKnife;

public final class MarketGroupsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int MARKET_GROUP_VIEW = 0;
    private final int MARKET_TYPE_VIEW = 1;

    private final Activity host;
    private final LayoutInflater layoutInflater;

    private boolean toClear = false;
    private onMarketGroupChanged groupChangedListener;
    private ArrayList<MarketItemBase> items = new ArrayList<>();

    public interface onMarketGroupChanged {
        void updateSelectedParentGroup(MarketGroup group);
    }

    public MarketGroupsAdapter(Activity host, onMarketGroupChanged changed) {
        this.groupChangedListener = changed;
        this.host = host;
        this.layoutInflater = LayoutInflater.from(host);
        this.items = new ArrayList<>();
    }

    public MarketGroupsAdapter(Activity host) {
        this.host = host;
        this.layoutInflater = LayoutInflater.from(host);
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

        @Bind(R.id.item_image) ImageView image;
        @Bind(R.id.item_title) TextView title;

        public MarketTypeHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Nullable
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

        holder.itemView.setOnClickListener(v -> {
            MarketGroup item = (MarketGroup) getItem(holder.getAdapterPosition());
            groupChangedListener.updateSelectedParentGroup(item);
        });

        return holder;
    }

    private MarketTypeHolder createMarketTypeHolder(ViewGroup parent) {
        final MarketTypeHolder holder = new MarketTypeHolder(layoutInflater.inflate(
                R.layout.type_item_layout, parent, false));

        holder.itemView.setOnClickListener(v -> {
            Type type = (Type) getItem(holder.getAdapterPosition());

            final Intent intent = new Intent();
            intent.setClass(host, ItemActivity.class);
            intent.putExtra("currentType", type);

            final ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(host,
                    new Pair(host.findViewById(R.id.app_bar), host.getString(R.string.toolbar_transition_name)),
                    new Pair(holder.image, host.getString(R.string.type_icon_transition)),
                    new Pair(holder.title, host.getString(R.string.type_name_transition)));

            ActivityCompat.startActivity(host, intent, options.toBundle());
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

    public int getAdapterSize() {
        return items.size();
    }

    private void bindMarketGroup(final MarketGroup group, MarketGroupHolder holder) {
        holder.title.setText(group.getName());

        holder.subtitle.setVisibility(View.VISIBLE);
        if (group.getDescription().equals("")) {
            holder.subtitle.setVisibility(View.GONE);
        }
        else {
            holder.subtitle.setText(group.getDescription());
        }
    }

    private void bindMarketType(final Type type, MarketTypeHolder holder) {
        holder.title.setText(type.getName());

        Glide.with(host)
            .load(type.getIcon())
            .into(holder.image);
    }

    public void updateCollection(Collection<? extends MarketItemBase> newChildren) {
        ArrayList<MarketItemBase> groups = new ArrayList<>(newChildren);

        int newSize;
        int oldSize = items.size();

        if (toClear) {
            toClear = false;
            newSize = groups.size();
            items = groups;
        }
        else {
            newSize = oldSize + groups.size();
            items.addAll(groups);
        }

        if (newSize < oldSize) {
            notifyItemRangeRemoved(newSize, oldSize - newSize);
            notifyItemRangeChanged(0, newSize);
        }
        if (newSize == oldSize) {
            notifyItemRangeChanged(0, newSize);
        }
        if (newSize > oldSize) {
            notifyItemRangeChanged(0, oldSize);
            notifyItemRangeInserted(oldSize, newSize - oldSize);
        }
    }

    public void clear() {
        int oldSize = items.size();

        items.clear();
        notifyItemRangeRemoved(0, oldSize);
    }

    public void setToClear(boolean clear) {
        toClear = clear;
    }

//    private class TypeComparator implements Comparator<MarketItemBase> {
//        @Override
//        public int compare(MarketItemBase lhs, MarketItemBase rhs) {
//            if (lhs instanceof MarketGroup && rhs instanceof Type) {
//                return -1;
//            }
//            if (lhs instanceof Type && rhs instanceof MarketGroup) {
//                return 1;
//            }
//
//            return lhs.getName().compareTo(rhs.getName());
//        }
//    }
}
