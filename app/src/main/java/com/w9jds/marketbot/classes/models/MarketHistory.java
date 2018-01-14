//package com.w9jds.marketbot.classes.models;
//
//public final class MarketHistory {
//
//    private int orderCount;
//    private double lowPrice;
//    private double highPrice;
//    private double averagePrice;
//    private long volume;
//    private long recordDate;
//
//    public int getOrderCount() {
//        return orderCount;
//    }
//
//    public double getAveragePrice() {
//        return averagePrice;
//    }
//
//    public double getHighPrice() {
//        return highPrice;
//    }
//
//    public double getLowPrice() {
//        return lowPrice;
//    }
//
//    public long getVolume() {
//        return volume;
//    }
//
//    public long getRecordDate() {
//        return recordDate;
//    }
//
//    public static class Builder {
//
//        private int orderCount;
//        private double lowPrice;
//        private double highPrice;
//        private double averagePrice;
//        private long volume;
//        private long recordDate;
//
//        public Builder setOrderCount(int orderCount) {
//            this.orderCount = orderCount;
//            return this;
//        }
//
//        public Builder setAveragePrice(double averagePrice) {
//            this.averagePrice = averagePrice;
//            return this;
//        }
//
//        public Builder setHighPrice(double highPrice) {
//            this.highPrice = highPrice;
//            return this;
//        }
//
//        public Builder setLowPrice(double lowPrice) {
//            this.lowPrice = lowPrice;
//            return this;
//        }
//
//        public Builder setRecordDate(long recordDate) {
//            this.recordDate = recordDate;
//            return this;
//        }
//
//        public Builder setVolume(long volume) {
//            this.volume = volume;
//            return this;
//        }
//
//        public MarketHistory build() {
//            MarketHistory history = new MarketHistory();
//            history.orderCount = orderCount;
//            history.lowPrice = lowPrice;
//            history.highPrice = highPrice;
//            history.averagePrice = averagePrice;
//            history.volume = volume;
//            history.recordDate = recordDate;
//
//            return history;
//        }
//    }
//}
