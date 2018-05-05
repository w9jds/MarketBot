package com.w9jds.marketbot.utils

import android.support.v7.util.DiffUtil
import com.w9jds.marketbot.classes.models.market.MarketOrder

class OrdersDiffUtil(oldItems: List<MarketOrder>, newItems: List<MarketOrder>): DiffUtil.Callback() {

    private val old: List<MarketOrder> = oldItems
    private val new: List<MarketOrder> = newItems

    override fun getOldListSize(): Int {
        return old.count()
    }

    override fun getNewListSize(): Int {
        return new.count()
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition].order_id == new[newItemPosition].order_id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition].order_id == new[newItemPosition].order_id
    }

}