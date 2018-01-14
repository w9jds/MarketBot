package com.w9jds.marketbot.classes.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.w9jds.marketbot.data.models.esi.Order

data class MarketOrder(
        override val name: String? = null,
        @JsonProperty("order_id") override val id: Int,
        @JsonProperty("type_id") override val typeId: Int,
        @JsonProperty("location_id") override val locationId: Long,
        @JsonProperty("volume_total") override val totalVolume: Int,
        @JsonProperty("volume_remain") override val volumeLeft: Int,
        @JsonProperty("min_volume") override val minVolume: Int,
        @JsonProperty override val price: Double,
        @JsonProperty("is_buy_order") override val isBuyOrder: Boolean,
        @JsonProperty override val duration: Int,
        @JsonProperty override val issued: String,
        @JsonProperty override val range: String
): Order