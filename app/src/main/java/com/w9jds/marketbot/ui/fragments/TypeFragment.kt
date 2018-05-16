package com.w9jds.marketbot.ui.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.w9jds.marketbot.R
import com.w9jds.marketbot.classes.MarketBot
import com.w9jds.marketbot.classes.models.market.MarketType
import com.w9jds.marketbot.classes.models.universe.Region
import com.w9jds.marketbot.data.model.RegionsModel
import com.w9jds.marketbot.databinding.FragmentTypeBinding
import com.w9jds.marketbot.ui.adapters.RegionsAdapter
import com.w9jds.marketbot.ui.adapters.TabAdapter
import io.reactivex.subjects.BehaviorSubject

class TypeFragment: Fragment() {

    private val sharedPreferences: SharedPreferences = MarketBot.base.preferences()

    private var regionId = sharedPreferences.getInt("regionId", 10000002)
    private lateinit var binding: FragmentTypeBinding
    private lateinit var regionsAdapter: RegionsAdapter
    private lateinit var tabAdapter: TabAdapter
    private lateinit var model: RegionsModel
    private lateinit var type: MarketType

    private val regionObservable: BehaviorSubject<Region> = BehaviorSubject.create()


//    private var regionLoader: RegionsLoader = object : RegionsLoader(context!!) {
//        override fun onRegionsLoaded(data: List<DataSnapshot>) {
//            regionsAdapter.updateItems(data)
//            binding.regionSpinner.setSelection(regionsAdapter.positions[regionId] ?: 0)
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model = ViewModelProviders.of(this).get(RegionsModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_type, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = DataBindingUtil.bind(view)!!

        regionsAdapter = RegionsAdapter()

        tabAdapter = TabAdapter(fragmentManager!!, context!!, type, regionObservable)

        model.regions.observe(this, Observer {
            regionsAdapter.updateItems(it ?: emptyList())
            binding.regionSpinner.setSelection(regionsAdapter.positions[regionId] ?: 0)
        })

        binding.contentPager.adapter = tabAdapter
        binding.contentPager.offscreenPageLimit = 4
        binding.slidingTabs.setupWithViewPager(binding.contentPager)

        binding.regionSpinner.adapter = regionsAdapter
        binding.regionSpinner.onItemSelectedListener = regionListener
    }

    private val regionListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {

        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val region: Region? = regionsAdapter.getItem(position)

            if (region != null) {
                regionObservable.onNext(region)
                sharedPreferences.edit().putInt("regionId", region.region_id!!).apply()
            }
        }
    }
}