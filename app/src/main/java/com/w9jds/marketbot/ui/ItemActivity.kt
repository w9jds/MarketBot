package com.w9jds.marketbot.ui

import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import com.google.firebase.database.DataSnapshot
import com.w9jds.marketbot.R
import com.w9jds.marketbot.classes.MarketBot
import com.w9jds.marketbot.classes.models.universe.Region
import com.w9jds.marketbot.classes.models.market.MarketType
import com.w9jds.marketbot.data.DataLoadingSubject
import com.w9jds.marketbot.data.loader.RegionsLoader
import com.w9jds.marketbot.databinding.ActivityMarketItemBinding
import com.w9jds.marketbot.ui.adapters.RegionsAdapter
import com.w9jds.marketbot.ui.adapters.TabAdapter
import io.reactivex.subjects.BehaviorSubject

class ItemActivity: AppCompatActivity(), DataLoadingSubject.DataLoadingCallbacks {

    private val sharedPreferences: SharedPreferences = MarketBot.base.preferences()

    private val regionObservable: BehaviorSubject<Region> = BehaviorSubject.create()
    private var regionLoader: RegionsLoader = object : RegionsLoader(this) {
        override fun onRegionsLoaded(data: List<DataSnapshot>) {
            regionsAdapter.updateItems(data)
            binding.regionSpinner.setSelection(regionsAdapter.positions[regionId] ?: 0)
        }
    }

    // default to the forge
    private var regionId = sharedPreferences.getInt("regionId", 10000002)
    private lateinit var binding: ActivityMarketItemBinding
    private lateinit var regionsAdapter: RegionsAdapter
    private lateinit var tabAdapter: TabAdapter
    private lateinit var type: MarketType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_market_item)

        setSupportActionBar(binding.mainToolbar)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayShowTitleEnabled(false)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        handleIntent()
    }

    private fun handleIntent() {
        type = intent.getParcelableExtra("type")

        regionsAdapter = RegionsAdapter()
        regionLoader.loadRegions()

        tabAdapter = TabAdapter(supportFragmentManager, this, type, regionObservable)

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

    override fun dataStartedLoading() {

    }

    override fun dataFinishedLoading() {

    }

    override fun dataFailedLoading(errorMessage: String) {

    }


    override fun onNavigateUp(): Boolean {
        finishAfterTransition()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}