package com.w9jds.marketbot.ui

import android.app.SearchManager
import android.content.Intent
import android.databinding.DataBindingUtil

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.google.firebase.database.DataSnapshot
import com.w9jds.marketbot.R
import com.w9jds.marketbot.data.DataLoadingSubject
import com.w9jds.marketbot.data.loader.SearchLoader
import com.w9jds.marketbot.databinding.ActivitySearchBinding
import com.w9jds.marketbot.ui.adapters.GroupsAdapter
import kotlinx.android.synthetic.main.app_bar.view.*

class SearchActivity: AppCompatActivity(), DataLoadingSubject.DataLoadingCallbacks {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var adapter: GroupsAdapter
    private lateinit var loader: SearchLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)

        if (binding.baseView.toolbar != null) {
            setSupportActionBar(binding.baseView.toolbar)
        }

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        loader = object: SearchLoader(this) {
            override fun onDataLoaded(data: List<DataSnapshot>) {
                adapter.updateItems(data)
            }
        }

        binding.resultsList.layoutManager = LinearLayoutManager(this)
        binding.resultsList.itemAnimator = DefaultItemAnimator()

        adapter = GroupsAdapter(this, null)
        binding.resultsList.adapter = adapter

        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        if (intent != null) {
            handleIntent(intent)
        }
    }

    override fun dataStartedLoading() {
        binding.swipeRefresh.isRefreshing = true
    }

    override fun dataFinishedLoading() {
        binding.swipeRefresh.isRefreshing = false
    }

    override fun dataFailedLoading(errorMessage: String) {
        binding.swipeRefresh.isRefreshing = false
        Snackbar.make(binding.baseView, errorMessage, Snackbar.LENGTH_LONG).show()
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query: String = intent.getStringExtra(SearchManager.QUERY)
            supportActionBar?.title = query
            loader.loadSearchResults(query)
        }
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