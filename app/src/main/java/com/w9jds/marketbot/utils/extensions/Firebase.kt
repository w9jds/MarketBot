package com.w9jds.marketbot.utils.extensions

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import io.reactivex.Observable

fun Query.once(): Observable<DataSnapshot> {
    return Observable.create {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                it.onNext(snapshot)
                it.onComplete()
            }
            override fun onCancelled(error: DatabaseError) {
                it.onError(error.toException())
                it.onComplete()
            }
        }

        this.addListenerForSingleValueEvent(listener)
    }
}

inline fun <reified T: Any> Query.value(): Observable<T> {
    return once().map {
        it.getValue(T::class.java)
    }
}