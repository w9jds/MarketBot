package com.w9jds.marketbot.classes;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface EsiService {

    @GET("/v1/markets/groups/")
    Observable<Response<List<Long>>> getGroups();

    @GET("/v1/markets/groups/{groupId}/")
    Observable<Response<List<EsiMarketGroup>>> getGroup(@Path("groupId") final long groupId);

    @GET("/v1/universe/regions/")
    Observable<Response<List<Long>>> getRegions();

    @POST("/v2/universe/names/")
    Observable<Response<List<EsiName>>> getNames(@Body List<Integer> ids);

    @GET("/v3/universe/types/{typeId}/")
    Observable<Response<List<EsiType>>> getType(@Path("typeId") final long typeId);

}
