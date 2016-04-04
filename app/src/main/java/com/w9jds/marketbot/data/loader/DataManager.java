package com.w9jds.marketbot.data.loader;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.ContactsContract;

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
import com.w9jds.marketbot.data.BaseDataManager;
import com.w9jds.marketbot.data.Database;
import com.w9jds.marketbot.data.storage.MarketGroupEntry;
import com.w9jds.marketbot.data.storage.MarketTypeEntry;
import com.w9jds.marketbot.data.storage.RegionEntry;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Jeremy Shore on 2/19/16.
 */
public abstract class DataManager extends BaseDataManager {

    @Inject Crest publicCrest;
    @Inject SharedPreferences sharedPreferences;
    @Inject boolean isFirstRun;
    @Inject String serverVersion;

    private Context context;
    private boolean updateFailed = false;
    private SQLiteDatabase updateDatabase;

    public DataManager(Context context) {
        super();

        MarketBot.createNewStorageSession().inject(this);
        this.context = context;
    }

    public abstract void onProgressUpdate(int page, int totalPages);

    private boolean isConnected() {
        ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    protected void updateFinished(SQLiteDatabase database) {
        super.updateFinished(database);

        if (!updateFailed && isFirstRun && updatingCount() == 0) {
            sharedPreferences.edit().putBoolean("isFirstRun", false).apply();
            database.setTransactionSuccessful();
            database.endTransaction();
            database.close();

            loadMarketGroups(null, false);
        }
    }

    public void updateAndLoad() {
        loadStarted();
        incrementLoadingCount();

        if (isConnected()) {
            publicCrest.getServerVersion(new Callback<ServerInfo>() {
                @Override
                public void success(ServerInfo serverInfo) {
                    if (serverInfo.getServerVersion().equals(serverVersion) && !isFirstRun) {
                        loadMarketGroups(null, false);
                    }
                    else {
                        resetLoadingCount();
                        loadFinished();

                        sharedPreferences.edit()
                                .putString("serverVersion", serverInfo.getServerVersion())
                                .apply();

                        updateStarted();
                        incrementUpdatingCount(3);

                        updateDatabase = Database.getInstance(context).getWritableDatabase();
                        updateDatabase.beginTransaction();

                        updateMarketGroups();
                        updateMarketTypes();
                        updateRegions();
                    }
                }

                @Override
                public void failure(String error) {

                    updateFailed = true;
                    loadFailed("Connection timed out, CREST Might be down.");
                    loadMarketGroups(null, false);
                }
            });
        }
        else {
            loadMarketGroups(null, false);
        }
    }



    private void updateMarketGroups() {
        publicCrest.getMarketGroups(new Callback<ArrayList<MarketGroup>>() {

            @Override
            public void success(ArrayList<MarketGroup> groups) {
                createNewMarketGroups(groups);

                decrementUpdatingCount();
                updateFinished(updateDatabase);
            }

            @Override
            public void failure(String error) {
                updateFailed = true;
                loadFailed("Failed to update cached market groups.");
                decrementUpdatingCount();
                updateFinished(updateDatabase);
            }
        });
    }

    public void createNewMarketGroups(Collection<MarketGroup> groups) {
        for (MarketGroup group : groups) {
            ContentValues thisItem = new ContentValues();
            thisItem.put(MarketGroupEntry._ID, group.getId());
            thisItem.put(MarketGroupEntry.COLUMN_NAME, group.getName());
            thisItem.put(MarketGroupEntry.COLUMN_HREF, group.getHref());
            thisItem.put(MarketGroupEntry.COLUMN_DESCRIPTION, group.getDescription());
            if (group.hasParent()) {
                thisItem.put(MarketGroupEntry.COLUMN_PARENT_ID, group.getParentGroupId());
            }
            thisItem.put(MarketGroupEntry.COLUMN_TYPES_LOCATION, group.getTypesLocation());

            updateDatabase.insertWithOnConflict(MarketGroupEntry.TABLE_NAME, null, thisItem,
                    SQLiteDatabase.CONFLICT_REPLACE);
        }
    }

    public void createNewMarketTypes(Collection<Type> types) {
        for (Type type : types) {
            ContentValues thisItem = new ContentValues();
            thisItem.put(MarketTypeEntry._ID, type.getId());
            thisItem.put(MarketTypeEntry.COLUMN_GROUP_ID, type.marketGroup.getId());
            thisItem.put(MarketTypeEntry.COLUMN_NAME, type.getName());
            thisItem.put(MarketTypeEntry.COLUMN_HREF, type.getHref());
            thisItem.put(MarketTypeEntry.COLUMN_ICON_LOC, type.getIconLink());

            updateDatabase.insertWithOnConflict(MarketTypeEntry.TABLE_NAME, null, thisItem,
                    SQLiteDatabase.CONFLICT_REPLACE);
        }
    }

    public void createNewRegions(ArrayList<Region> regions) {
        for (Region region : regions) {
            ContentValues thisItem = new ContentValues();
            thisItem.put(RegionEntry._ID, region.getId());
            thisItem.put(RegionEntry.COLUMN_NAME, region.getName());
            thisItem.put(RegionEntry.COLUMN_HREF, region.getHref());

            updateDatabase.insertWithOnConflict(RegionEntry.TABLE_NAME, null,
                    thisItem, SQLiteDatabase.CONFLICT_REPLACE);
        }
    }

    private Callback<Types> marketTypeCallback = new Callback<Types>() {
        @Override
        public void success(Types types) {
            createNewMarketTypes(types.items);

            if (types.next != null && !types.next.href.equals("")) {
                Uri uri = Uri.parse(types.next.href);
                onProgressUpdate(Integer.parseInt(uri.getQueryParameter("page")), types.pageCount);

                loadNextPageTypes(types.next.href);
            }
            else {
                decrementUpdatingCount();
                updateFinished(updateDatabase);
            }
        }

        @Override
        public void failure(String error) {
            updateFailed = true;
            loadFailed("Failed to update cached market types.");
            decrementUpdatingCount();
            updateFinished(updateDatabase);
        }
    };

    private void updateMarketTypes() {
        publicCrest.getAllMarketTypes(marketTypeCallback);
    }

    public void loadNextPageTypes(String targetLocation) {
        publicCrest.getMarketTypes(targetLocation,  marketTypeCallback);
    }

    private void updateRegions() {
        publicCrest.getRegions(new Callback<ArrayList<Region>>() {
            @Override
            public void success(ArrayList<Region> regions) {
                createNewRegions(regions);

                decrementUpdatingCount();
                updateFinished(updateDatabase);
            }

            @Override
            public void failure(String error) {
                updateFailed = true;
                loadFailed("Failed to update cached regions.");
                decrementUpdatingCount();
                loadFinished();
            }
        });
    }

    public void loadRegions(boolean includesWormholes) {
        onDataLoaded(RegionEntry.getRegions(context, includesWormholes));

        decrementLoadingCount();
        loadFinished();
    }

    public void loadMarketGroups(Long parentId, boolean isDirectCall) {
        if (isDirectCall) {
            loadStarted();
            incrementLoadingCount();
        }

        onDataLoaded(MarketGroupEntry.getMarketGroupsforParent(context, parentId));
        decrementLoadingCount();
        loadFinished();
    }

    public void loadMarketTypes(long groupId, boolean isDirectCall) {
        if (isDirectCall) {
            loadStarted();
            incrementLoadingCount();
        }

        onDataLoaded(MarketTypeEntry.getMarketTypes(context, groupId));

        decrementLoadingCount();
        loadFinished();
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

        onDataLoaded(MarketTypeEntry.searchMarketTypes(context, queryString));

        decrementLoadingCount();
        loadFinished();
    }
}
