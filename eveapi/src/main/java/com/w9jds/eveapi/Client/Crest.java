package com.w9jds.eveapi.Client;

import android.os.AsyncTask;

import com.w9jds.eveapi.Callback;
import com.w9jds.eveapi.Models.MarketGroup;
import com.w9jds.eveapi.Models.MarketItemBase;
import com.w9jds.eveapi.Models.OrderType;
import com.w9jds.eveapi.Models.ServerInfo;
import com.w9jds.eveapi.Models.TypeInfo;
import com.w9jds.eveapi.Models.containers.MarketGroups;
import com.w9jds.eveapi.Models.Region;
import com.w9jds.eveapi.Models.containers.MarketOrders;
import com.w9jds.eveapi.Models.containers.Regions;
import com.w9jds.eveapi.Models.containers.Types;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Jeremy Shore on 2/16/16.
 */
public final class Crest {

    public static final String PUBLIC_SINGULARITY = "https://public-crest-sisi.testeveonline.com/";
    public static final String SINGULARITY = "https://api-sisi.testeveonline.com/";
    public static final String PUBLIC_TRANQUILITY = "https://public-crest.eveonline.com/";
    public static final String TRANQUILITY = "https://crest-tq.eveonline.com/";

    Endpoint crestEndpoint;

    public Crest (Retrofit retrofit) {
        this.crestEndpoint = new Builder()
                .buildEndpoint(retrofit);
    }

    public void setCrestEndpoint(Endpoint crestEndpoint) {
        this.crestEndpoint = crestEndpoint;
    }

    interface Endpoint {

        @GET("/")
        Call<ServerInfo> getServer();

        @GET
        Call<Types> getMarketTypes(@Url String url);

        @GET("/types/{typeId}/")
        Call<TypeInfo> getTypeInfo(@Path("typeId") long id);

        @GET("/market/groups/")
        Call<MarketGroups> getMarketGroups();

        @GET("/regions/")
        Call<Regions> getRegions();

        @GET("/market/{regionId}/orders/{orderType}/")
        Call<MarketOrders> getMarketOrders(@Path("regionId") long regionId, @Path("orderType") String orderType,
                                           @Query(value = "type", encoded = true) String typeId);

    }

    public void getServerVersion(final Callback<ServerInfo> callback) {
        Call<ServerInfo> call = crestEndpoint.getServer();
        call.enqueue(new retrofit2.Callback<ServerInfo>() {
            @Override
            public void onResponse(Call<ServerInfo> call, Response<ServerInfo> response) {
                callback.success(response.body());
            }

            @Override
            public void onFailure(Call<ServerInfo> call, Throwable t) {
                callback.failure(t.getMessage());
            }
        });
    }

    public void getRegions(final Callback<ArrayList<Region>> callback) {
        Call<Regions> call = crestEndpoint.getRegions();
        call.enqueue(new retrofit2.Callback<Regions>() {
            @Override
            public void onResponse(Call<Regions> call, Response<Regions> response) {
                callback.success(response.body().items);
            }

            @Override
            public void onFailure(Call<Regions> call, Throwable t) {
                callback.failure(t.getMessage());
            }
        });
    }

    public void getMarketGroups(final Callback<Hashtable<Long, MarketGroup>> callback) {
        Call<MarketGroups> call = crestEndpoint.getMarketGroups();
        call.enqueue(new retrofit2.Callback<MarketGroups>() {
            @Override
            public void onResponse(Call<MarketGroups> call, Response<MarketGroups> response) {

//                callback.success(response.body().groups);

                new buildMarketGroupTree(response.body().groups, callback).execute();
            }

            @Override
            public void onFailure(Call<MarketGroups> call, Throwable t) {
                callback.failure(t.getMessage());
            }
        });
    }

