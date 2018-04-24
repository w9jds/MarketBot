package com.w9jds.marketbot.data.loader

import android.content.Context
import com.google.firebase.database.*
import com.w9jds.marketbot.classes.MarketBot
import com.w9jds.marketbot.data.DataManager

abstract class ItemsLoader(context: Context) : DataManager(context) {

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



}