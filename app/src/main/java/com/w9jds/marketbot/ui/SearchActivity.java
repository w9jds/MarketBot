package com.w9jds.marketbot.ui;

import android.app.SearchManager;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.w9jds.marketbot.R;
import com.w9jds.marketbot.classes.models.MarketItemBase;
import com.w9jds.marketbot.data.loader.GroupsLoader;
import com.w9jds.marketbot.ui.adapters.MarketGroupsAdapter;
import com.w9jds.marketbot.data.DataLoadingSubject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity implements DataLoadingSubject.DataLoadingCallbacks {

    @Bind(R.id.market_groups) RecyclerView recyclerView;
    @Bind(R.id.dataloading_progress) ProgressBar progressBar;
    @Bind(R.id.toolbar) Toolbar toolbar;

    private ActionBar actionBar;
    private GroupsLoader searchLoader;
    private MarketGroupsAdapter adapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new MarketGroupsAdapter(this);
        recyclerView.setAdapter(adapter);

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void createDataManager() {

        searchLoader = new GroupsLoader(this) {
            @Override
            public void onProgressUpdate(int page, int totalPages, String message) {

            }

            @Override
            public void onDataLoaded(List<? extends MarketItemBase> data) {
                adapter.updateCollection(data);
            }
        };

        searchLoader.registerLoadingCallback(this);
    }

    private void handleIntent(Intent intent) {

        if (searchLoader == null) {
            createDataManager();
        }

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            actionBar.setTitle(query);
            searchLoader.searchMarketTypes(query);
        }
    }

    @Override
    public void dataStartedLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void dataFinishedLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void dataFailedLoading(String errorMessage) {
//        Snackbar.make(baseView, errorMessage, Snackbar.LENGTH_LONG).show();
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

}
