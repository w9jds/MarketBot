package com.w9jds.marketbot.ui

import android.app.SearchManager
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.database.DataSnapshot
import com.w9jds.marketbot.R
import com.w9jds.marketbot.data.DataLoadingSubject
import com.w9jds.marketbot.data.loader.GroupsLoader
import com.w9jds.marketbot.databinding.ActivityMainBinding
import com.w9jds.marketbot.ui.adapters.GroupsAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.app_bar.view.*

class MainActivity: AppCompatActivity(), DataLoadingSubject.DataLoadingCallbacks {

    private val parentListener: BehaviorSubject<DataSnapshot> = BehaviorSubject.create()

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: GroupsAdapter

    private var parent: DataSnapshot? = null
    private val loader: GroupsLoader = object : GroupsLoader(this) {
        override fun onDataLoaded(data: List<DataSnapshot>) {
            adapter.updateItems(data)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        if (binding.baseView.toolbar != null) {
            setSupportActionBar(binding.baseView.toolbar)
        }

        binding.marketGroups.layoutManager = LinearLayoutManager(this)
        binding.marketGroups.itemAnimator = DefaultItemAnimator()

        adapter = GroupsAdapter(this, parentListener)
        binding.marketGroups.adapter = adapter

        attachListeners()
        loadBaseGroups()
    }

    private fun loadBaseGroups() {
        parent = null
        binding.baseView.toolbar.title = " "
        loader.loadMarketGroups(null)
    }

    private fun onNextParent(snapshot: DataSnapshot) {
        parent = snapshot

        val id = snapshot.child("market_group_id").value as Long
        binding.baseView.toolbar.title = snapshot.child("name").value.toString()

        if (snapshot.hasChild("types")) {
            loader.loadMarketTypes(id)
        }
        else {
            loader.loadMarketGroups(id.toDouble())
        }
    }

    private fun attachListeners() {
        loader.registerLoadingCallback(this)
        parentListener.observeOn(AndroidSchedulers.mainThread())
            .doOnNext(this::onNextParent)
            .subscribe()
    }

    override fun dataStartedLoading() {

    }

    override fun dataFinishedLoading() {

    }

    override fun dataFailedLoading(errorMessage: String) {
        Snackbar.make(binding.baseView, errorMessage, Snackbar.LENGTH_LONG).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        if (menu != null) {
            val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
            val searchView = menu.findItem(R.id.search).actionView as SearchView
//            searchView.setSearchableInfo(
//                searchManager.getSearchableInfo(
//                    ComponentName(application, SearchActivity.class)
//                )
//            )
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

    override fun onBackPressed() {
        if (parent != null) {
            if (parent!!.hasChild("parent_group_id")) {
//                loader.loadMarketGroups(parent.child("parent_group_id"))
            }
            else {
                loadBaseGroups()
            }
        }
        else {
            super.onBackPressed()
        }
    }

}
