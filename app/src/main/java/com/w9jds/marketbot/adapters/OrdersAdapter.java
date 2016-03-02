package com.w9jds.marketbot.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.w9jds.eveapi.Models.MarketItemBase;
import com.w9jds.eveapi.Models.MarketOrder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Jeremy on 3/1/2016.
 */
public final class OrdersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<MarketOrder> orders = new ArrayList<>();

    public OrdersAdapter() {

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    private class Comparitor implements Comparator<MarketOrder> {
        @Override
        public int compare(MarketOrder lhs, MarketOrder rhs) {
//            return lhs.getName().compareTo(rhs.getName());
            return -1;
        }
    }

    public void updateCollection(Collection<MarketOrder> newChildren) {
        ArrayList<MarketOrder> newOrders = new ArrayList<>(newChildren);
        Collections.sort(newOrders, new Comparitor());

        int newSize = newOrders.size();
        int oldSize = orders.size();

        orders = newOrders;

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

}
