package com.w9jds.eveapi.Client;

import com.w9jds.eveapi.Callback;
import com.w9jds.eveapi.Models.MarketGroup;
import com.w9jds.eveapi.Models.MarketGroups;
import com.w9jds.eveapi.Models.Region;
import com.w9jds.eveapi.Models.Types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Url;

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

        @GET
        Call<Types> getMarketTypes(@Url String url);

        @GET("/market/groups/")
        Call<MarketGroups> getMarketGroups();

        @GET("/regions/")
        Call<Region[]> getRegions();

    }

//    public void getRegions(final Callback<ArrayList<Region>> callback) {
//        Call<Region[]> call = crestEndpoint.getRegions();
//        call.enqueue(new retrofit2.Callback<Region[]>() {
//            @Override
//            public void onResponse(Call<Region[]> call, Response<Region[]> response) {
//                callback.success((ArrayList<Region>) Arrays.asList(response.body()));
//            }
//
//            @Override
//            public void onFailure(Call<Region[]> call, Throwable t) {
//                callback.failure(t.getMessage());
//            }
//        });
//    }

    public void getMarketGroups(final Callback<Hashtable<Integer, MarketGroup>> callback) {
        Call<MarketGroups> call = crestEndpoint.getMarketGroups();
        call.enqueue(new retrofit2.Callback<MarketGroups>() {
            @Override
            public void onResponse(Call<MarketGroups> call, Response<MarketGroups> response) {
                Hashtable<Integer, MarketGroup> tree = new Hashtable<>();

                for (MarketGroup group : response.body().groups) {
                    tree.put(group.getId(), group);
                }

                for (int i = response.body().groups.size() - 1; i > 0; i--) {
                    MarketGroup group = response.body().groups.get(i);

                    if (group.hasParent()) {
                        Integer parentId = group.getParentGroupId();
                        if (tree.containsKey(parentId)) {
                            tree.get(parentId).children.put(group.getId(), group);
                        }

                        tree.remove(group.getId());
                    }
                }


                callback.success(tree);
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
            return new Retrofit.Builder()
                    .baseUrl(basePath)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(Endpoint.class);
        }
    }
}