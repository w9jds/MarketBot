package com.w9jds.marketbot.data;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.w9jds.eveapi.Callback;
import com.w9jds.eveapi.Client.Crest;
import com.w9jds.eveapi.Models.MarketGroup;
import com.w9jds.eveapi.Models.OrderType;
import com.w9jds.eveapi.Models.Region;
import com.w9jds.eveapi.Models.ServerInfo;
import com.w9jds.eveapi.Models.Type;
import com.w9jds.eveapi.Models.TypeInfo;
import com.w9jds.eveapi.Models.containers.MarketOrders;
import com.w9jds.eveapi.Models.containers.Types;
import com.w9jds.marketbot.classes.MarketBot;
import com.w9jds.marketbot.data.storage.DataContracts;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.inject.Inject;
import javax.inject.Named;

import retrofit2.Retrofit;

/**
 * Created by Jeremy Shore on 2/19/16.
 */
public abstract class DataManager extends BaseDataManager {

    @Inject
    Retrofit retrofit;
    @Inject @Named("read")
    SQLiteDatabase readDatabase;
    @Inject @Named("write")
    SQLiteDatabase writeDatabase;
    @Inject
    SharedPreferences sharedPreferences;
    @Inject
    String serverVersion;

    Context context;
    Crest crest;

    public DataManager(Application application) {
        super();
        ((MarketBot)application).getStorageComponent().inject(this);

        this.context = application;
        crest = new Crest(retrofit);
    }

    private boolean isConnected() {
        ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public void loadMarketGroups() {
        if (isConnected()) {
            crest.getServerVersion(new Callback<ServerInfo>() {
                @Override
                public void success(ServerInfo serverInfo) {
                    if (serverInfo.getServerVersion().equals(serverVersion)) {
                        loadMarketGroups(null);
                    }
                    else {
                        sharedPreferences.edit()
                                .putString("serverVersion", serverInfo.getServerVersion())
                                .apply();

                        updateMarketGroups();
                    }
                }

                @Override
                public void failure(String error) {

                }
            });
        } else {
            loadMarketGroups(null);
        }
    }

    public void loadMarketGroups(Long parentId) {
//        onDataLoaded();

        loadFinished();
    }

    private void updateMarketGroups() {
        loadStarted();
        crest.getMarketGroups(new Callback<Hashtable<Long, MarketGroup>>() {

            @Override
            public void success(Hashtable<Long, MarketGroup> groups) {
                DataContracts.MarketGroupEntry.createNewMarketGroups(writeDatabase, groups.values());

                loadMarketGroups(null);
            }

            @Override
            public void failure(String error) {
                // failed to update marketgroups
                loadFinished();
            }
        });
    }

    public void loadGroupTypes(String targetLocation) {
        loadStarted();
        crest.getMarketTypes(targetLocation, new Callback<Types>() {
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

    public void loadTypeInfo(long typeId) {
        loadStarted();
        crest.getTypeInfo(typeId, new Callback<TypeInfo>() {
            @Override
            public void success(TypeInfo typeInfo) {
                if (typeInfo != null) {
                    onDataLoaded(typeInfo);
                }

                loadFinished();
            }

            @Override
            public void failure(String error) {
                loadFinished();
            }
        });
    }

    public void loadRegions() {
        loadStarted();
        crest.getRegions(new Callback<ArrayList<Region>>() {
            @Override
            public void success(ArrayList<Region> regions) {
                if (regions != null) {
                    onDataLoaded(regions);
                }

                loadFinished();
            }

            @Override
            public void failure(String error) {
                loadFinished();
            }
        });
    }

    public void loadSellOrders(Region region, Type type) {
        loadStarted();
        crest.getOrders(region.getId(), type.getHref(), OrderType.sell, new Callback<MarketOrders>() {
            @Override
            public void success(MarketOrders marketOrders) {
                if (marketOrders != null) {
                    onDataLoaded(marketOrders.orders);
                }

                loadFinished();
            }

            @Override
            public void failure(String error) {
                loadFinished();
            }
        });
    }

    public void loadBuyOrders(Region region, Type type) {
        loadStarted();
        crest.getOrders(region.getId(), type.getHref(), OrderType.buy, new Callback<MarketOrders>() {
            @Override
            public void success(MarketOrders marketOrders) {
                if (marketOrders != null) {
                    onDataLoaded(marketOrders.orders);
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
