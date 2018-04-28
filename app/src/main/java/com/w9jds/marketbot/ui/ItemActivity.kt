package com.w9jds.marketbot.ui

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.w9jds.marketbot.R
import com.w9jds.marketbot.classes.models.market.MarketType
import com.w9jds.marketbot.databinding.ActivityMarketItemBinding
import com.w9jds.marketbot.ui.adapters.TabAdapter
import com.w9jds.marketbot.ui.adapters.RegionAdapter

class ItemActivity: AppCompatActivity() {

//    private val regionObservable: BehaviorSubject<> = BehaviorSubject.create()

    private lateinit var binding: ActivityMarketItemBinding
    private lateinit var tabAdapter: TabAdapter
    private lateinit var type: MarketType
    private lateinit var adapter: RegionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_market_item)

        setSupportActionBar(binding.mainToolbar)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayShowTitleEnabled(false)
            supportActionBar?.setDisplayShowHomeEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeButtonEnabled(true)
        }



        handleIntent()
    }

    private fun handleIntent() {
        type = intent.getParcelableExtra("type")
        tabAdapter = TabAdapter(supportFragmentManager, this, type)

        binding.contentPager.adapter = tabAdapter
        binding.contentPager.offscreenPageLimit = 4
        binding.slidingTabs.setupWithViewPager(binding.contentPager)
    }

}