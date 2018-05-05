package com.w9jds.marketbot.classes.models.market

import com.fasterxml.jackson.annotation.JsonProperty

data class MarketOrder(
        @JsonProperty val order_id: Int,
        @JsonProperty val type_id: Int,
        @JsonProperty val location_id: Int,
        @JsonProperty val system_id: Int,
        @JsonProperty val system_name: String?,
        @JsonProperty val volume_total: Int,
        @JsonProperty val volume_remain: Int,
        @JsonProperty val min_volume: Int,
        @JsonProperty val price: Double,
        @JsonProperty val is_buy_order: Boolean,
        @JsonProperty val duration: Int,
        @JsonProperty val issued: String,
        @JsonProperty val range: String
)