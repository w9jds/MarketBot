package com.w9jds.marketbot.ui

import android.app.SearchManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import com.w9jds.marketbot.R
import com.w9jds.marketbot.classes.MarketBot
import com.w9jds.marketbot.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.layout_app_bar.view.*

class MainActivity: AppCompatActivity(), LifecycleOwner {

    private val sharedPreference: SharedPreferences = MarketBot.base.preferences()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        if (binding.baseView.toolbar != null) {
            setSupportActionBar(binding.baseView.toolbar)
            supportActionBar?.title = " "
        }

        checkMarketStatus()
    }

    private fun checkMarketStatus() {

        when (sharedPreference.contains("lastSynced")) {
            true -> {

            }
            false -> {

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        if (menu != null) {
            val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
            val searchView = menu.findItem(R.id.search).actionView as SearchView

        }

        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
//        if (parent != null) {
//            if (parent!!.hasChild("parent_group_id")) {
//                val groupId = parent?.child("parent_group_id")?.value as Long
//
//                loader.getMarketGroup(groupId, {
//                    parent = it
//                    binding.baseView.toolbar.title = it?.child("name")?.value.toString()
//                    loader.loadMarketGroups(groupId.toDouble())
//                })
//            }
//            else {
//                loadBaseGroups()
//            }
//        }
//        else {
//            super.onBackPressed()
//        }
    }

}
