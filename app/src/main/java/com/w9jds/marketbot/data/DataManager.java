package com.w9jds.marketbot.data;

import android.content.Context;

import com.w9jds.eveapi.Callback;
import com.w9jds.eveapi.Models.MarketGroup;
import com.w9jds.eveapi.Models.OrderType;
import com.w9jds.eveapi.Models.Region;
import com.w9jds.eveapi.Models.Type;
import com.w9jds.eveapi.Models.TypeInfo;
import com.w9jds.eveapi.Models.containers.MarketOrders;
import com.w9jds.eveapi.Models.containers.Types;

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
        getPublicCrestApi().getMarketGroups(new Callback<Hashtable<Long, MarketGroup>>() {

            @Override
            public void success(Hashtable<Long, MarketGroup> groups) {
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

    public void loadTypeInfo(long typeId) {
        loadStarted();
        getPublicCrestApi().getTypeInfo(typeId, new Callback<TypeInfo>() {
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
        getPublicCrestApi().getRegions(new Callback<ArrayList<Region>>() {
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
        getPublicCrestApi().getOrders(region.getId(), type.getHref(), OrderType.sell, new Callback<MarketOrders>() {
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
        getPublicCrestApi().getOrders(region.getId(), type.getHref(), OrderType.buy, new Callback<MarketOrders>() {
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
