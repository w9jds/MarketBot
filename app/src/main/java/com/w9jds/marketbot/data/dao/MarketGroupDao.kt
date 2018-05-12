package com.w9jds.marketbot.data.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.w9jds.marketbot.classes.models.market.MarketGroup

@Dao
interface MarketGroupDao {

    @Query("Select * from MarketGroup where parent_group_id = :parentId order by name")
    fun getGroup(parentId: Long)

    @Insert(onConflict = REPLACE)
    fun insertGroup(type: MarketGroup)

}