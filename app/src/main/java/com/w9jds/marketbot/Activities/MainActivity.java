package com.w9jds.marketbot.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.w9jds.eveapi.Callback;
import com.w9jds.eveapi.Client.Crest;
import com.w9jds.eveapi.Models.MarketGroup;
import com.w9jds.marketbot.R;

import java.util.Hashtable;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @Bind(R.id.market_groups)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        populateMarketGroups();
    }

    private void populateMarketGroups() {
        Crest crestService = new Crest.Builder()
                .setPublicTranquilityEndpoint()
                .build();

        crestService.getMarketGroups(new Callback<Hashtable<Integer, MarketGroup>>() {
            @Override
            public void success(Hashtable<Integer, MarketGroup> integerMarketGroupHashtable) {

            }

            @Override
            public void failure(String error) {

            }
        });
    }
}