    public void getMarketTypes(String href, final Callback<Types> callback) {
        Call<Types> call = crestEndpoint.getMarketTypes(href);
        call.enqueue(new retrofit2.Callback<Types>() {
            @Override
            public void onResponse(Call<Types> call, Response<Types> response) {
                callback.success(response.body());
            }

            @Override
            public void onFailure(Call<Types> call, Throwable t) {
                callback.failure(t.getMessage());
            }
        });
    }

    public void getTypeInfo(long typeId, final Callback<TypeInfo> callback) {
        Call<TypeInfo> call = crestEndpoint.getTypeInfo(typeId);
        call.enqueue(new retrofit2.Callback<TypeInfo>() {
            @Override
            public void onResponse(Call<TypeInfo> call, Response<TypeInfo> response) {
                callback.success(response.body());
            }

            @Override
            public void onFailure(Call<TypeInfo> call, Throwable t) {
                callback.failure(t.getMessage());
            }
        });
    }

    public void getOrders(long regionId, String typeHref, OrderType orderType, final Callback<MarketOrders> callback) {
        Call<MarketOrders> call = crestEndpoint.getMarketOrders(regionId, orderType.toString(), typeHref);
        call.enqueue(new retrofit2.Callback<MarketOrders>() {
            @Override
            public void onResponse(Call<MarketOrders> call, Response<MarketOrders> response) {
                callback.success(response.body());
            }

            @Override
            public void onFailure(Call<MarketOrders> call, Throwable t) {
                callback.failure(t.getMessage());
            }
        });
    }

    private class buildMarketGroupTree extends AsyncTask<Void, Void, Hashtable<Long, MarketGroup>> {

        Hashtable<Long, MarketGroup> tree = new Hashtable<>();
        Hashtable<Long, MarketGroup> children = new Hashtable<>();
        final ArrayList<MarketGroup> groups;
        final Callback<Hashtable<Long, MarketGroup>> callback;

        public buildMarketGroupTree(ArrayList<MarketGroup> groups, Callback<Hashtable<Long, MarketGroup>> callback) {
            this.groups = groups;
            this.callback = callback;
        }

        @Override
        protected Hashtable<Long, MarketGroup> doInBackground(Void... params) {
            for (MarketGroup group : groups) {
                if (!group.hasParent()) {
                    tree.put(group.getId(), group);
                }
                else {
                    children.put(group.getId(), group);
                }
            }

            while (children.values().size() > 0) {
                for (Iterator<MarketGroup> iterator = children.values().iterator(); iterator.hasNext(); ) {
                    MarketGroup group = iterator.next();
                    if (children.containsKey(group.getParentGroupId())) {
                        children.get(group.getParentGroupId()).children.put(group.getId(), group);
                        iterator.remove();
                    }
                    else {
                        boolean found = bfsForParent(group, tree);

                        if (found) {
                            iterator.remove();
                        }
                    }
                }
            }

            return tree;
        }

        @Override
        protected void onPostExecute(Hashtable<Long, MarketGroup> tree) {
            callback.success(tree);
            super.onPostExecute(tree);
        }
    }

    private boolean bfsForParent(MarketItemBase base, Hashtable<Long, MarketGroup> tree) {
        boolean parentFound = false;
        MarketGroup kid = (MarketGroup) base;
        Queue queue = new LinkedList();

        for (MarketItemBase group : tree.values()) {
            if (parentFound) {
                break;
            }

            queue.add(group);

            while(!queue.isEmpty()) {
                MarketGroup node = (MarketGroup)queue.remove();
                if (node.getId() == kid.getParentGroupId()) {
                    node.children.put(base.getId(), (MarketGroup)base);
                    parentFound = true;
                    queue.clear();
                }
                else {
                    for (MarketGroup child : node.children.values()) {
                        queue.add(child);
                    }
                }
            }
        }

        return parentFound;
    }

    public static class Builder {

        public Crest build(Retrofit retrofit) {
            return new Crest(retrofit);
        }

        public Endpoint buildEndpoint(Retrofit retrofit) {
            return retrofit.create(Endpoint.class);
        }

    }
}