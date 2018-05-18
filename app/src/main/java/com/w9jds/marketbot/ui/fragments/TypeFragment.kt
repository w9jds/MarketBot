package com.w9jds.marketbot.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.w9jds.marketbot.R
import com.w9jds.marketbot.classes.MarketBot
import com.w9jds.marketbot.classes.models.market.MarketType
import com.w9jds.marketbot.classes.models.universe.Region
import com.w9jds.marketbot.data.model.RegionsModel
import com.w9jds.marketbot.databinding.FragmentTypeBinding
import com.w9jds.marketbot.ui.adapters.TabAdapter
import io.reactivex.subjects.BehaviorSubject

class TypeFragment: Fragment() {

    private val sharedPreferences: SharedPreferences = MarketBot.base.preferences()

    private var regionId = sharedPreferences.getInt("regionId", 10000002)
    private lateinit var binding: FragmentTypeBinding
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

        tabAdapter = TabAdapter(fragmentManager!!, context!!, type, regionObservable)

        binding.contentPager.adapter = tabAdapter
        binding.contentPager.offscreenPageLimit = 4
        binding.slidingTabs.setupWithViewPager(binding.contentPager)

    }
}