//package com.w9jds.marketbot.classes.models;
//
//public final class StationMargin {
//
//    double maxBuyPrice;
//    double maxSellPrice;
//    double margin;
//    double percentage;
//
//    String station;
//
//    public double getMargin() {
//        return margin;
//    }
//
//    public double getPercentage() {
//        return percentage;
//    }
//
//    public double getMaxBuyPrice() {
//        return maxBuyPrice;
//    }
//
//    public double getMaxSellPrice() {
//        return maxSellPrice;
//    }
//
//    public String getStation() {
//        return station;
//    }
//
//    public static class Builder {
//
//        double maxBuyPrice;
//        double maxSellPrice;
//        String station;
//
//        public Builder setMaxBuyPrice(double maxBuyPrice) {
//            this.maxBuyPrice = maxBuyPrice;
//            return this;
//        }
//
//        public Builder setMaxSellPrice(double maxSellPrice) {
//            this.maxSellPrice = maxSellPrice;
//            return this;
//        }
//
//        public Builder setStation(String station) {
//            this.station = station;
//            return this;
//        }
//
//        public StationMargin build() {
//            StationMargin margin = new StationMargin();
//            margin.maxBuyPrice = maxBuyPrice;
//            margin.maxSellPrice = maxSellPrice;
//            margin.margin = maxSellPrice - maxBuyPrice;
//            margin.percentage = margin.margin / maxBuyPrice;
//            margin.station = station;
//
//            return margin;
//        }
//    }
//
//}
