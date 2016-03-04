package com.w9jds.marketbot.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.w9jds.eveapi.Models.MarketGroup;
import com.w9jds.eveapi.Models.MarketItemBase;
import com.w9jds.eveapi.Models.MarketOrder;
import com.w9jds.marketbot.R;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Jeremy on 3/1/2016.
 */
public final class OrdersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<MarketOrder> orders = new ArrayList<>();

    public OrdersAdapter(Context context) {
        this.context = context;
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.volume_remaining)
        TextView volume;
        @Bind(R.id.price)
        TextView price;
        @Bind(R.id.location)
        TextView location;
        @Bind(R.id.range)
        TextView range;

        public OrderViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final OrderViewHolder holder = new OrderViewHolder(LayoutInflater.from(context).inflate(
                R.layout.market_order_item, parent, false));

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        OrderViewHolder viewHolder = (OrderViewHolder) holder;
        MarketOrder order = (MarketOrder) getItem(position);

        DecimalFormat formatter = new DecimalFormat("#,###.00");
        String price = formatter.format(order.getPrice()) + " ISK";
        formatter = new DecimalFormat("#,###");
        String volumeRemaining = formatter.format(order.getVolume());
        String range = order.getRange();

        viewHolder.location.setText(order.getLocation().getName());
        viewHolder.price.setText(price);
        viewHolder.volume.setText(volumeRemaining);

        if (StringUtils.isNumeric(range)) {
            range = range + " Jumps";
        }

        range = WordUtils.capitalizeFully(range);
        viewHolder.range.setText(range);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    private class Comparitor implements Comparator<MarketOrder> {
        @Override
        public int compare(MarketOrder lhs, MarketOrder rhs) {
            if (lhs.isBuyOrder()) {
                return lhs.getPrice() < rhs.getPrice() ? 1 : -1;
            } else {
                return lhs.getPrice() < rhs.getPrice() ? -1 : 1;
            }
        }
    }

    public Object getItem(int position) {
        return orders.get(position);
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
