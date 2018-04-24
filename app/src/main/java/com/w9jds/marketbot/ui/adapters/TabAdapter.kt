package com.w9jds.marketbot.ui.adapters

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.w9jds.marketbot.R
import com.w9jds.marketbot.classes.models.market.MarketType
import com.w9jds.marketbot.ui.fragments.Info
import com.w9jds.marketbot.ui.fragments.List

class TabAdapter(
        manager: FragmentManager,
        private val context: Context,
        private val type: MarketType
) : FragmentStatePagerAdapter(manager) {

    private val pageCount = 5

    private fun <T: Fragment> createTypeInstance(fragment: T, type: MarketType): T {
        val bundle = Bundle()
        bundle.putParcelable("type", type)

        fragment.arguments = bundle
        fragment.retainInstance = true

        return fragment
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> createTypeInstance(Info(), type)
            else -> createTypeInstance(List(), type)
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> context.getString(R.string.type_info_label)
            1 -> context.getString(R.string.sell_orders)
            2 -> context.getString(R.string.buy_orders)
            3 -> context.getString(R.string.margins)
            4 -> context.getString(R.string.history)
            else -> ""
        }
    }

    override fun getCount(): Int {
        return pageCount
    }

}