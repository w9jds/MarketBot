package com.w9jds.marketbot.data.loader

import android.content.Context
import com.google.firebase.database.*
import com.w9jds.marketbot.classes.ApiService
import com.w9jds.marketbot.classes.MarketBot
import com.w9jds.marketbot.classes.models.market.MarketOrder
import com.w9jds.marketbot.data.DataManager
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

abstract class OrdersLoader(context: Context) : DataManager(context) {

    @Inject lateinit var apiService: ApiService

    init {
        MarketBot.base.inject(this)
    }

    abstract fun onRegionsLoaded(data: List<MarketOrder>)

    fun loadBuyOrders(regionId: Int, typeId: Int) {
        loadStarted()
        incrementLoadingCount()
        getOrders(regionId, typeId,"buy")
    }

    fun loadSellOrders(regionId: Int, typeId: Int) {
        loadStarted()
        incrementLoadingCount()
        getOrders(regionId, typeId,"sell")
    }

    private fun getOrders(regionId: Int, typeId: Int, type: String) {
        apiService.getOrders(regionId, typeId, type)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext({
                onRegionsLoaded(it)
                decrementLoadingCount()
                loadFinished()
            })
            .doOnError({
                onRegionsLoaded(emptyList())
                decrementLoadingCount()
                loadFailed(it.message ?: "Error occurred while pulling orders...")
            })
    }

}