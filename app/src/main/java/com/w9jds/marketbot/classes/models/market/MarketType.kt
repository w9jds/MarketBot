package com.w9jds.marketbot.classes.models.market

import com.w9jds.marketbot.classes.models.dogma.AttributeBase
import com.w9jds.marketbot.classes.models.dogma.EffectBase


data class MarketType(
        val type_id: Int? = null,
        val name: String = "",
        val description: String = "",
        val published: Boolean = false,
        val group_id: Int? = null,
        val market_group_id: Int? = null,
        val radius: Float? = null,
        val volume: Float? = null,
        val packaged_volume: Float? = null,
        val icon_id: Int? = null,
        val capacity: Float? = null,
        val portion_size: Int? = null,
        val mass: Float? = null,
        val graphic_id: Int? = null,
        val dogma_attributes: List<AttributeBase>? = null,
        val dogma_effects: List<EffectBase>? = null
)
