package com.w9jds.marketbot.ui

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.database.*
import com.w9jds.marketbot.R
import com.w9jds.marketbot.databinding.ActivityMainBinding
import com.w9jds.marketbot.ui.adapters.GroupsAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.app_bar.view.*

class MainActivity: AppCompatActivity() {

    var database: FirebaseDatabase = FirebaseDatabase.getInstance()

    private val parentListener: BehaviorSubject<DataSnapshot> = BehaviorSubject.create()

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: GroupsAdapter

    private var parent: DataSnapshot? = null
    private lateinit var groupQuery: Query

//    init {
//        MarketBot.base.inject(this)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        database.setPersistenceEnabled(true)
        database.getReference("groups").keepSynced(true)
        database.getReference("types").keepSynced(true)
        database.getReference("regions").keepSynced(true)

        if (binding.baseView.toolbar != null) {
            setSupportActionBar(binding.baseView.toolbar)
            binding.baseView.toolbar.title = " "
        }

        adapter = GroupsAdapter(this, parentListener)

        binding.marketGroups.layoutManager = LinearLayoutManager(this)
        binding.marketGroups.itemAnimator = DefaultItemAnimator()
        binding.marketGroups.adapter = adapter

        parentListener
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                parent = it
                val id = it.child("market_group_id").value as Long

                binding.baseView.toolbar.title = it.child("name").value.toString()

                if (it.hasChild("types")) {

                }
                else {
                    groupQuery.equalTo(id.toDouble()).addListenerForSingleValueEvent(valueHandler)
                }
            }
            .subscribe()

        groupQuery.equalTo(null).addListenerForSingleValueEvent(valueHandler)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        if (menu != null) {
//            val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
//            val searchView = menu.findItem(R.id.search).actionView as SearchView
//            searchView.setSearchableInfo(
//                searchManager.getSearchableInfo(
//                    ComponentName(application, SearchActivity.class)
//                )
//            )
        }

        return true
    }

    private val valueHandler = object: ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot?) {
            adapter.updateItems(snapshot?.children?.distinct() ?: emptyList())
        }

        override fun onCancelled(error: DatabaseError?) {
            adapter.updateItems(emptyList())
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (parent != null) {
            if (parent!!.hasChild("parent_group_id")) {

            }
            else {
                groupQuery.equalTo(null).addListenerForSingleValueEvent(valueHandler)
            }
        }
        else {
            super.onBackPressed()
        }
    }

}
