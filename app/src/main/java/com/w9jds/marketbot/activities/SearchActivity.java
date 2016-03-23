package com.w9jds.marketbot.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ProgressBar;

import com.w9jds.eveapi.Models.MarketItemBase;
import com.w9jds.marketbot.R;
import com.w9jds.marketbot.adapters.MarketGroupsAdapter;
import com.w9jds.marketbot.data.DataManager;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity {

    @Bind(R.id.market_groups) RecyclerView recyclerView;
    @Bind(R.id.dataloading_progress) ProgressBar progressBar;
    @Bind(R.id.toolbar) Toolbar toolbar;

    private DataManager dataManager;
    private MarketGroupsAdapter adapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar();

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

        dataManager = new DataManager(getApplication()) {
            @Override
            public void onDataLoaded(List<? extends MarketItemBase> data) {
                adapter.updateCollection(data);
            }

            @Override
            public void onDataLoaded(Object data) {
                // never fired
            }
        };

    }

    private void handleIntent(Intent intent) {

        if (dataManager == null) {
            createDataManager();
        }

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            dataManager.searchMarketTypes(query);
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.main_menu, menu);
//
//        // Associate searchable configuration with the SearchView
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//
//        return true;
//    }
}
