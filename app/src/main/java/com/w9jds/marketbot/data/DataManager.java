package com.w9jds.marketbot.data;

import android.content.Context;

import com.w9jds.eveapi.Callback;
import com.w9jds.eveapi.Models.MarketGroup;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by Jeremy Shore on 2/19/16.
 */
public abstract class DataManager extends BaseDataManager {

    public DataManager(Context context) {
        super(context);
//        setupPageIndexes();
    }

    public void loadMarketGroups() {
        getPublicCrestApi().getMarketGroups(new Callback<Hashtable<Integer, MarketGroup>>() {

            @Override
            public void success(Hashtable<Integer, MarketGroup> groups) {
                loadFinished();
                if (groups != null) {
                    onDataLoaded(new ArrayList<MarketGroup>(groups.values()));
                }
            }

            @Override
            public void failure(String error) {
                loadFinished();
            }
        });
    }
}
