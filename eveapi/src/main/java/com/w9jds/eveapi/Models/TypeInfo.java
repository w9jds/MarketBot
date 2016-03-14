package com.w9jds.eveapi.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jeremy Shore on 3/3/16.
 */
public final class TypeInfo extends MarketItemBase {

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

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getPortionSize() {
        return portionSize;
    }

    public void setPortionSize(double portionSize) {
        this.portionSize = portionSize;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }
}
