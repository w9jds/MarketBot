package com.w9jds.marketbot.data.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.w9jds.marketbot.classes.models.market.MarketGroup
import io.reactivex.Maybe

@Dao
interface MarketGroupDao {

    @Query("Select * from MarketGroup where parent_group_id = :parentId order by name")
    fun getGroup(parentId: Long): Maybe<List<MarketGroup>>

    @Insert(onConflict = REPLACE)
    fun insertGroup(group: MarketGroup)

    @Insert(onConflict = REPLACE)
    fun insertAllGroups(groups: Iterable<MarketGroup>)

}