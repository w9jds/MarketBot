package com.w9jds.marketbot.data.model

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.w9jds.marketbot.classes.MarketBot
import com.w9jds.marketbot.data.LoaderModel

class RegionsModel: LoaderModel() {

    val regions: MutableLiveData<List<DataSnapshot>> = MutableLiveData()

    private val database: FirebaseDatabase = MarketBot.base.firebase()

    private val valueHandler = object: ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot?) {
            regions.value = snapshot?.children?.distinct() ?: emptyList()
            loadFinished()
        }

        override fun onCancelled(error: DatabaseError?) {
            regions.value = emptyList()
            loadFinished()
        }
    }

    fun loadRegions() {
        loadStarted()

        database.getReference("regions")
                .orderByChild("name")
                .addListenerForSingleValueEvent(valueHandler)
    }

}