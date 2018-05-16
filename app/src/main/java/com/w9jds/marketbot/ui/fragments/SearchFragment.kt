package com.w9jds.marketbot.ui.fragments

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.w9jds.marketbot.R
import com.w9jds.marketbot.data.model.GroupsModel
import com.w9jds.marketbot.databinding.FragmentListBinding
import com.w9jds.marketbot.ui.MainActivity
import com.w9jds.marketbot.ui.adapters.GroupsAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy

class SearchFragment: Fragment(), LifecycleOwner {

    private lateinit var binding: FragmentListBinding
    private lateinit var adapter: GroupsAdapter
    private lateinit var model: GroupsModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model = ViewModelProviders.of(this).get(GroupsModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = DataBindingUtil.bind(view)!!

        binding.itemsList.layoutManager = LinearLayoutManager(view.context)
        binding.itemsList.itemAnimator = DefaultItemAnimator()

        adapter = GroupsAdapter(fragmentManager!!, null)
        binding.itemsList.adapter = adapter

        attachListeners()
    }

    private fun attachListeners() {
        model.groups.observe(this, Observer {
            adapter.updateItems(it ?: emptyList())
        })

        model.isLoading.observe(this, Observer {
            when (it) {
                true -> binding.contentLoading.show()
                false -> binding.contentLoading.hide()
            }
        })
    }



//    override fun dataStartedLoading() {
//        binding.contentLoading.show()
//    }
//
//    override fun dataFinishedLoading() {
//        binding.contentLoading.hide()
//    }
//
//    override fun dataFailedLoading(errorMessage: String) {
//        binding.contentLoading.hide()
//    }

}