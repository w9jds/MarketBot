package com.w9jds.marketbot.ui.fragments

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DataSnapshot
import com.w9jds.marketbot.R
import com.w9jds.marketbot.data.model.GroupsModel
import com.w9jds.marketbot.databinding.FragmentMarketBinding
import com.w9jds.marketbot.ui.MainActivity
import com.w9jds.marketbot.ui.adapters.GroupsAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject

class GroupsFragment: Fragment(), LifecycleOwner {

    private val parentListener: BehaviorSubject<DataSnapshot> = BehaviorSubject.create()
    private lateinit var binding: FragmentMarketBinding
    private lateinit var adapter: GroupsAdapter
    private lateinit var model: GroupsModel

    private var parent: DataSnapshot? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model = ViewModelProviders.of(this).get(GroupsModel::class.java)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_market, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = DataBindingUtil.bind(view)!!
        adapter = GroupsAdapter(fragmentManager!!, parentListener)

        binding.itemsList.layoutManager = LinearLayoutManager(activity)
        binding.itemsList.itemAnimator = DefaultItemAnimator()
        binding.itemsList.adapter = adapter

        attachListeners()
        loadBaseGroups()
    }

    private fun loadBaseGroups() {
        parent = null
        model.loadMarketGroups(null)
    }

    private fun onNextParent(snapshot: DataSnapshot) {
        if (model.isLoading.value == false) {
            parent = snapshot
            val id = snapshot.child("market_group_id").value as Long


            if (snapshot.hasChild("types")) {
                model.loadMarketTypes(id)
            }
            else {
                model.loadMarketGroups(id.toDouble())
            }
        }
    }

    private fun attachListeners() {
        model.groups.observe(this, Observer {
            if (parent != null) {
                (activity as MainActivity).supportActionBar?.title = parent?.child("name")?.value.toString()
            }

            adapter.updateItems(it ?: emptyList())
        })

        model.isLoading.observe(this, Observer {
            when (it) {
                true -> binding.contentLoading.show()
                false -> binding.contentLoading.hide()
            }
        })

        parentListener.observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = this::onNextParent
            )
    }

}