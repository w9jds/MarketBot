package com.w9jds.marketbot.ui

import android.app.SearchManager
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.w9jds.marketbot.R
import com.w9jds.marketbot.databinding.ActivitySearchBinding

class SearchActivity: AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)

        var layoutManager = LinearLayoutManager(this)

        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {

    }

    private fun createDataManager() {

    }

    private fun handleIntent(intent: Intent) {

        if (Intent.ACTION_SEARCH.equals(intent.action)) {
            val query: String = intent.getStringExtra(SearchManager.QUERY)

            actionBar.title = query
        }
    }


}