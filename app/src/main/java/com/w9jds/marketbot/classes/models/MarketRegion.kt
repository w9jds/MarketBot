package com.w9jds.marketbot.classes.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.w9jds.marketbot.data.models.esi.Base

data class MarketRegion(
        @JsonProperty("region_id") override val id: Int,
        @JsonProperty override val name: String?
): Base