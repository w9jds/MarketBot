package com.w9jds.marketbot.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.w9jds.marketbot.BR
import com.w9jds.marketbot.R
import com.w9jds.marketbot.classes.models.universe.Region

class RegionsAdapter(diffCallback: DiffUtil.ItemCallback<Region>) : ListAdapter<Region, RegionsAdapter.RegionViewHolder>(diffCallback) {

    inner class RegionViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val binding = DataBindingUtil.bind<ViewDataBinding>(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegionViewHolder {
        return createRegionHolder(parent)
    }

    override fun onBindViewHolder(holder: RegionViewHolder, position: Int) {

    }

    private fun createRegionHolder(parent: ViewGroup): RegionViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_item_region, parent, false)
        val holder = RegionViewHolder(view)

        holder.itemView.setOnClickListener {

        }

        return holder
    }

    private fun bindRegion(holder: RegionViewHolder, region: Region) {
        holder.binding?.setVariable(BR.region, region)
    }

}