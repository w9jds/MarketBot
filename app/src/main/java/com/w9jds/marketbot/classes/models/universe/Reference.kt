package com.w9jds.marketbot.classes.models.universe

import com.google.gson.annotations.SerializedName

data class Reference (
        @SerializedName("category") val category: String,
        @SerializedName("id") val id: Long,
        @SerializedName("name") val name: String
)