//package com.w9jds.eveapi.Models;
//
//import com.google.gson.annotations.SerializedName;
//
///**
// * Created by Jeremy Shore on 2/23/16.
// */
//public final class MarketPriceStorage {
//
//    @SerializedName("adjustedPrice")
//    private double adjusted;
//
//    @SerializedName("averagePrice")
//    private double average;
//
//    @SerializedName("type")
//    private Type type;
//
//    public double getAdjustedPrice() {
//        return this.adjusted;
//    }
//
//    public double getAveragePrice() {
//        return this.average;
//    }
//
//    public String getHref() {
//        return type.href;
//    }
//
//    public Integer getId() {
//        return type.getId();
//    }
//
//    public String getName() {
//        return type.getName();
//    }
//
//    private class Type extends MarketItemBase {
//
//        @SerializedName("href")
//        public String href;
//
//    }
//}
