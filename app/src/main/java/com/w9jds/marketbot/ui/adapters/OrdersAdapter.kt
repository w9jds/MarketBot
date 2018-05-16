package com.w9jds.marketbot.ui.adapters

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.w9jds.marketbot.BR
import com.w9jds.marketbot.R
import com.w9jds.marketbot.classes.models.market.MarketOrder
import com.w9jds.marketbot.utils.OrdersDiffUtil


class OrdersAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val MARKET_ORDER_VIEW: Int = 0
    }

    private var items: MutableList<MarketOrder> = mutableListOf()

    inner class OrderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = DataBindingUtil.bind<ViewDataBinding>(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return createOrderHolder(parent)
    }

    private fun createOrderHolder(parent: ViewGroup): OrderHolder {
        val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_item_order, parent, false)

        return OrderHolder(view)
    }


    override fun getItemViewType(position: Int): Int {
        return MARKET_ORDER_VIEW
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            MARKET_ORDER_VIEW -> bindOrder(items[position], holder as OrderHolder)
        }
    }

    private fun bindOrder(group: MarketOrder?, holder: OrderHolder) {
        holder.binding?.setVariable(BR.marketOrder, group)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItems(snapshots: List<MarketOrder>) {
        val oldItems: MutableList<MarketOrder> = ArrayList(items)
        val diffUtil = DiffUtil.calculateDiff(OrdersDiffUtil(oldItems, snapshots))

        items.clear()
        items.addAll(snapshots)
        diffUtil.dispatchUpdatesTo(this)
    }
}