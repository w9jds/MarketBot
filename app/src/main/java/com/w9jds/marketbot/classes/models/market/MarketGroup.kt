package com.w9jds.marketbot.classes.models.market

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class MarketGroup(

        @PrimaryKey(autoGenerate = false)
        val market_group_id: Int? = null,

        @ColumnInfo(name = "name")
        val name: String = "",

        @ColumnInfo(name = "description")
        val description: String = "",

        @ColumnInfo(name = "types")
        val types: List<Int>? = emptyList(),

        @ColumnInfo(name = "parent_group_id")
        val parent_group_id: Int? = null

)