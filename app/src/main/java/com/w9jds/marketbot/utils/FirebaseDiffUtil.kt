package com.w9jds.marketbot.utils


import androidx.recyclerview.widget.DiffUtil
import com.google.firebase.database.DataSnapshot

class FirebaseDiffUtil(oldItems: List<DataSnapshot>, newItems: List<DataSnapshot>): DiffUtil.Callback() {

    private val old: List<DataSnapshot> = oldItems
    private val new: List<DataSnapshot> = newItems

    override fun getOldListSize(): Int {
        return old.count()
    }

    override fun getNewListSize(): Int {
       return new.count()
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition].key == new[newItemPosition].key
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition].key == new[newItemPosition].key
    }

}