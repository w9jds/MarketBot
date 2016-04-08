package com.w9jds.eveapi.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jeremy on 3/1/2016.
 *
 * Modified by Alexander Whipp on 4/8/2016.
 */

public final class MarketOrder extends MarketItemBase {

    @SerializedName("buy")
    private boolean isBuyOrder;

    @SerializedName("margin")
    private boolean isMarginOrder;

//    @SerializedName("issued")
//    private Date issued;

    @SerializedName("price")
    private double price;

    @SerializedName("volume")
    private int volume;

    @SerializedName("volumeEntered")
    private int volumeStart;

    @SerializedName("range")
    private String range;

    @SerializedName("href")
    private String href;

    @SerializedName("duration")
    private int duration;

    @SerializedName("type")
    private Reference type;

    @SerializedName("location")
    private Reference location;


    public String getHref() {
        return href;
    }

//    public Date getIssued() {
//        return issued;
//    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) { this.price = price; }

    public int getDuration() {
        return duration;
    }

    public int getVolume() {
        return volume;
    }

    public Reference getLocation() {
        return location;
    }

    public Reference getType() {
        return type;
    }

    public String getRange() {
        return range;
    }

    public int getVolumeStart() {
        return volumeStart;
    }

    public boolean isBuyOrder() {
        return isBuyOrder;
    }

    public void setIsMarginOrder(boolean isMarginOrder){
        this.isMarginOrder = isMarginOrder;
    }

    public boolean isMarginOrder() {
        return isMarginOrder;
    }




}
