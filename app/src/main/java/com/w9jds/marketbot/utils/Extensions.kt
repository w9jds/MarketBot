package com.w9jds.marketbot.utils

import android.databinding.BindingAdapter
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

@BindingAdapter("typeId")
fun ImageView.setTypeId(typeId: Int) {
    Glide.with(context)
            .load("https://imageserver.eveonline.com/Type/${typeId}_64.png")
            .into(this)
}

@BindingAdapter("htmlText")
fun TextView.setHtmlText(html: String) {
    text = if (Build.VERSION.SDK_INT < 24) {
        Html.fromHtml(html)
    } else {
        Html.fromHtml(html, paintFlags)
    }
}