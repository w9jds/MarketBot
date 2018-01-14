//package com.w9jds.marketbot.ui.adapters.bindings;
//
//import android.databinding.BindingAdapter;
//import android.widget.TextView;
//import com.w9jds.marketbot.classes.models.StationMargin;
//import java.text.DecimalFormat;
//
//public class MarginAdapters {
//
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
//
//    @BindingAdapter("bind:formattedPrice")
//    public static void setFormattedPrice(TextView textView, double price) {
//        DecimalFormat formatter = new DecimalFormat("#,###.00");
//        String formattedPrice = formatter.format(price) + " ISK";
//
//        textView.setText(formattedPrice);
//    }
//
//}
