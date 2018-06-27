package com.w9jds.marketbot.classes

import com.w9jds.marketbot.classes.models.universe.Reference
import com.w9jds.marketbot.classes.models.market.MarketOrder
import com.w9jds.marketbot.classes.models.universe.Station
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("/v1/markets/{region_id}/orders/")
    fun getOrders(
            @Path("region_id") regionId: Int,
            @Query("type_id") typeId: Int
    ): Observable<List<MarketOrder>>

    @POST("/v2/universe/names/")
    fun getNames(
            @Body ids: List<Long>
    ): Call<List<Reference>>

    @GET("/v2/universe/stations/{station_id}/")
    fun getStation(
            @Path("station_id") locationId: Long
    ): Call<Station>

}