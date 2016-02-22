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
import com.w9jds.marketbot.data.DataManager;

import java.util.Collection;
import java.util.Hashtable;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @Bind(R.id.market_groups)
    RecyclerView mRecyclerView;

    private DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        dataManager = new DataManager(this, ) {
            @Override
            public void onDataLoaded(Collection<?> data) {

            }
        }
    }

}
