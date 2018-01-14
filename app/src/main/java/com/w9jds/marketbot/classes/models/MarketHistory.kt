package com.w9jds.marketbot.classes.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.w9jds.marketbot.data.models.esi.History

data class MarketHistory(
        @JsonProperty override val date: String,
        @JsonProperty("order_count") override val orderCount: Long,
        @JsonProperty override val volume: Long,
        @JsonProperty override val average: Double,
        @JsonProperty override val highest: Double,
        @JsonProperty override val lowest: Double
): History