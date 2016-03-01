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
        getPublicCrestApi().getMarketGroups(new Callback<Hashtable<Integer, MarketGroup>>() {

            @Override
            public void success(Hashtable<Integer, MarketGroup> groups) {
                loadFinished();
                if (groups != null) {
                    onDataLoaded(new ArrayList<>(groups.values()));
                }
            }

            @Override
            public void failure(String error) {
                loadFinished();
            }
        });
    }

    public void loadGroupTypes(String targetLocation) {
        getPublicCrestApi().getMarketTypes(targetLocation, new Callback<Types>() {
            @Override
            public void success(Types types) {
                loadFinished();
                if (types != null) {
                    onDataLoaded(types.items);
                }
            }

            @Override
            public void failure(String error) {
                loadFinished();
            }
        });
    }
}
