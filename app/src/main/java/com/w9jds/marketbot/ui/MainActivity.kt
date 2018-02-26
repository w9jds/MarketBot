package com.w9jds.marketbot.ui

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.w9jds.marketbot.R
import com.w9jds.marketbot.databinding.ActivityMainBinding

class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        if (binding.activityHeader?.toolbar != null) {
            setSupportActionBar(binding.activityHeader!!.toolbar)
//            binding.activityHeader!!.toolbar.setTitle(" ")
        }



    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        val inflater: MenuInflater = getMenuInflater()
//        inflater.inflate(R.menu.main_menu, menu)
//
//        // Associate searchable configuration with the SearchView
//        val searchManager: SearchManager = getSystemService(Context.SEARCH_SERVICE)
//        val searchView: SearchView = menu.findItem(R.id.search).getActionView();
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(ComponentName(getApplication(), SearchActivity.class)))
//
//        return true;
//    }


}