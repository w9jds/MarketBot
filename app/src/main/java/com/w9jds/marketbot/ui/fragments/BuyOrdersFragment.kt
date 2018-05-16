package com.w9jds.marketbot.ui.fragments

import android.arch.lifecycle.LifecycleOwner
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.w9jds.marketbot.R
import com.w9jds.marketbot.classes.models.market.MarketType
import com.w9jds.marketbot.classes.models.universe.Region
import com.w9jds.marketbot.data.model.OrdersModel
import com.w9jds.marketbot.databinding.FragmentListBinding
import com.w9jds.marketbot.ui.adapters.OrdersAdapter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class BuyOrdersFragment: Fragment(), LifecycleOwner {

    private lateinit var regionObservable: Observable<Region>
    private lateinit var binding: FragmentListBinding
    private lateinit var ordersAdapter: OrdersAdapter
    private lateinit var model: OrdersModel
    private lateinit var type: MarketType

    private var region: Region? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        type = arguments?.getParcelable("type")!!
        ordersAdapter = OrdersAdapter()
//        ordersLoaders = object: OrdersLoader(context!!) {
//            init {
//                MarketBot.base.inject(this)
//            }
//
//            override fun onRegionsLoaded(data: List<MarketOrder>) {
//                binding.message.visibility = View.GONE
//
//                if (data.isEmpty()) {
//                    binding.message.text = getString(R.string.no_buy_orders_available)
//                    binding.message.visibility = View.VISIBLE
//                }
//
//                ordersAdapter.updateItems(data.sortedByDescending { it.price })
//            }
//        }

        regionObservable.observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    region = it
                    loadBuyOrders()
                },
                onError = {
//                    dataFailedLoading(it.message ?: "Error occured pulling orders...")
                    it.printStackTrace()
                }
            )

    }

    private fun loadBuyOrders() {
        if (region != null && type.type_id != null) {
//            ordersLoaders.loadBuyOrders(region?.region_id!!, type.type_id!!)
        }
    }

    fun setOnRegionChangeObservable(observable: Observable<Region>) {
        regionObservable = observable
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_list, container, false)
        binding = DataBindingUtil.bind(view)!!

        binding.itemsList.layoutManager = LinearLayoutManager(activity)
        binding.itemsList.itemAnimator = DefaultItemAnimator()
        binding.itemsList.adapter = ordersAdapter

        return view
    }

//    override fun dataStartedLoading() {
//        binding.contentLoading.show()
//    }
//
//    override fun dataFinishedLoading() {
//        binding.contentLoading.hide()
//    }
//
//    override fun dataFailedLoading(errorMessage: String) {
//        binding.contentLoading.hide()
//    }

}