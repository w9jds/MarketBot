package com.w9jds.eveapi.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jeremy Shore on 3/3/16.
 */
public class TypeInfo extends MarketItemBase {

    @SerializedName("capacity")
    private double capacity;

    @SerializedName("description")
    private String description;

    @SerializedName("mass")
    private double mass;

    @SerializedName("radius")
    private double radius;

    @SerializedName("volume")
    private double volume;

    @SerializedName("portionSize")
    private double portionSize;

    public String getDescription() {
        return description;
    }

    public double getCapacity() {
        return capacity;
    }

    public double getMass() {
        return mass;
    }

    public double getPortionSize() {
        return portionSize;
    }

    public double getRadius() {
        return radius;
    }

    public double getVolume() {
        return volume;
    }
}
