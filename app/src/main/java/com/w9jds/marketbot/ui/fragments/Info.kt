package com.w9jds.marketbot.ui.fragments

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.w9jds.marketbot.BR
import com.w9jds.marketbot.R
import com.w9jds.marketbot.classes.models.market.MarketType
import com.w9jds.marketbot.databinding.FragmentTypeInfoBinding

class Info : Fragment() {

    private lateinit var binding: FragmentTypeInfoBinding
    private lateinit var type: MarketType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        type = arguments?.getParcelable("type")!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_type_info, container, false)

        binding.setVariable(BR.marketType, type)

        return binding.root
    }

}