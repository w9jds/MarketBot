package com.w9jds.eveapi.Client;

import android.os.AsyncTask;

import com.w9jds.eveapi.Callback;
import com.w9jds.eveapi.Models.MarketGroup;
import com.w9jds.eveapi.Models.MarketItemBase;
import com.w9jds.eveapi.Models.OrderType;
import com.w9jds.eveapi.Models.ServerInfo;
import com.w9jds.eveapi.Models.Type;
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

        @GET("/market/types/")
        Call<Types> getAllMarketTypes();

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

    public void getMarketGroups(final Callback<ArrayList<MarketGroup>> callback) {
        Call<MarketGroups> call = crestEndpoint.getMarketGroups();
        call.enqueue(new retrofit2.Callback<MarketGroups>() {
            @Override
            public void onResponse(Call<MarketGroups> call, Response<MarketGroups> response) {
                callback.success(response.body().groups);
            }

            @Override
            public void onFailure(Call<MarketGroups> call, Throwable t) {
                callback.failure(t.getMessage());
            }
        });
    }

    public void getAllMarketTypes(final Callback<Types> callback) {
        Call<Types> call = crestEndpoint.getAllMarketTypes();
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

    public static class Builder {

        public Crest build(Retrofit retrofit) {
            return new Crest(retrofit);
        }

        public Endpoint buildEndpoint(Retrofit retrofit) {
            return retrofit.create(Endpoint.class);
        }

    }
}