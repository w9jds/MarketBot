package com.w9jds.marketbot.data.model

import android.arch.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.w9jds.marketbot.classes.MarketBot
import com.w9jds.marketbot.classes.models.market.MarketGroup
import com.w9jds.marketbot.data.LoaderModel
import com.w9jds.marketbot.utils.extensions.getValue
import io.reactivex.Observable

class GroupsModel: LoaderModel() {

    var groups: MutableLiveData<List<DataSnapshot>> = MutableLiveData()

    private val firebase: FirebaseDatabase = MarketBot.base.firebase()

    private val valueHandler = object: ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot?) {
            groups.value = snapshot?.children?.distinct() ?: emptyList()
            loadFinished()
        }

        override fun onCancelled(error: DatabaseError?) {
            groups.value = emptyList()
            loadFinished()
        }
    }

    fun loadMarketGroups(parentId: Double?) {
        loadStarted()

        val query: Query = firebase.getReference("groups").orderByChild("parent_group_id")

        if (parentId != null) {
            query.equalTo(parentId).addListenerForSingleValueEvent(valueHandler)
        }
        else {
            query.equalTo(null).addListenerForSingleValueEvent(valueHandler)
        }
    }

    fun loadMarketTypes(groupId: Long) {
        loadStarted()

        firebase.getReference("types/$groupId").orderByChild("name")
                .addListenerForSingleValueEvent(valueHandler)

    }

    fun getMarketGroup(groupId: Long): Observable<List<MarketGroup>> {
        return firebase.getReference("groups/$groupId").getValue()
    }

    fun loadSearchResults(query: String) {
        loadStarted()

        firebase.getReference("types")
                .orderByChild("name")
                .startAt(query)
                .endAt("$query\\uf8ff")
                .addListenerForSingleValueEvent(valueHandler)
    }

}