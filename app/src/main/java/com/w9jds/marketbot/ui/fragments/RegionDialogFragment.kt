package com.w9jds.marketbot.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.w9jds.marketbot.R
import com.w9jds.marketbot.databinding.FragmentRegionDialogBinding
import com.w9jds.marketbot.ui.adapters.RegionsAdapter

class RegionDialogFragment: BottomSheetDialogFragment() {

    private lateinit var binding: FragmentRegionDialogBinding
    private lateinit var adapter: RegionsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_region_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = DataBindingUtil.bind(view)!!

        binding.regionsList.layoutManager = LinearLayoutManager(activity)
        binding.regionsList.itemAnimator = DefaultItemAnimator()
        binding.regionsList.adapter = adapter

    }

}