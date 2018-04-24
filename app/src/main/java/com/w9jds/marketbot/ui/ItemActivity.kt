package com.w9jds.marketbot.ui

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.w9jds.marketbot.R
import com.w9jds.marketbot.databinding.ActivityMarketItemBinding
import kotlinx.android.synthetic.main.app_bar.view.*

class ItemActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMarketItemBinding
//    private lateinit var adapter: RegionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_market_item)

        setSupportActionBar(binding.itemAppBar.toolbar)

        if (supportActionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false)
            actionBar.setDisplayShowHomeEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeButtonEnabled(true)
        }

    }

}