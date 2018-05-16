package com.w9jds.marketbot.ui

import android.app.SearchManager
import android.app.job.JobScheduler
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import com.w9jds.marketbot.R
import com.w9jds.marketbot.classes.MarketBot
import com.w9jds.marketbot.databinding.ActivityMainBinding
import com.w9jds.marketbot.ui.fragments.GroupsFragment
import com.w9jds.marketbot.ui.fragments.SearchFragment
import kotlinx.android.synthetic.main.layout_app_bar.view.*

class MainActivity: AppCompatActivity(), LifecycleOwner {

    private val sharedPreference: SharedPreferences = MarketBot.base.preferences()

    private lateinit var binding: ActivityMainBinding
    private lateinit var lifecycleRegistry: LifecycleRegistry

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        if (binding.baseView.toolbar != null) {
            setSupportActionBar(binding.baseView.toolbar)
            supportActionBar?.title = " "
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.content_container, GroupsFragment(), "market_fragment")
            .addToBackStack(null)
            .commit()

        lifecycleRegistry = LifecycleRegistry(this)
        lifecycleRegistry.markState(Lifecycle.State.CREATED)

        checkMarketStatus()
    }

    override fun onStart() {
        super.onStart()
        lifecycleRegistry.markState(Lifecycle.State.STARTED)
    }

    override fun onResume() {
        super.onResume()
        lifecycleRegistry.markState(Lifecycle.State.RESUMED)
    }

    private fun loadFragment() {

    }

    private fun checkMarketStatus() {
        val scheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler

        when (sharedPreference.contains("lastSynced")) {
            true -> {

            }
            false -> {
//                val info = JobInfo
//                    .Builder(1, ComponentName(packageName, MarketSync::class.qualifiedName))
//                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
//                    .build()
//
//                scheduler.schedule(info)
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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        when (intent?.action) {
            Intent.ACTION_SEARCH -> {
                supportFragmentManager.putFragment(intent.extras, "search_results", SearchFragment())
            }
            Intent.ACTION_DEFAULT -> {

            }
        }
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

    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }

}
