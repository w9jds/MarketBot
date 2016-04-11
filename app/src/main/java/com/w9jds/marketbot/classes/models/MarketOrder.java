package com.w9jds.marketbot.classes.models;

import java.util.Date;

/**
 * Created by Jeremy on 3/1/2016.
 *
 * Modified by Alexander Whipp on 4/8/2016.
 */
public final class MarketOrder extends MarketItemBase {

    private boolean isMarginOrder;
    private boolean isBuyOrder;

    private Date issued;
    private double price;
    private int volume;
    private int volumeStart;
    private String range;
    private String href;
    private int duration;
    private String type;
    private String location;

    public String getHref() {
        return href;
    }

    public double getPrice() {
        return price;
    }

    public int getDuration() {
        return duration;
    }

    public int getVolume() {
        return volume;
    }

    public String getRange() {
        return range;
    }

    public int getVolumeStart() {
        return volumeStart;
    }

    public boolean isMarginOrder() {
        return isMarginOrder;
    }

    public boolean isBuyOrder() {
        return isBuyOrder;
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    public Date getIssued() {
        return issued;
    }

    public static class Builder {

        private long id;
        private boolean isMarginOrder;
        private boolean isBuyOrder;
        private Date issued;
        private double price;
        private int volume;
        private int volumeStart;
        private String range;
        private String href;
        private int duration;
        private String type;
        private String location;

        public void setId(long id) {
            this.id = id;
        }

        public Builder setHref(String href) {
            this.href = href;
            return this;
        }

        public Builder setBuyOrder(boolean buyOrder) {
            isBuyOrder = buyOrder;
            return this;
        }

        public Builder setDuration(int duration) {
            this.duration = duration;
            return this;
        }

        public Builder setIssued(Date issued) {
            this.issued = issued;
            return this;
        }

        public Builder setLocation(String location) {
            this.location = location;
            return this;
        }

        public Builder setMarginOrder(boolean marginOrder) {
            isMarginOrder = marginOrder;
            return this;
        }

        public Builder setPrice(double price) {
            this.price = price;
            return this;
        }

        public Builder setRange(String range) {
            this.range = range;
            return this;
        }

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public Builder setVolume(int volume) {
            this.volume = volume;
            return this;
        }

        public Builder setVolumeStart(int volumeStart) {
            this.volumeStart = volumeStart;
            return this;
        }

        public MarketOrder build() {
            MarketOrder order = new MarketOrder();
            order.id = this.id;
            order.isMarginOrder = this.isMarginOrder;
            order.isBuyOrder = this.isBuyOrder;
            order.issued = this.issued;
            order.price = this.price;
            order.volume = this.volume;
            order.volumeStart = this.volumeStart;
            order.range = this.range;
            order.href = this.href;
            order.duration = this.duration;
            order.type = this.type;
            order.location = this.location;

            return order;
        }
    }

}
