package com.w9jds.marketbot.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.w9jds.eveapi.Models.MarketItemBase;
import com.w9jds.marketbot.R;
import com.w9jds.marketbot.adapters.MarketGroupsAdapter;
import com.w9jds.marketbot.data.DataManager;

import java.util.Collection;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @Bind(R.id.market_groups)
    RecyclerView recyclerView;

    private DataManager dataManager;
    private MarketGroupsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

//        dataManager = new DataManager(this, filtersAdapter) {
//            @Override
//            public void onDataLoaded(List<? extends PlaidItem> data) {
//                adapter.addAndResort(data);
//                checkEmptyState();
//            }
//        };

        dataManager = new DataManager(this) {
            @Override
            public void onDataLoaded(List<? extends MarketItemBase> data) {
                adapter.addAndResort(data);
            }
        };

        adapter = new MarketGroupsAdapter(this, dataManager);
        recyclerView.setAdapter(adapter);

        dataManager.loadMarketGroups();
    }

}
