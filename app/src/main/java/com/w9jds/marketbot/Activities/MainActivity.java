package com.w9jds.marketbot.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.w9jds.eveapi.Callback;
import com.w9jds.eveapi.Client.Crest;
import com.w9jds.eveapi.Models.MarketGroup;
import com.w9jds.marketbot.R;

import java.util.Hashtable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
