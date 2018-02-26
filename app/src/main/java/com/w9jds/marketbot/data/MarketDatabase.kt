package com.w9jds.marketbot.data

import com.raizlabs.android.dbflow.annotation.Database

@Database(name = MarketDatabase.NAME, version = MarketDatabase.VERSION)
class MarketDatabase {

    companion object {
        const val NAME: String = "MarketBotDb"
        const val VERSION: Int = 4
    }

}