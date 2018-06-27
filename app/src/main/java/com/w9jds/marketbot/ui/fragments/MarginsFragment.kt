package com.w9jds.marketbot.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.w9jds.marketbot.R
import com.w9jds.marketbot.classes.models.market.MarketType
import com.w9jds.marketbot.databinding.FragmentListBinding

class MarginsFragment: Fragment() {

    private lateinit var binding: FragmentListBinding
    private lateinit var type: MarketType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        type = arguments?.getParcelable("type")!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_list, container, false)

        binding = DataBindingUtil.bind(view)!!

        return view
    }

}