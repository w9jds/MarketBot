package com.w9jds.marketbot.data.loader

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.w9jds.marketbot.classes.MarketBot
import com.w9jds.marketbot.data.DataManager
import javax.inject.Inject

abstract class GroupsLoader(val context: Context): DataManager(context) {

    @Inject lateinit var database: FirebaseDatabase

    abstract fun onDataLoaded(data: List<DataSnapshot>)

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

    fun loadMarketGroups(parentId: Long?) {
        val id: Double = parentId?.toDouble()

        loadStarted()
        incrementLoadingCount()

        database.getReference("groups")
                .orderByChild("parent_group_id")
                .equalTo(parentId)
                .addListenerForSingleValueEvent()

//        onDataLoaded(MarketGroupEntry.getMarketGroupsForParent(parentId))
        decrementLoadingCount()
        loadFinished()
    }

    fun loadMarketTypes(groupId: Long) {
        loadStarted()
        incrementLoadingCount()

        database.getReference("types/$groupId")
                .orderByChild("name")
                .addListenerForSingleValueEvent()

        decrementLoadingCount()
        loadFinished()
    }

    fun searchMarketTypes(queryString: String) {
        loadStarted()
        incrementLoadingCount()



        decrementLoadingCount()
        loadFinished()
    }

}