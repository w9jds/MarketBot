package com.w9jds.marketbot.activities;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
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
import com.w9jds.marketbot.data.DataLoadingSubject;
import com.w9jds.marketbot.data.DataManager;
import com.w9jds.marketbot.data.storage.DataContracts;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MarketGroupsAdapter.onMarketGroupChanged,
        BaseDataManager.DataLoadingCallbacks, BaseDataManager.DataUpdatingCallbacks{

    @Bind(R.id.market_groups) RecyclerView recyclerView;
    @Bind(R.id.dataloading_progress) ProgressBar progressBar;
    @Bind(R.id.toolbar) Toolbar toolbar;

    private ProgressDialog progressDialog;
    private ActionBar actionBar;
    private DataManager dataManager;
    private MarketGroupsAdapter adapter;
    private LinearLayoutManager layoutManager;

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

        dataManager = new DataManager(this) {
            @Override
            public void onDataLoaded(List<? extends MarketItemBase> data) {
                adapter.updateCollection(data);
            }

            @Override
            public void onDataLoaded(Object data) {
                // never fired
            }
        };

        dataManager.registerCallback((DataLoadingSubject.DataLoadingCallbacks) this);
        dataManager.registerCallback((DataLoadingSubject.DataUpdatingCallbacks) this);

        adapter = new MarketGroupsAdapter(this, this);
        recyclerView.setAdapter(adapter);

        dataManager.updateAndLoad();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(new ComponentName(getApplication(), SearchActivity.class)));

        return true;
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

        adapter.setToClear(true);
        dataManager.loadMarketGroups(group.getId(), true);
        dataManager.loadMarketTypes(group.getId(), true);

        animateTitleChange();
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
        adapter.setToClear(true);

        if (!dataManager.isDataLoading()) {
            if (currentParent != null && currentParent.hasParent()) {
                long parentId = currentParent.getParentGroupId();

                currentParent = DataContracts.MarketGroupEntry.getMarketGroup(this, parentId);
                dataManager.loadMarketGroups(parentId, true);
                dataManager.loadMarketTypes(parentId, true);
            }
            else if (currentParent != null) {
                dataManager.loadMarketGroups(null, true);
                currentParent = null;
            }
            else {
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

    @Override
    public void dataUpdatingStarted() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating Market Cache...");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    @Override
    public void dataUpdatingFinished() {
        progressDialog.dismiss();
    }
}
