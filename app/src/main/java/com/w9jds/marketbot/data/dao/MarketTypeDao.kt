package com.w9jds.marketbot.data.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.w9jds.marketbot.classes.models.market.MarketType
import io.reactivex.Maybe

@Dao
interface MarketTypeDao {

    @Query("Select * from marketType where market_group_id = :groupId order by name")
    fun getGroupTypes(groupId: Long): Maybe<List<MarketType>>

    @Insert(onConflict = REPLACE)
    fun insertGroupType(type: MarketType)

    @Insert(onConflict = REPLACE)
    fun insertAllTypes(types: Iterable<MarketType>)

    @Query("Select * from marketType where name Like '%' + :query + '%'")
    fun searchMarketTypes(query: String): Maybe<List<MarketType>>

}