//package com.w9jds.marketbot.ui.adapters;
//
//import android.content.Context;
//import android.databinding.DataBindingUtil;
//import android.databinding.ViewDataBinding;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.w9jds.marketbot.BR;
//import com.w9jds.marketbot.R;
//import com.w9jds.marketbot.classes.models.MarketOrder;
//import com.w9jds.marketbot.classes.models.StationMargin;
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.lang3.text.WordUtils;
//
//import java.text.DecimalFormat;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//
//import butterknife.Bind;
//import butterknife.ButterKnife;
//
//public final class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//
//    private static final int MARKET_ORDER_VIEW = 111;
//    private static final int MARKET_MARGIN_VIEW = 101;
//
//    Context context;
//    LayoutInflater layoutInflater;
//    List<?> items = new ArrayList<>();
//
//    public ListAdapter(Context context) {
//        this.context = context;
//        this.layoutInflater = LayoutInflater.from(context);
//    }
//
//    static class MarketOrderHolder extends RecyclerView.ViewHolder {
//
//        @Bind(R.id.inventory) TextView volume;
//        @Bind(R.id.price) TextView price;
//        @Bind(R.id.location) TextView location;
//        @Bind(R.id.range) TextView range;
//        @Bind(R.id.range_container) View rangeContainer;
//
//        public MarketOrderHolder(View view) {
//            super(view);
//            ButterKnife.bind(this, view);
//        }
//    }
//
//    static class MarketMarginHolder extends RecyclerView.ViewHolder {
//
//        ViewDataBinding binding;
//
//        public MarketMarginHolder(View view) {
//            super(view);
//            binding = DataBindingUtil.bind(itemView);
//        }
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        switch(viewType) {
//            case MARKET_MARGIN_VIEW:
//                return createMarketMarginHolder(parent);
//            default:
//                return createMarketOrderHolder(parent);
//        }
//    }
//
//    private MarketOrderHolder createMarketOrderHolder(ViewGroup parent) {
//        return new MarketOrderHolder(layoutInflater.inflate(R.layout.market_order_item, parent, false));
//    }
//
//    private MarketMarginHolder createMarketMarginHolder(ViewGroup parent) {
//        return new MarketMarginHolder(layoutInflater.inflate(R.layout.market_margin_item, parent, false));
//    }
//
//    @Override
//    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
//        switch(getItemViewType(position)) {
//            case MARKET_ORDER_VIEW:
//                bindMarketOrderView((MarketOrder) getItem(position), (MarketOrderHolder) holder);
//                break;
//            case MARKET_MARGIN_VIEW:
//                bindMarketMarginView((StationMargin) getItem(position), (MarketMarginHolder) holder);
//                break;
//        }
//
//    }
//
//    private void bindMarketMarginView(StationMargin margin, MarketMarginHolder holder) {
//        holder.binding.setVariable(BR.margin, margin);
//        holder.binding.executePendingBindings();
//    }
//
//    private void bindMarketOrderView(MarketOrder order, MarketOrderHolder holder) {
//        DecimalFormat formatter;
//        String price;
//
//        formatter = new DecimalFormat("#,###.00");
//        price = formatter.format(order.getPrice()) + " ISK";
//
//
//        formatter = new DecimalFormat("#,###");
//        String volume = formatter.format(order.getVolume());
//
//        holder.location.setText(order.getLocation());
//        holder.price.setText(price);
//        holder.volume.setText(volume);
//
//        if (order.isBuyOrder()) {
//            String range = order.getRange();
//            holder.rangeContainer.setVisibility(View.VISIBLE);
//
//            if (StringUtils.isNumeric(range)) {
//                range = range + " Jumps";
//            }
//            range = WordUtils.capitalizeFully(range);
//            holder.range.setText(range);
//        }
//        else {
//            holder.rangeContainer.setVisibility(View.GONE);
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return items.size();
//    }
//
//    private class PriceComparator implements Comparator<MarketOrder> {
//        @Override
//        public int compare(MarketOrder lhs, MarketOrder rhs) {
//            if (lhs.isBuyOrder()) {
//                return lhs.getPrice() < rhs.getPrice() ? 1 : lhs.getPrice() > rhs.getPrice() ? -1 : 0;
//            }
//            else {
//                return lhs.getPrice() < rhs.getPrice() ? -1 : lhs.getPrice() > rhs.getPrice() ? 1 : 0;
//            }
//        }
//    }
//
//    public Object getItem(int position) {
//        return items.get(position);
//    }
//
//    public void updateCollection(List<?> newChildren) {
//        int newSize = newChildren.size();
//        int oldSize = items.size();
//
//        if (newSize > 0 && newChildren.get(0) instanceof MarketOrder) {
//            List<MarketOrder> orders = new ArrayList<>(newSize);
//            for (int i = 0; i < newSize; i++) {
//                orders.add((MarketOrder) newChildren.get(i));
//            }
//
//            Collections.sort(orders, new PriceComparator());
//            items = orders;
//        }
//        else {
//            items = newChildren;
//        }
//
//        if (newSize < oldSize) {
//            notifyItemRangeChanged(0, newSize);
//            notifyItemRangeRemoved(newSize, oldSize - newSize);
//        }
//        if (newSize == oldSize) {
//            notifyItemRangeChanged(0, newSize);
//        }
//        if (newSize > oldSize) {
//            notifyItemRangeChanged(0, oldSize);
//            notifyItemRangeInserted(oldSize, newSize - oldSize);
//        }
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        if (getItem(position) instanceof MarketOrder) {
//            return MARKET_ORDER_VIEW;
//        }
//        else {
//            return MARKET_MARGIN_VIEW;
//        }
//    }
//
//    public void clear() {
//        int oldSize = items.size();
//
//        items.clear();
//        notifyItemRangeRemoved(0, oldSize);
//    }
//
//}
