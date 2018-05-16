package com.w9jds.marketbot.data.model

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.w9jds.marketbot.classes.ApiService
import com.w9jds.marketbot.classes.models.market.MarketOrder
import com.w9jds.marketbot.classes.models.universe.Reference
import com.w9jds.marketbot.classes.models.universe.Station
import com.w9jds.marketbot.data.LoaderModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject

class OrdersModel: LoaderModel() {

    @Inject
    lateinit var apiService: ApiService

    var orders: MutableLiveData<List<MarketOrder>> = MutableLiveData()

    fun loadBuyOrders(regionId: Int, typeId: Int) {
        loadStarted()
        getOrders(regionId, typeId,true)
    }

    fun loadSellOrders(regionId: Int, typeId: Int) {
        loadStarted()
        getOrders(regionId, typeId,false)
    }

    private fun getOrders(regionId: Int, typeId: Int, isBuyOrder: Boolean) {

        apiService.getOrders(regionId, typeId)
            .observeOn(Schedulers.io())
            .map { it.filter { marketOrder -> marketOrder.is_buy_order == isBuyOrder } }
            .map { getOrderInfo(it) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    orders.value = it
                    loadFinished()
                },
                onError = {
                    orders.value = emptyList()
                    loadFinished()
                }
            )

    }

    private fun getOrderInfo(orders: List<MarketOrder>): List<MarketOrder> {
        val names: HashMap<Long, String> = hashMapOf()
        val structures: HashMap<Long, String> = hashMapOf()
        val systemIds: MutableList<Long> = mutableListOf()
        val locationIds: MutableList<Long> = mutableListOf()

        orders.forEach {
            if (!systemIds.contains(it.system_id)) {
                systemIds.add(it.system_id)
            }
            if (!locationIds.contains(it.location_id)) {
                locationIds.add(it.location_id)
            }
        }

        val reference: List<Reference> = apiService.getNames(systemIds).execute().body()!!

        reference.forEach { names[it.id] = it.name }

        locationIds.forEach {
            val station: Response<Station> = apiService.getStation(it).execute()

            if (station.isSuccessful) {
                val content = station.body() ?: station.errorBody()

                if (content is Station) {
                    structures[it] = content.name
                }
            }
        }

        orders.forEach {
            it.system_name = names[it.system_id]

            if (structures.containsKey(it.location_id)) {
                it.location_name = structures[it.location_id]
            }
        }

        return orders
    }

}