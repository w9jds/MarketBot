package com.w9jds.marketbot.adapters;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
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

        @Bind(R.id.inventory)
        TextView volume;
        @Bind(R.id.price)
        TextView price;
        @Bind(R.id.location)
        TextView location;
        @Bind(R.id.range)
        TextView range;
        @Bind(R.id.details_pane)
        View details;
//        @Bind(R.id.sales_progress)
//        View salesProgress;
        @Bind(R.id.range_container)
        View rangeContainer;

        boolean isOpen;
        boolean animationRunning;

        public OrderViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

//    private void animateSalesProgressBar(final OrderViewHolder viewHolder, MarketOrder order) {
//        ValueAnimator slideAnimation = ValueAnimator
//                .ofInt(0, calculateSalesProgress(viewHolder, order))
//                .setDuration(250);
//
//        slideAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                viewHolder.salesProgress.getLayoutParams().width = (int)animation.getAnimatedValue();
//                viewHolder.salesProgress.requestLayout();
//            }
//        });
//
//        AnimatorSet set = new AnimatorSet();
//        set.play(slideAnimation);
//        set.setInterpolator(new AccelerateInterpolator());
//        set.start();
//    }

    private void animateDetailsPane(final OrderViewHolder holder) {
        ValueAnimator slideOpen;

        if (!holder.isOpen) {
            slideOpen = ValueAnimator
                    .ofInt(0, calculateDetailsPaneHeight(holder))
                    .setDuration(250);
        }
        else {
            slideOpen = ValueAnimator
                    .ofInt(calculateDetailsPaneHeight(holder), 0)
                    .setDuration(250);
        }

        slideOpen.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                holder.details.getLayoutParams().height = (int) animation.getAnimatedValue();
                holder.details.requestLayout();
            }
        });

        slideOpen.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                holder.animationRunning = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                holder.animationRunning = false;
                holder.isOpen = !holder.isOpen;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        AnimatorSet set = new AnimatorSet();
        set.play(slideOpen);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.start();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final OrderViewHolder holder = new OrderViewHolder(LayoutInflater.from(context).inflate(
                R.layout.market_order_item, parent, false));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!holder.animationRunning) {
                    animateDetailsPane(holder);
                }
            }
        });

        return holder;
    }

    private int calculateDetailsPaneHeight(OrderViewHolder holder) {
        int widthSpec = View.MeasureSpec.makeMeasureSpec(holder.itemView.getWidth(), View.MeasureSpec.EXACTLY);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

        holder.details.measure(widthSpec, heightSpec);
        return holder.details.getMeasuredHeight();
    }

    private int calculateSalesProgress(OrderViewHolder holder, MarketOrder order) {
        double percentage = (double)order.getVolume() / (double)order.getVolumeStart();

        return (int)(holder.itemView.getWidth() * percentage);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final OrderViewHolder viewHolder = (OrderViewHolder) holder;
        final MarketOrder order = (MarketOrder) getItem(position);

        DecimalFormat formatter = new DecimalFormat("#,###.00");
        String price = formatter.format(order.getPrice()) + " ISK";

        formatter = new DecimalFormat("#,###");
        String volume = formatter.format(order.getVolume());

        viewHolder.location.setText(order.getLocation().getName());
        viewHolder.price.setText(price);
        viewHolder.volume.setText(volume);

        if (order.isBuyOrder()) {
            String range = order.getRange();
            viewHolder.rangeContainer.setVisibility(View.VISIBLE);

            if (StringUtils.isNumeric(range)) {
                range = range + " Jumps";
            }
            range = WordUtils.capitalizeFully(range);
            viewHolder.range.setText(range);
        }
        else {
            viewHolder.rangeContainer.setVisibility(View.GONE);
        }

        viewHolder.details.getLayoutParams().height = 0;
        viewHolder.details.requestLayout();

        holder.itemView.post(new Runnable() {
            @Override
            public void run() {
//                animateSalesProgressBar(viewHolder, order);

                if (viewHolder.isOpen) {
                    animateDetailsPane(viewHolder);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    private class Comparitor implements Comparator<MarketOrder> {
        @Override
        public int compare(MarketOrder lhs, MarketOrder rhs) {
            if (lhs.isBuyOrder()) {
                if (lhs.getPrice() < rhs.getPrice()) {
                    return 1;
                }
                else if (lhs.getPrice() > rhs.getPrice()) {
                    return -1;
                }
                else {
                    return 0;
                }
            } else {
                if (lhs.getPrice() < rhs.getPrice()) {
                    return -1;
                }
                else if (lhs.getPrice() > rhs.getPrice()) {
                    return 1;
                }
                else {
                    return 0;
                }
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
