package com.w9jds.marketbot.ui.adapters

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.w9jds.marketbot.R
import com.w9jds.marketbot.classes.models.universe.Region
import com.w9jds.marketbot.classes.models.market.MarketType
import com.w9jds.marketbot.ui.fragments.BuyOrders
import com.w9jds.marketbot.ui.fragments.Info
import com.w9jds.marketbot.ui.fragments.Margins
import com.w9jds.marketbot.ui.fragments.SellOrders
import io.reactivex.Observable

class TabAdapter(
        manager: FragmentManager,
        private val context: Context,
        private val type: MarketType,
        private val regionObservable: Observable<Region>
) : FragmentStatePagerAdapter(manager) {

    private val pageCount = 3

    private fun <T: Fragment> createTypeInstance(fragment: T, type: MarketType): T {
        val bundle = Bundle()
        bundle.putParcelable("type", type)

        fragment.arguments = bundle
        fragment.retainInstance = true

        return fragment
    }

    override fun getItem(position: Int): Fragment? {
        val sellFragment = createTypeInstance(SellOrders(), type)
        val buyFragment = createTypeInstance(BuyOrders(), type)

        sellFragment.setOnRegionChangeObservable(regionObservable)
        buyFragment.setOnRegionChangeObservable(regionObservable)

        return when(position) {
            0 -> createTypeInstance(Info(), type)
            1 -> sellFragment
            2 -> buyFragment
            3 -> createTypeInstance(Margins(), type)
            else -> null
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