package com.w9jds.marketbot.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
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

class SellOrdersFragment: Fragment(), LifecycleOwner {

    private lateinit var regionsObservable: Observable<Region>
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
//                    binding.message.text = getString(R.string.no_sell_orders_available)
//                    binding.message.visibility = View.VISIBLE
//                }
//
//                ordersAdapter.updateItems(data.sortedBy { it.price })
//            }
//        }

        regionsObservable.observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    region = it
                    loadSellOrders()
                },
                onError = {
//                    dataFailedLoading(it.message ?: "Error occured pulling orders...")
                    it.printStackTrace()
                }
            )
    }

    private fun loadSellOrders() {
        if (region != null && type.type_id != null) {
//            ordersLoaders.loadSellOrders(region?.region_id!!, type.type_id!!)
        }
    }

    fun setOnRegionChangeObservable(observable: Observable<Region>) {
        regionsObservable = observable
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