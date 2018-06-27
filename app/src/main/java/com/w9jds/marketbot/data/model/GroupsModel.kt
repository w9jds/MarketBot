package com.w9jds.marketbot.data.model

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.w9jds.marketbot.classes.MarketBot
import com.w9jds.marketbot.classes.models.market.MarketGroup
import com.w9jds.marketbot.data.LoaderModel
import com.w9jds.marketbot.utils.extensions.once
import com.w9jds.marketbot.utils.extensions.value
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy

class GroupsModel: LoaderModel() {

    val groups: MutableLiveData<List<DataSnapshot>> = MutableLiveData()

    private val firebase: FirebaseDatabase = MarketBot.base.firebase()

    private val valueHandler = object: ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            groups.value = snapshot.children.distinct() ?: emptyList()
            loadFinished()
        }

        override fun onCancelled(error: DatabaseError) {
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

    private fun setGroups(data: DataSnapshot) {
        groups.value = data.children?.distinct() ?: emptyList()
        loadFinished()
    }

    private fun handleErrors(error: Throwable) {
        groups.value = emptyList()
        loadFinished()
    }

    fun loadMarketTypes(groupId: Long) {
        loadStarted()

        firebase.getReference("types/$groupId").orderByChild("name").once()
                .subscribeBy(
                    onNext = this::setGroups,
                    onError = this::handleErrors
                )
    }

    fun getMarketGroup(groupId: Long): Observable<List<MarketGroup>> {
        return firebase.getReference("groups/$groupId").value()
    }

//    fun loadSearchResults(query: String) {
//        loadStarted()
//
//        firebase.getReference("types")
//                .orderByChild("name")
//                .startAt(query)
//                .endAt("$query\\uf8ff")
//                .addListenerForSingleValueEvent(valueHandler)
//    }

}