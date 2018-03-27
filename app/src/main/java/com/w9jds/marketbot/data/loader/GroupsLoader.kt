package com.w9jds.marketbot.data.loader

import android.content.Context
import android.content.SharedPreferences
import com.w9jds.marketbot.classes.EsiService
import com.w9jds.marketbot.classes.MarketBot
import com.w9jds.marketbot.data.DataManager
import com.w9jds.marketbot.data.models.esi.Base
import javax.inject.Inject

abstract class GroupsLoader(val context: Context): DataManager(context) {

    @Inject lateinit var esiServer: EsiService
    @Inject lateinit var preferences: SharedPreferences

    abstract fun onProgressUpdate(page: Int, totalPages: Int, message: String?)
    abstract fun onDataLoaded(data: List<Base>)

    init {
        MarketBot.base.inject(this)
    }

    private fun incrementUpdatingCount(count: Int) {
//        updatingCount.set(count)
    }

    private fun decrementUpdatingCount() {
//        updatingCount.decrementAndGet()
    }

//    private fun updatingCount(): Int {
//        return updatingCount.intValue()
//    }

    private fun updateStarted() {
//        dispatchUpdateStartedCallbacks()
    }




    fun loadMarketGroups(parentId: Long) {
        loadStarted()
        incrementLoadingCount()
//        onDataLoaded(MarketGroupEntry.getMarketGroupsForParent(parentId))
        decrementLoadingCount()
        loadFinished()
    }

    fun loadMarketTypes(groupId: Long) {
        loadStarted()
        incrementLoadingCount()
//        onDataLoaded(MarketTypeEntry.getMarketTypes(groupId))
        decrementLoadingCount()
        loadFinished()
    }

    fun searchMarketTypes(queryString: String) {
        loadStarted()
        incrementLoadingCount()
//        onDataLoaded(MarketTypeEntry.searchMarketTypes(queryString))
        decrementLoadingCount()
        loadFinished()
    }

}