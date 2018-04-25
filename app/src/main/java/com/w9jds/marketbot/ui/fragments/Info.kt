package com.w9jds.marketbot.ui.fragments

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import com.w9jds.marketbot.BR
import com.w9jds.marketbot.R
import com.w9jds.marketbot.classes.models.market.MarketType
import com.w9jds.marketbot.databinding.FragmentTypeInfoBinding

class Info : Fragment() {

    private lateinit var binding: FragmentTypeInfoBinding
    private lateinit var type: MarketType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<FragmentTypeInfoBinding>(this.activity!!, R.layout.fragment_type_info)

        type = arguments?.getParcelable("type")!!
        binding.setVariable(BR.marketType, type)
    }

}