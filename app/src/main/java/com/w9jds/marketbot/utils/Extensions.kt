package com.w9jds.marketbot.utils

import android.databinding.BindingAdapter
import android.os.Build
import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.w9jds.marketbot.classes.models.market.MarketOrder
import java.text.DecimalFormat

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

@BindingAdapter("iskValue")
fun TextView.setIskValue(price: Double) {
    val formatter = DecimalFormat("#,###.00")
    val formattedPrice = "${formatter.format(price)} ISK"
    text = formattedPrice
}

@BindingAdapter("volumeAmount")
fun TextView.setVolumeAmount(inventory: Long) {
    val formatter = DecimalFormat("#,###")
    text = formatter.format(inventory)
}

@BindingAdapter("buyRegion")
fun TextView.setBuyRegion(region: String) {
    text = when (region.toIntOrNull()) {
        null -> region.capitalize()
        else -> when (region.toInt()) {
            1 -> "$region Jump"
            else -> "$region Jumps"
        }
    }
}

@BindingAdapter("orderLocation")
fun TextView.setOrderLocation(order: MarketOrder) {
    val location: String? = order.location_name

    text = if (location.isNullOrBlank()) {
        "${order.system_name} - Unknown Structure"
    }
    else {
        location
    }
}


//    @BindingAdapter("bind:marginPercentage")
//    public static void setMarginPercentage(TextView textView, StationMargin margin) {
//
//        DecimalFormat format = new DecimalFormat("0.#");
//        String percentage = format.format(margin.getPercentage()) + "%";
//        textView.setText(percentage);
//
//        if (margin.getPercentage() <= 20) {
//            textView.setTextColor(0xFFFF0000);
//        }
//        else {
//            textView.setTextColor(0xFF00FF00);
//        }
//    }