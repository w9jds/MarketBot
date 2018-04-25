package com.w9jds.marketbot.ui.adapters

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.w9jds.marketbot.R
import io.reactivex.subjects.BehaviorSubject
import android.support.v7.util.DiffUtil
import com.bumptech.glide.Glide
import com.w9jds.marketbot.BR
import com.w9jds.marketbot.classes.models.market.MarketGroup
import com.w9jds.marketbot.classes.models.market.MarketType
import com.w9jds.marketbot.ui.ItemActivity
import com.w9jds.marketbot.utils.FirebaseDiffUtil


class GroupsAdapter(private val host: Activity, private val listener: BehaviorSubject<DataSnapshot>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val MARKET_GROUP_VIEW: Int = 0
    private val MARKET_TYPE_VIEW: Int = 1

    private val inflater: LayoutInflater = host.layoutInflater
    private var items: MutableList<DataSnapshot> = mutableListOf()

    inner class GroupHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val binding = DataBindingUtil.bind<ViewDataBinding>(itemView)

        val title: TextView = itemView.findViewById(R.id.title)
        val description: TextView = itemView.findViewById(R.id.description)
    }

    inner class TypeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title: TextView = itemView.findViewById(R.id.title)
        val icon: ImageView = itemView.findViewById(R.id.icon)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            MARKET_TYPE_VIEW -> createTypeHolder(parent)
            else -> createGroupHolder(parent)
        }
    }

    private fun createGroupHolder(parent: ViewGroup): GroupHolder {
        val view: View = inflater.inflate(R.layout.group_item_layout, parent, false)
        val holder = GroupHolder(view)

        holder.itemView.setOnClickListener {
            listener.onNext(items[holder.adapterPosition])
        }

        return holder
    }

    private fun createTypeHolder(parent: ViewGroup): TypeHolder {
        val view: View = inflater.inflate(R.layout.type_item_layout, parent, false)
        val holder = TypeHolder(view)

        holder.itemView.setOnClickListener {
            val type = items[holder.adapterPosition].getValue(MarketType::class.java)

            val intent = Intent()
            intent.setClass(host, ItemActivity::class.java)
            intent.putExtra("type", type)

            val options = ActivityOptions.makeSceneTransitionAnimation(
                    host,
                    android.util.Pair(host.findViewById(R.id.app_bar), host.getString(R.string.toolbar_transition_name))
            )

            ActivityCompat.startActivity(host, intent, options.toBundle())

        }

        return holder
    }

    override fun getItemViewType(position: Int): Int {
        val item = items[position]

        return if (item.hasChild("group_id")) {
            MARKET_TYPE_VIEW
        }
        else {
            MARKET_GROUP_VIEW
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            MARKET_GROUP_VIEW -> bindGroup(items[position].getValue(MarketGroup::class.java), holder as GroupHolder)
            MARKET_TYPE_VIEW -> bindType(items[position].getValue(MarketType::class.java), holder as TypeHolder)
        }
    }

    private fun bindGroup(group: MarketGroup?, holder: GroupHolder) {
        holder.binding?.setVariable(BR.marketGroup, group)

        if (group?.description.isNullOrBlank()) {
            holder.description.visibility = View.GONE
        }
    }

    private fun bindType(type: MarketType?, holder: TypeHolder) {
        holder.title.text = type?.name

        Glide.with(host)
            .load("https://imageserver.eveonline.com/Type/${type?.type_id}_64.png")
            .into(holder.icon)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItems(snapshots: List<DataSnapshot>) {
        val oldItems: MutableList<DataSnapshot> = ArrayList(items)
        val diffUtil = DiffUtil.calculateDiff(FirebaseDiffUtil(oldItems, snapshots))

        items.clear()
        items.addAll(snapshots)
        diffUtil.dispatchUpdatesTo(this)
    }
}