package com.w9jds.marketbot.classes

import com.w9jds.marketbot.classes.models.market.MarketOrder
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/market/{regionId}/orders/{typeId}")
    fun getOrders(
        @Path("regionId") regionId: Int,
        @Path("typeId") typeId: Int,
        @Query("type") orderType: String
    ): Observable<List<MarketOrder>>


}