package com.w9jds.marketbot.data;

import android.content.Context;

import com.w9jds.eveapi.Callback;
import com.w9jds.eveapi.Models.MarketGroup;
import com.w9jds.eveapi.Models.Types;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by Jeremy Shore on 2/19/16.
 */
public abstract class DataManager extends BaseDataManager {

    public DataManager(Context context) {
        super(context);

    }

    public void loadMarketGroups() {
        loadStarted();
        getPublicCrestApi().getMarketGroups(new Callback<Hashtable<Integer, MarketGroup>>() {

            @Override
            public void success(Hashtable<Integer, MarketGroup> groups) {
                if (groups != null) {
                    onDataLoaded(new ArrayList<>(groups.values()));
                }

                loadFinished();
            }

            @Override
            public void failure(String error) {
                loadFinished();
            }
        });
    }

    public void loadGroupTypes(String targetLocation) {
        loadStarted();
        getPublicCrestApi().getMarketTypes(targetLocation, new Callback<Types>() {
            @Override
            public void success(Types types) {
                if (types != null) {
                    onDataLoaded(types.items);
                }

                loadFinished();
            }

            @Override
            public void failure(String error) {
                loadFinished();
            }
        });
    }
}
