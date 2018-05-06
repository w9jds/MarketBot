package com.w9jds.marketbot.classes.models.market

import com.google.gson.annotations.SerializedName

class MarketOrder(
        @SerializedName("order_id") val order_id: Long,
        @SerializedName("type_id") val type_id: Long,
        @SerializedName("location_id") val location_id: Long,
        @SerializedName("system_id") val system_id: Long,
        val system_name: String?,
        @SerializedName("volume_total") val volume_total: Long,
        @SerializedName("volume_remain") val volume_remain: Long,
        @SerializedName("min_volume") val min_volume: Long,
        @SerializedName("price") val price: Double,
        @SerializedName("is_buy_order") val is_buy_order: Boolean,
        @SerializedName("duration") val duration: Long,
        @SerializedName("issued")  val issued: String,
        @SerializedName("range")  val range: String
)