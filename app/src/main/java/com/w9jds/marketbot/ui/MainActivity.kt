package com.w9jds.marketbot.ui

import android.app.SearchManager
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import com.w9jds.marketbot.R
import com.w9jds.marketbot.data.loader.GroupsLoader
import com.w9jds.marketbot.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.app_bar.view.*

class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        if (binding.baseView.toolbar != null) {
            setSupportActionBar(binding.baseView.toolbar)
            binding.baseView.toolbar.title = ""
        }

        var layoutManager = LinearLayoutManager(this)
        binding.marketGroups.layoutManager = layoutManager
        binding.marketGroups.itemAnimator = DefaultItemAnimator()

//        var groupLoader =


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        if (menu != null) {
            // Associate searchable configuration with the SearchView
            val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
            val searchView = menu.findItem(R.id.search).actionView as SearchView
//            searchView.setSearchableInfo(searchManager.getSearchableInfo(ComponentName(application, SearchActivity.class)))

        }

        return true
    }

}