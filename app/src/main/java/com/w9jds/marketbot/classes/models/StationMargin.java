package com.w9jds.marketbot.classes.models;

import java.util.Date;

/**
 * Created by Jeremy Shore on 4/24/16.
 */
public class StationMargin {

    double maxBuyPrice;
    double maxSellPrice;
    double margin;
    double percentage;

    String station;

    public double getMargin() {
        return margin;
    }

    public double getPercentage() {
        return percentage;
    }

    public double getMaxBuyPrice() {
        return maxBuyPrice;
    }

    public double getMaxSellPrice() {
        return maxSellPrice;
    }

    public String getStation() {
        return station;
    }

    public static class Builder {

        double maxBuyPrice;
        double maxSellPrice;

        String station;

//        public Builder setMargin(double margin) {
//            this.margin = margin;
//            return this;
//        }

        public Builder setMaxBuyPrice(double maxBuyPrice) {
            this.maxBuyPrice = maxBuyPrice;
            return this;
        }

        public Builder setMaxSellPrice(double maxSellPrice) {
            this.maxSellPrice = maxSellPrice;
            return this;
        }

//        public Builder setPercentage(double percentage) {
//            this.percentage = percentage;
//            return this;
//        }

        public Builder setStation(String station) {
            this.station = station;
            return this;
        }

        public StationMargin build() {
            StationMargin margin = new StationMargin();
            margin.maxBuyPrice = maxBuyPrice;
            margin.maxSellPrice = maxSellPrice;
            margin.margin = maxSellPrice - maxBuyPrice;
            margin.percentage = (margin.margin / maxBuyPrice) * 100;
            margin.station = station;

            return margin;
        }
    }

}
