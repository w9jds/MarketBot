package com.w9jds.marketbot.data.loader

import android.content.Context
import com.w9jds.marketbot.classes.ApiService
import com.w9jds.marketbot.classes.MarketBot
import com.w9jds.marketbot.classes.models.market.MarketOrder
import com.w9jds.marketbot.data.DataManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

abstract class OrdersLoader(context: Context) : DataManager(context) {

    @Inject
    lateinit var apiService: ApiService

    abstract fun onRegionsLoaded(data: List<MarketOrder>)

    fun loadBuyOrders(regionId: Int, typeId: Int) {
        loadStarted()
        incrementLoadingCount()
        getOrders(regionId, typeId,true)
    }

    fun loadSellOrders(regionId: Int, typeId: Int) {
        loadStarted()
        incrementLoadingCount()
        getOrders(regionId, typeId,false)
    }

    private fun getOrders(regionId: Int, typeId: Int, isBuyOrder: Boolean) {
        apiService.getOrders(regionId, typeId)
            .observeOn(Schedulers.io())
            .map { it.filter { marketOrder -> marketOrder.is_buy_order == isBuyOrder } }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    onRegionsLoaded(it)
                    decrementLoadingCount()
                    loadFinished()
                },
                onError = {
                    onRegionsLoaded(emptyList())
                    decrementLoadingCount()
                    loadFailed(it.message ?: "Error occurred while pulling orders...")
                }
            )
    }

}