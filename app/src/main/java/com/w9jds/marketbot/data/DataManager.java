package com.w9jds.marketbot.data;

import android.content.Context;
import android.content.SharedPreferences;
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

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Jeremy Shore on 2/19/16.
 */
public abstract class DataManager extends BaseDataManager {

    @Inject Crest publicCrest;
    @Inject SharedPreferences sharedPreferences;
    @Inject String serverVersion;

    Context context;

    public DataManager(Context context) {
        super();

        MarketBot.createNewStorageSession().inject(this);
        this.context = context;
    }

    private boolean isConnected() {
        ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public void updateAndLoad() {
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
                        resetLoadingCount();
                        loadFinished();

                        sharedPreferences.edit()
                                .putString("serverVersion", serverInfo.getServerVersion())
                                .apply();

                        updateStarted();
                        incrementUpdatingCount(2);

                        updateMarketGroups();
                        updateMarketTypes();
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
            incrementLoadingCount();
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

                decrementUpdatingCount();
                updateFinished();
            }

            @Override
            public void failure(String error) {
                // failed to update marketgroups
                loadMarketGroups(null, false);

                decrementUpdatingCount();
                updateFinished();
            }
        });
    }

    private void updateMarketTypes() {
        incrementLoadingCount();

        publicCrest.getAllMarketTypes(new Callback<Types>() {
            @Override
            public void success(Types types) {
                DataContracts.MarketTypeEntry.createNewMarketTypes(context, types.items);

                if (types.next != null && !types.next.href.equals("")) {
                    loadNextPageTypes(types.next.href);
                }
                else {
                    decrementUpdatingCount();
                    updateFinished();
                }
            }

            @Override
            public void failure(String error) {
                // failed to update types

                decrementUpdatingCount();
                updateFinished();
            }
        });
    }

    public void loadMarketTypes(long groupId, boolean isDirectCall) {
        if (isDirectCall) {
            loadStarted();
            incrementLoadingCount();
        }

        onDataLoaded(DataContracts.MarketTypeEntry.getMarketTypes(context, groupId));

        decrementLoadingCount();
        loadFinished();
    }

    public void loadNextPageTypes(String targetLocation) {
        publicCrest.getMarketTypes(targetLocation,  new Callback<Types>() {
            @Override
            public void success(Types types) {
                DataContracts.MarketTypeEntry.createNewMarketTypes(context, types.items);

                if (types.next != null && !types.next.href.equals("")) {
                    loadNextPageTypes(types.next.href);
                }
                else {
                    decrementUpdatingCount();
                    updateFinished();
                }
            }

            @Override
            public void failure(String error) {
                //  update types failed
                decrementUpdatingCount();
                updateFinished();
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

    public void searchMarketTypes(String queryString) {
        loadStarted();
        incrementLoadingCount();

        onDataLoaded(DataContracts.MarketTypeEntry.searchMarketTypes(context, queryString));

        decrementLoadingCount();
        loadFinished();
    }
}
