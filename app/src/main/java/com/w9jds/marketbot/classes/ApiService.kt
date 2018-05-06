package com.w9jds.marketbot.classes

import com.w9jds.marketbot.classes.models.market.MarketOrder
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

//    @GET("/market/{regionId}/orders/{typeId}")
//    fun getOrders(
//        @Path("regionId") regionId: Int,
//        @Path("typeId") typeId: Int,
//        @Query("type") orderType: String
//    ): Observable<List<MarketOrder>>

    @GET("/v1/markets/{region_id}/orders/")
    fun getOrders(
            @Path("region_id") regionId: Int,
            @Query("type_id") typeId: Int
    ): Observable<List<MarketOrder>>
}