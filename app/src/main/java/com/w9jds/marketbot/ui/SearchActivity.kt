package com.w9jds.marketbot.ui

import android.app.SearchManager
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.w9jds.marketbot.R
import com.w9jds.marketbot.databinding.ActivitySearchBinding
import kotlinx.android.synthetic.main.app_bar.view.*

class SearchActivity: AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
//    private lateinit var searchLoader: SearchLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)

        if (binding.baseView.toolbar != null) {
            setSupportActionBar(binding.baseView.toolbar)
            binding.baseView.toolbar.title = ""
        }

        var layoutManager = LinearLayoutManager(this)
//        binding.marketGroups.layoutManager = layoutManager
//        binding.marketGroups.itemAnimator = DefaultItemAnimator()
//
//        var adapter = MarketGroupsAdapter(this)

        handleIntent(intent)

    }

    override fun onNewIntent(intent: Intent?) {
//        handleIntent(intent)
    }

    private fun createDataManager() {
//        searchLoader = new GroupsLoader(this) {
//            @Override
//            public void onProgressUpdate(int page, int totalPages, String message) {
//
//            }
//
//            @Override
//            public void onDataLoaded(List<? extends MarketItemBase> data) {
//                adapter.updateCollection(data);
//            }
//        };
//
//        searchLoader.registerLoadingCallback(this);
    }

    private fun handleIntent(intent: Intent) {
//        if (searchLoader == null) {
//            createDataManager()
//        }

        if (Intent.ACTION_SEARCH.equals(intent.action)) {
            val query: String = intent.getStringExtra(SearchManager.QUERY)

            actionBar.title = query
//            searchLoader.searchMarketTypes(query)
        }
    }


}