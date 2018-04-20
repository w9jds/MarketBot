package com.w9jds.marketbot.classes.models.market

data class MarketGroup(
        val market_group_id: Int? = null,
        val name: String = "",
        val description: String = "",
        val types: List<Int>? = emptyList(),
        val parent_group_id: Int? = null
)