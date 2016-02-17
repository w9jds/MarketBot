package com.w9jds.eveapi.Client;

import com.google.gson.Gson;
import com.w9jds.eveapi.Callback;
import com.w9jds.eveapi.Client.Converters.StringConverter;
import com.w9jds.eveapi.Models.MarketGroup;
import com.w9jds.eveapi.Models.Region;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.QueryMap;

/**
 * Created by Jeremy Shore on 2/16/16.
 */
public final class Crest {

    public static final String PUBLIC_SINGULARITY = "https://public-crest-sisi.testeveonline.com/";
    public static final String SINGULARITY = "https://api-sisi.testeveonline.com/";
    public static final String PUBLIC_TRANQUILITY = "https://public-crest.eveonline.com/";
    public static final String TRANQUILITY = "https://crest-tq.eveonline.com/";

    Endpoint crestEndpoint = new Builder()
            .setTranquilityEndpoint()
            .buildEndpoint();

    public void setCrestEndpoint(Endpoint crestEndpoint) {
        this.crestEndpoint = crestEndpoint;
    }

    interface Endpoint {
        @GET("/{path}")
        void getCrest(@Path("path") String path, retrofit.Callback<?> callback);

        @GET("/{type}/{path}")
        void getCrest(@Path("type") String type, @Path("path") String path, retrofit.Callback<?> callback);

        @GET("/{path}")
        void getCrest(@Path("path") String path, @QueryMap HashMap<String, String> params,
                      retrofit.Callback<?> callback);

        @GET("/{path}/{id}/")
        void getCrest(@Path("path") String path, @Path("id") int id, retrofit.Callback<?> callback);

        @GET("/{path}/{id}/")
        void getCrest(@Path("path") String path, @Path("id") int id, @QueryMap HashMap<String, String> params,
                      retrofit.Callback<?> callback);
    }

    public void getRegions(final Callback<ArrayList<Region>> callback) {
        crestEndpoint.getCrest("regions", new retrofit.Callback<String>() {
            @Override
            public void success(String json, Response response) {
                Region[] regions = new Gson().fromJson(json, Region[].class);

                callback.success((ArrayList<Region>)Arrays.asList(regions));
            }

            @Override
            public void failure(RetrofitError error) {
                callback.failure(error.getMessage());
            }
        });
    }

    public void getMarketGroups(final Callback<Hashtable<Integer, MarketGroup>> callback) {
        crestEndpoint.getCrest("market/groups", new retrofit.Callback<String>() {
            @Override
            public void success(String json, Response response) {
                Hashtable<Integer, MarketGroup> tree = new Hashtable<>();
                MarketGroup[] groups = new Gson().fromJson(json, MarketGroup[].class);

                for (MarketGroup group : groups) {
                    if (!group.hasParent()) {
                        tree.put(group.getId(), group);
                    }
                }

                for (MarketGroup group : groups) {
                    if (group.hasParent()) {
                        Integer parentId = group.getParentGroupId();

                        if (parentId != null) {
                            tree.get(parentId).children.put(group.getId(), group);
                        }
                    }
                }

                callback.success(tree);
            }

            @Override
            public void failure(RetrofitError error) {
                callback.failure(error.getMessage());
            }
        });
    }

    public static class Builder {

        private String basePath;

        public Builder setSingularityEndpoint() {
            this.basePath = SINGULARITY;
            return this;
        }

        public Builder setPublicSingularityEndpoint() {
            this.basePath = PUBLIC_SINGULARITY;
            return this;
        }

        public Builder setPublicTranquilityEndpoint() {
            this.basePath = PUBLIC_TRANQUILITY;
            return this;
        }

        public Builder setTranquilityEndpoint() {
            this.basePath = TRANQUILITY;
            return this;
        }

        public Crest build() {
            Crest crest = new Crest();
            crest.setCrestEndpoint(buildEndpoint());
            return crest;
        }

        public Endpoint buildEndpoint() {
            return new RestAdapter.Builder()
                    .setConverter(new StringConverter())
                    .setEndpoint(basePath)
                    .build()
                    .create(Endpoint.class);
        }
    }
}