package com.w9jds.marketbot.ui.adapters.bindings

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide

@BindingAdapter("typeId")
fun ImageView.setTypeId(typeId: Int) {
    Glide.with(context)
        .load("https://imageserver.eveonline.com/Type/${typeId}_64.png")
        .into(this)
}