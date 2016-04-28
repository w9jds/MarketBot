package com.w9jds.marketbot.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.repacked.apache.commons.codec.binary.StringUtils;
import com.w9jds.marketbot.R;
import com.w9jds.marketbot.classes.models.MarketOrder;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public final class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<Object> items = new ArrayList<>();

    public ListAdapter(Context context) {
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


        formatter = new DecimalFormat("#,###.00");
        price = formatter.format(order.getPrice()) + " ISK";


        formatter = new DecimalFormat("#,###");
        String volume = formatter.format(order.getVolume());

        viewHolder.location.setText(order.getLocation());
        viewHolder.price.setText(price);
        viewHolder.volume.setText(volume);

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
        return items.size();
    }

    private class PriceComparator implements Comparator<MarketOrder> {
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
        return items.get(position);
    }

    public void updateCollection(List<?> newChildren) {
        List<Object> newOrders = new ArrayList<>(newChildren);
//        Collections.sort(newOrders, new PriceComparator());

        int newSize = newOrders.size();
        int oldSize = items.size();

        items = newOrders;

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
        int oldSize = items.size();

        items.clear();
        notifyItemRangeRemoved(0, oldSize);
    }

}
