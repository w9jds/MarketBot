package com.w9jds.marketbot.classes;

import org.devfleet.crest.model.CrestDictionary;
import org.devfleet.crest.model.CrestItem;
import org.devfleet.crest.model.CrestMarketGroup;
import org.devfleet.crest.model.CrestMarketHistory;
import org.devfleet.crest.model.CrestMarketOrder;
import org.devfleet.crest.model.CrestMarketType;
import org.devfleet.crest.model.CrestServerStatus;
import org.devfleet.crest.model.CrestType;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface CrestService {

    @GET("/")
    Observable<Response<CrestServerStatus>> getServer();

    @GET("/market/types/")
    Call<CrestDictionary<CrestMarketType>> getMarketTypes(@Query("page") int page);

    @GET("/inventory/types/{typeId}/")
    Call<CrestType> getTypeInfo(@Path("typeId") long id);

    @GET("/market/groups/")
    Observable<Response<CrestDictionary<CrestMarketGroup>>> getMarketGroups();

    @GET("/regions/")
    Observable<Response<CrestDictionary<CrestItem>>> getRegions();

    @GET("/market/{regionId}/orders/{orderType}/")
    Observable<Response<CrestDictionary<CrestMarketOrder>>> getMarketOrders(@Path("regionId") long regionId,
                                                                            @Path("orderType") String orderType,
                                                                            @Query(value = "type", encoded = true) String typeId);

    @GET("/market/{regionId}/orders/")
    Observable<Response<CrestDictionary<CrestMarketOrder>>> getMarketOrders(@Path("regionId") long regionId,
                                                                            @Query(value = "type", encoded = true) String typeId);

    @GET("/market/{regionId}/history/")
    Call<CrestDictionary<CrestMarketHistory>> getMarketHistory(@Path("regionId") final long regionId,
                                                               @Query("type") final String typeRef);

}
