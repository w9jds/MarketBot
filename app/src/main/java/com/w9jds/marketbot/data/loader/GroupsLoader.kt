package com.w9jds.marketbot.data.loader

import android.content.Context
import com.google.firebase.database.*
import com.w9jds.marketbot.classes.MarketBot
import com.w9jds.marketbot.data.DataManager

abstract class GroupsLoader(context: Context) : DataManager(context) {

    private val database: FirebaseDatabase = MarketBot.base.database()

    abstract fun onDataLoaded(data: List<DataSnapshot>)

    private val valueHandler = object: ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot?) {
            onDataLoaded(snapshot?.children?.distinct() ?: emptyList())
            decrementLoadingCount()
            loadFinished()
        }

        override fun onCancelled(error: DatabaseError?) {
            onDataLoaded(emptyList())
            decrementLoadingCount()
            loadFailed(error?.message ?: "Database Error Occurred")
        }
    }

    fun loadMarketGroups(parentId: Double?) {
        val query: Query = database.getReference("groups").orderByChild("parent_group_id")

        loadStarted()
        incrementLoadingCount()

        if (parentId != null) {
            query.equalTo(parentId)
                    .addListenerForSingleValueEvent(valueHandler)
        }
        else {
            query.equalTo(null)
                    .addListenerForSingleValueEvent(valueHandler)
        }
    }

    fun loadMarketTypes(groupId: Long) {
        loadStarted()
        incrementLoadingCount()

        database.getReference("types/$groupId")
                .orderByChild("name")
                .addListenerForSingleValueEvent(valueHandler)
    }

    init {
        MarketBot.base.inject(context)
    }

}