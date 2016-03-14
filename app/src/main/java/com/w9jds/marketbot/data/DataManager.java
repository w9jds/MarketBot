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

    @Inject @Named("public_traq")
    Crest publicCrest;
    @Inject
    SharedPreferences sharedPreferences;
    @Inject
    String serverVersion;

    Context context;

    public DataManager(Application application) {
        super();
        ((MarketBot)application).getStorageComponent().inject(this);

        this.context = application;
    }

    private boolean isConnected() {
        ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public void loadMarketGroups() {
        loadStarted();
        incrementLoadingCount();

        if (isConnected()) {
            publicCrest.getServerVersion(new Callback<ServerInfo>() {
                @Override
                public void success(ServerInfo serverInfo) {
                    if (serverInfo.getServerVersion().equals(serverVersion)) {
                        loadMarketGroups(null, false);
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
            loadMarketGroups(null, false);
        }
    }

    public void loadMarketGroups(Long parentId, boolean isDirectCall) {
        if (isDirectCall) {
            loadStarted();
        }

        onDataLoaded(DataContracts.MarketGroupEntry.getMarketGroupsforParent(context, parentId));
        decrementLoadingCount();
        loadFinished();
    }

    private void updateMarketGroups() {
        publicCrest.getMarketGroups(new Callback<ArrayList<MarketGroup>>() {

            @Override
            public void success(ArrayList<MarketGroup> groups) {
                DataContracts.MarketGroupEntry.createNewMarketGroups(context, groups);

                loadMarketGroups(null, false);
            }

            @Override
            public void failure(String error) {
                // failed to update marketgroups
                decrementLoadingCount();
                loadFinished();
            }
        });
    }

    public void loadGroupTypes(String targetLocation, long groupId) {
        loadStarted();
        incrementLoadingCount();

        publicCrest.getMarketTypes(targetLocation, groupId, new Callback<ArrayList<Type>>() {
            @Override
            public void success(ArrayList<Type> types) {
                if (types != null) {
                    onDataLoaded(types);
                }

                decrementLoadingCount();
                loadFinished();
            }

            @Override
            public void failure(String error) {
                decrementLoadingCount();
                loadFinished();
            }
        });
    }

    public void loadTypeInfo(long typeId) {
        loadStarted();
        incrementLoadingCount();
        publicCrest.getTypeInfo(typeId, new Callback<TypeInfo>() {
            @Override
            public void success(TypeInfo typeInfo) {
                if (typeInfo != null) {
                    onDataLoaded(typeInfo);
                }

                decrementLoadingCount();
                loadFinished();
            }

            @Override
            public void failure(String error) {
                decrementLoadingCount();
                loadFinished();
            }
        });
    }

    public void loadRegions() {
        loadStarted();
        incrementLoadingCount();
        publicCrest.getRegions(new Callback<ArrayList<Region>>() {
            @Override
            public void success(ArrayList<Region> regions) {
                if (regions != null) {
                    onDataLoaded(regions);
                }

                decrementLoadingCount();
                loadFinished();
            }

            @Override
            public void failure(String error) {
                decrementLoadingCount();
                loadFinished();
            }
        });
    }

    public void loadSellOrders(Region region, Type type) {
        loadStarted();
        incrementLoadingCount();

        publicCrest.getOrders(region.getId(), type.getHref(), OrderType.sell, new Callback<MarketOrders>() {
            @Override
            public void success(MarketOrders marketOrders) {
                if (marketOrders != null) {
                    onDataLoaded(marketOrders.orders);
                }

                decrementLoadingCount();
                loadFinished();
            }

            @Override
            public void failure(String error) {
                decrementLoadingCount();
                loadFinished();
            }
        });
    }

    public void loadBuyOrders(Region region, Type type) {
        loadStarted();
        incrementLoadingCount();

        publicCrest.getOrders(region.getId(), type.getHref(), OrderType.buy, new Callback<MarketOrders>() {
            @Override
            public void success(MarketOrders marketOrders) {
                if (marketOrders != null) {
                    onDataLoaded(marketOrders.orders);
                }

                decrementLoadingCount();
                loadFinished();
            }

            @Override
            public void failure(String error) {
                decrementLoadingCount();
                loadFinished();
            }
        });
    }
}
