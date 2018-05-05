package com.w9jds.marketbot.data.loader

import android.content.Context
import com.google.firebase.database.*
import com.w9jds.marketbot.classes.MarketBot
import com.w9jds.marketbot.data.DataManager

abstract class RegionsLoader(context: Context) : DataManager(context) {

    private val database: FirebaseDatabase = MarketBot.base.database()

    abstract fun onRegionsLoaded(data: List<DataSnapshot>)

    private val valueHandler = object: ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot?) {
            onRegionsLoaded(snapshot?.children?.distinct() ?: emptyList())
            decrementLoadingCount()
            loadFinished()
        }

        override fun onCancelled(error: DatabaseError?) {
            onRegionsLoaded(emptyList())
            decrementLoadingCount()
            loadFailed(error?.message ?: "Database Error Occurred")
        }
    }

    fun loadRegions() {
        loadStarted()
        incrementLoadingCount()

        database.getReference("regions")
                .orderByChild("name")
                .addListenerForSingleValueEvent(valueHandler)
    }

}