package com.w9jds.marketbot.classes.models.universe

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import com.google.gson.annotations.SerializedName
import com.w9jds.marketbot.classes.models.Position

@Entity
data class Station(

        @ColumnInfo(name = "max_dockable_ship_volume")
        @SerializedName("max_dockable_ship_volume")
        val max_dockable_ship_volume: Long,

        @ColumnInfo(name = "office_rental_cost")
        @SerializedName("office_rental_cost")
        val office_rental_cost: Double,

        @ColumnInfo(name = "owner")
        @SerializedName("owner")
        val owner: Long?,

        @ColumnInfo(name = "race_id")
        @SerializedName("race_id")
        val race_id: Int?,

        @ColumnInfo(name = "reprocessing_efficiency")
        @SerializedName("reprocessing_efficiency")
        val reprocessing_efficiency: Double,

        @ColumnInfo(name = "reprocessing_stations_take")
        @SerializedName("reprocessing_stations_take")
        val reprocessing_stations_take: Double,

        @ColumnInfo(name = "services")
        @SerializedName("services")
        val services: List<String>,

        @ColumnInfo(name = "name")
        @SerializedName("name")
        val name: String,

        @Ignore
        @SerializedName("position")
        val position: Position?,

        @ColumnInfo(name = "station_id")
        @SerializedName("station_id")
        val station_id: Long,

        @ColumnInfo(name = "system_id")
        @SerializedName("system_id")
        val system_id: Long,

        @ColumnInfo(name = "type_id")
        @SerializedName("type_id")
        val type_id: Long

)

