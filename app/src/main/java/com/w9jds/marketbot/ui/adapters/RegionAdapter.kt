//package com.w9jds.marketbot.ui.adapters
//
//import android.app.Activity
//import android.support.v7.widget.RecyclerView
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import com.google.firebase.database.DataSnapshot
//import com.w9jds.marketbot.R
//import io.reactivex.subjects.BehaviorSubject
//import android.support.v7.util.DiffUtil
//import com.bumptech.glide.Glide
//import com.w9jds.marketbot.classes.models.market.MarketGroup
//import com.w9jds.marketbot.classes.models.market.MarketType
//import com.w9jds.marketbot.utils.FirebaseDiffUtil
//
//
//class RegionAdapter(private val host: Activity, private val listener: BehaviorSubject<DataSnapshot>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//
//
//    private val inflater: LayoutInflater = host.layoutInflater
//    private var items: List<DataSnapshot> = emptyList()
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        return when (viewType) {
//
//        }
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        val item = items[position]
//
//    }
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        when (getItemViewType(position)) {
//
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return items.size
//    }
//
//    fun updateItems(snapshots: List<DataSnapshot>) {
//        val old: List<DataSnapshot>  = ArrayList(items)
//
//        items = snapshots
//
//        val diffUtil = DiffUtil.calculateDiff(FirebaseDiffUtil(items, old))
//        diffUtil.dispatchUpdatesTo(this)
//    }
//
//}