package com.w9jds.marketbot.classes.models

import com.google.gson.annotations.SerializedName

data class Position (
        @SerializedName("x")
        val x: Double,

        @SerializedName("y")
        val y: Double,

        @SerializedName("z")
        val z: Double
)