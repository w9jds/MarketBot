package com.w9jds.marketbot.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.w9jds.marketbot.R;
import com.w9jds.marketbot.classes.models.MarketOrder;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Jeremy on 3/1/2016.
 *
 * Modified by Alexander Whipp on 4/8/2016.
 */

public final class OrdersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<MarketOrder> orders = new ArrayList<>();

    public OrdersAdapter(Context context) {
        this.context = context;
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.inventory) TextView volume;
        @Bind(R.id.price) TextView price;
        @Bind(R.id.location) TextView location;
        @Bind(R.id.range) TextView range;
        @Bind(R.id.range_container) View rangeContainer;

        public OrderViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrderViewHolder(LayoutInflater.from(context).inflate(
                R.layout.market_order_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final OrderViewHolder viewHolder = (OrderViewHolder) holder;
        final MarketOrder order = (MarketOrder) getItem(position);

        DecimalFormat formatter;
        String price;
        int color = -1;
        if(order.isMarginOrder()){

            formatter = new DecimalFormat("##0.00");
            price = formatter.format(order.getPrice()) + "%";

            if (order.getPrice() <= 20) {
                color = 0xAAAA0000;
            }
            else {
                color = 0xAA00AA00;
            }

        }
        else {

            formatter = new DecimalFormat("#,###.00");
            price = formatter.format(order.getPrice()) + " ISK";

        }

        formatter = new DecimalFormat("#,###");
        String volume = formatter.format(order.getVolume());

        viewHolder.location.setText(order.getLocation());
        viewHolder.price.setText(price);
        if(color != -1) {
            viewHolder.price.setTextColor(color);
        }
        if(!order.isMarginOrder()) {
            viewHolder.volume.setText(volume);
        }

        if (order.isBuyOrder()) {
            String range = order.getRange();
            viewHolder.rangeContainer.setVisibility(View.VISIBLE);

//            if (StringUtils.isNumeric(range)) {
//                range = range + " Jumps";
//            }
//            range = WordUtils.capitalizeFully(range);
//            viewHolder.range.setText(range);
        }
        else {
            viewHolder.rangeContainer.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    private class Comparitor implements Comparator<MarketOrder> {
        @Override
        public int compare(MarketOrder lhs, MarketOrder rhs) {
            if (lhs.isBuyOrder()) {
                return lhs.getPrice() < rhs.getPrice() ? 1 : lhs.getPrice() > rhs.getPrice() ? -1 : 0;
            }
            else {
                return lhs.getPrice() < rhs.getPrice() ? -1 : lhs.getPrice() > rhs.getPrice() ? 1 : 0;
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
            notifyItemRangeChanged(0, newSize);
            notifyItemRangeRemoved(newSize, oldSize - newSize);
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
        int oldSize = orders.size();

        orders.clear();
        notifyItemRangeRemoved(0, oldSize);
    }

}
