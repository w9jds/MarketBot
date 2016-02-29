package com.w9jds.marketbot.ui.animations;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.w9jds.marketbot.adapters.MarketGroupsAdapter;

public class GridItemAnimator extends DefaultItemAnimator {

    @Override
    public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder viewHolder) {
        return true;
    }

    @Override
    public boolean animateChange(RecyclerView.ViewHolder oldHolder, RecyclerView.ViewHolder newHolder,
                                 ItemHolderInfo preInfo, ItemHolderInfo postInfo) {

        final MarketGroupsAdapter.MarketGroupHolder holder = (MarketGroupsAdapter.MarketGroupHolder)newHolder;

        TranslateAnimation exitAnimation = new TranslateAnimation(0, -holder.itemView.getWidth(), 0, 0);
        exitAnimation.setDuration(500);
        exitAnimation.setFillAfter(false);


        TranslateAnimation enterAnimation = new TranslateAnimation(holder.itemView.getWidth(), 0, 0, 0);
        enterAnimation.setDuration(500);
        enterAnimation.setFillAfter(false);

        exitAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                dispatchAnimationStarted(holder);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                dispatchAnimationFinished(holder);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        return super.animateChange(oldHolder, newHolder, preInfo, postInfo);
    }
}