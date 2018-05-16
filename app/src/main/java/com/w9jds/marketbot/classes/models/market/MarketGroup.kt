package com.w9jds.marketbot.classes.models.market

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

@Entity
data class MarketGroup(
    @PrimaryKey(autoGenerate = false)
    var market_group_id: Int? = null,

    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "description")
    var description: String = "",

    @Ignore
    var types: List<Int>? = emptyList(),

    @ColumnInfo(name = "parent_group_id")
    var parent_group_id: Int? = null
)