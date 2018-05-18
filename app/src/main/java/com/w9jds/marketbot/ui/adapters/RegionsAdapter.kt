//package com.w9jds.marketbot.ui.adapters
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.BaseAdapter
//import android.widget.SpinnerAdapter
//import android.widget.TextView
//import com.google.firebase.database.DataSnapshot
//import com.w9jds.marketbot.R
//import com.w9jds.marketbot.classes.models.universe.Region
//
//class RegionsAdapter : BaseAdapter(), SpinnerAdapter {
//
//    private val items: MutableList<DataSnapshot> = mutableListOf()
//    val positions: HashMap<Int, Int> = HashMap()
//
//    inner class RegionViewHolder(itemView: View) {
//        val name: TextView = itemView.findViewById(R.id.region_name)
//    }
//
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
//        var view: View? = convertView
//        val holder: RegionViewHolder
//        val region: Region? = getItem(position)
//
//        if (view == null) {
//            view = LayoutInflater.from(parent?.context).inflate(R.layout.region_item_layout, parent, false)
//
//            holder = RegionViewHolder(view)
//            view.tag = holder
//        }
//        else {
//            holder = view.tag as RegionViewHolder
//        }
//
//        holder.name.text = region?.name
//        return view!!
//    }
//
//    override fun getItem(position: Int): Region? {
//        return items[position].getValue(Region::class.java)
//    }
//
//    override fun getItemId(position: Int): Long {
//        return items[position].key.toLong()
//    }
//
//    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
//        var view: View? = convertView
//        val holder: RegionViewHolder
//        val region: Region? = getItem(position)
//
//        if (view == null) {
//            view = LayoutInflater.from(parent?.context).inflate(R.layout.region_item_dropdown_layout, parent, false)
//
//            holder = RegionViewHolder(view)
//            view.tag = holder
//        }
//        else {
//            holder = view.tag as RegionViewHolder
//        }
//
//        holder.name.text = region?.name
//        return view!!
//    }
//
//    override fun getCount(): Int {
//        return items.count()
//    }
//
//    fun updateItems(snapshots: List<DataSnapshot>) {
//        items.clear()
//        positions.clear()
//
//        items.addAll(snapshots)
//        items.forEachIndexed { index, dataSnapshot -> positions[dataSnapshot.key.toInt()] = index }
//
//        notifyDataSetChanged()
//    }
//
//}