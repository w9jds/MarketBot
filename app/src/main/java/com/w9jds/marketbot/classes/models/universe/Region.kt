package com.w9jds.marketbot.classes.models.universe

data class Region (
        val region_id: Int? = null,
        val name: String = "",
        val description: String = "",
        val constellations: List<Int> = emptyList()
)