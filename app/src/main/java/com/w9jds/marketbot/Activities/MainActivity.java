package com.w9jds.marketbot.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.w9jds.eveapi.Models.MarketGroup;
import com.w9jds.eveapi.Models.MarketItemBase;
import com.w9jds.marketbot.R;
import com.w9jds.marketbot.adapters.MarketGroupsAdapter;
import com.w9jds.marketbot.data.BaseDataManager;
import com.w9jds.marketbot.data.DataManager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MarketGroupsAdapter.onMarketGroupChanged,
        BaseDataManager.DataLoadingCallbacks{

    @Bind(R.id.market_groups)
    RecyclerView recyclerView;

    @Bind(R.id.dataloading_progress)
    ProgressBar progressBar;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private ActionBar actionBar;
    private DataManager dataManager;
    private MarketGroupsAdapter adapter;
    private LinearLayoutManager layoutManager;

    private ArrayList<? extends MarketItemBase> marketGroupList;
    private MarketGroup currentParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        if (actionBar != null) {
            // needs to be space so toolbar doesn't remove it from the view hierarchy
            actionBar.setTitle(" ");
        }

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        dataManager = new DataManager(this, getApplication()) {
            @Override
            public void onDataLoaded(List<? extends MarketItemBase> data) {
                if (marketGroupList == null) {
                    marketGroupList = new ArrayList<>(data);
                }

                adapter.addAndResort(data);
            }

            @Override
            public void onDataLoaded(Object data) {
                // never fired
            }
        };

        dataManager.registerCallback(this);

        adapter = new MarketGroupsAdapter(this, dataManager, this);
        recyclerView.setAdapter(adapter);

        dataManager.loadMarketGroups();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void updateSelectedParentGroup(MarketGroup group) {
        currentParent = group;

        if (group.items.size() > 0) {
            adapter.updateCollection(group.items.values());
        }

        if (currentParent != null) {
            if (group.children.size() > 0) {
                if (group.items.size() > 0) {
                    adapter.addAndResort(group.children.values());
                }
                else {
                    adapter.clear();
                    adapter.updateCollection(group.children.values());
                }
            } else {
                if (group.items.size() < 1) {
                    adapter.clear();
                }

                dataManager.loadGroupTypes(currentParent.getTypesLocation());
            }
        }


        animateTitleChange();
//        showToolbar();
        recyclerView.smoothScrollToPosition(0);
    }

    /**
     * Since we don't have access ot the views inside the toolbar we need to find the title view
     * @return Toolbar Title
     */
    private View getToolbarTitle() {

        for (int i = 0; i < toolbar.getChildCount(); i++) {
            View child = toolbar.getChildAt(i);
            if (child instanceof TextView) {
                return child;
            }
        }

        return new View(this);
    }

    private void animateTitleChange() {
        final View view = getToolbarTitle();

        if (view instanceof TextView) {
            AlphaAnimation fadeOut = new AlphaAnimation(1f, 0f);
            fadeOut.setDuration(250);
            fadeOut.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (currentParent != null) {
                        actionBar.setTitle(currentParent.getName());
                        actionBar.setDisplayShowHomeEnabled(true);
                        actionBar.setDisplayHomeAsUpEnabled(true);
                    }
                    else {
                        actionBar.setTitle(" ");
                        actionBar.setDisplayShowHomeEnabled(false);
                        actionBar.setDisplayHomeAsUpEnabled(false);
                    }

                    AlphaAnimation fadeIn = new AlphaAnimation(0f, 1f);
                    fadeIn.setDuration(250);
                    view.startAnimation(fadeIn);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            view.startAnimation(fadeOut);
        }
    }

    @Override
    public void onBackPressed() {
        if (!dataManager.isDataLoading()) {
            if (currentParent != null && currentParent.hasParent()) {
                bfsForParent(currentParent.getParentGroupId());
                adapter.updateCollection(currentParent.children.values());
            } else if (currentParent != null) {
                adapter.updateCollection(marketGroupList);
                currentParent = null;
            } else {
                super.onBackPressed();
            }

            animateTitleChange();
        }
    }

    @Override
    public void dataStartedLoading() {
        if (adapter.getAdapterSize() < 1) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void dataFinishedLoading() {
        progressBar.setVisibility(View.GONE);
    }

    private void bfsForParent(long parentId) {
        boolean parentFound = false;
        Queue queue = new LinkedList();

        for (MarketItemBase group : marketGroupList) {
            if (parentFound) {
                break;
            }

            queue.add(group);

            while(!queue.isEmpty()) {
                MarketGroup node = (MarketGroup)queue.remove();
                if (node.getId() == parentId) {
                    currentParent = node;
                    parentFound = true;
                    queue.clear();
                }
                else {
                    for (MarketGroup child : node.children.values()) {
                        queue.add(child);
                    }
                }
            }
        }
    }
}
