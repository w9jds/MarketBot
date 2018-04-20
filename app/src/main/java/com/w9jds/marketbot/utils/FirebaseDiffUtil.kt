package com.w9jds.marketbot.utils

import android.support.v7.util.DiffUtil
import com.google.firebase.database.DataSnapshot

class FirebaseDiffUtil(newItems: List<DataSnapshot>, oldItems: List<DataSnapshot>): DiffUtil.Callback() {

    private val old: List<DataSnapshot> = oldItems
    private val new: List<DataSnapshot> = newItems

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition].key == new[newItemPosition].key
    }

    override fun getOldListSize(): Int {
        return old.count()
    }

    override fun getNewListSize(): Int {
       return new.count()
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition].key == new[newItemPosition].key
    }

}