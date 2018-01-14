package com.w9jds.marketbot.classes

import com.w9jds.marketbot.data.models.esi.MarketGroup
import com.w9jds.marketbot.data.models.esi.Name
import com.w9jds.marketbot.data.models.esi.Type
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EsiService {

    @GET("/v1/markets/groups/")
    fun getGroups(): List<Long>

    @GET("/v1/markets/groups/{groupId}/")
    fun getGroup(@Path("groupId") groupId: Long): List<MarketGroup>

    @GET("/v1/universe/regions/")
    fun getRegions(): List<Long>

    @POST("/v2/universe/names/")
    fun getNames(@Body ids: List<Int>): List<Name>

    @GET("/v3/universe/types/{typeId}/")
    fun getType(@Path("typeId") typeId: Long): List<Type>

}