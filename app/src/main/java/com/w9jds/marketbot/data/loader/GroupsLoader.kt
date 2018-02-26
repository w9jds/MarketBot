package com.w9jds.marketbot.data.loader

import android.content.Context
import android.content.SharedPreferences
import com.w9jds.marketbot.classes.EsiService
import com.w9jds.marketbot.classes.MarketBot
import com.w9jds.marketbot.data.DataManager
import javax.inject.Inject

abstract class GroupsLoader(val context: Context): DataManager(context) {

    @Inject lateinit var esiServer: EsiService
    @Inject lateinit var preferences: SharedPreferences

    init {

    }


}