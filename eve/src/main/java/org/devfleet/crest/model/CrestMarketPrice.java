package org.devfleet.crest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CrestMarketPrice extends CrestEntity {

    @JsonProperty
    private double adjustedPrice;

    @JsonProperty
    private double averagePrice;

    @JsonProperty
    private CrestItem type;

    public double getAdjustedPrice() {
        return adjustedPrice;
    }

    public double getAveragePrice() {
        return averagePrice;
    }

    public CrestItem getType() {
        return type;
    }
}
