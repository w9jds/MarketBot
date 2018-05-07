package com.w9jds.marketbot.classes.models.universe

import com.google.gson.annotations.SerializedName
import com.w9jds.marketbot.classes.models.Position

data class Station(
        @SerializedName("max_dockable_ship_volume") val max_dockable_ship_volume: Long,
        @SerializedName("office_rental_cost") val office_rental_cost: Double,
        @SerializedName("owner") val owner: Long?,
        @SerializedName("race_id") val race_id: Int?,
        @SerializedName("reprocessing_efficiency") val reprocessing_efficiency: Double,
        @SerializedName("reprocessing_stations_take") val reprocessing_stations_take: Double,
        @SerializedName("services") val services: List<String>,
        @SerializedName("name") val name: String,
        @SerializedName("position") val position: Position?,
        @SerializedName("station_id") val station_id: Long,
        @SerializedName("system_id") val system_id: Long,
        @SerializedName("type_id") val type_id: Long
)

