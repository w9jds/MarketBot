package com.w9jds.marketbot.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.w9jds.marketbot.classes.models.market.MarketGroup
import com.w9jds.marketbot.classes.models.market.MarketType
import com.w9jds.marketbot.data.dao.MarketGroupDao
import com.w9jds.marketbot.data.dao.MarketTypeDao

@Database(entities = [MarketType::class, MarketGroup::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {
    abstract fun marketGroupDao(): MarketGroupDao
    abstract fun marketTypeDao(): MarketTypeDao
}