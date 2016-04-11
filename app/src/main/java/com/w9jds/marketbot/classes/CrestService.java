package com.w9jds.marketbot.classes;

import core.eve.crest.CrestDictionary;
import core.eve.crest.CrestItem;
import core.eve.crest.CrestMarketGroup;
import core.eve.crest.CrestServerStatus;
import core.eve.crest.CrestType;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

public interface CrestService {

    @GET("/")
    Observable<Response<CrestServerStatus>> getServer();

//    @GET("/market/types/")
//    Observable<Response<CrestDictionary<>>> getMarketTypes();
//
//    @GET
//    Observable<Response<CrestDictionary<>>> getMarketTypes(@Url String url);

    @GET("/types/{typeId}/")
    Observable<Response<CrestType>> getTypeInfo(@Path("typeId") long id);

    @GET("/market/groups/")
    Observable<Response<CrestDictionary<CrestMarketGroup>>> getMarketGroups();

    @GET("/regions/")
    Observable<Response<CrestDictionary<CrestItem>>> getRegions();

//    @GET("/market/{regionId}/orders/{orderType}/")
//    Observable<Response<CrestDictionary<CrestOrder>>> getMarketOrders(@Path("regionId") long regionId,
//                                                       @Path("orderType") String orderType,
//                                                       @Query(value = "type", encoded = true) String typeId);

}