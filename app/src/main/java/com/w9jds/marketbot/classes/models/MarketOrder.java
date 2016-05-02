package com.w9jds.marketbot.classes.models;

import java.util.Date;

public final class MarketOrder extends MarketItemBase {

    private boolean isBuyOrder;

    private Date issued;
    private double price;
    private long volume;
    private long volumeStart;
    private String range;
    private String href;
    private long duration;

    private String type;
    private long typeId;

    private String location;
    private long locationId;

    public String getHref() {
        return href;
    }

    public long getLocationId() {
        return locationId;
    }

    public long getTypeId() {
        return typeId;
    }

    public double getPrice() {
        return price;
    }

    public long getDuration() {
        return duration;
    }

    public long getVolume() {
        return volume;
    }

    public String getRange() {
        return range;
    }

    public long getVolumeStart() {
        return volumeStart;
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
        private boolean isBuyOrder;
        private Date issued;
        private double price;
        private long volume;
        private long volumeStart;
        private String range;
        private String href;
        private long duration;

        private String type;
        private long typeId;

        private String location;
        private long locationId;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setHref(String href) {
            this.href = href;
            return this;
        }

        public Builder setBuyOrder(boolean buyOrder) {
            isBuyOrder = buyOrder;
            return this;
        }

        public Builder setDuration(long duration) {
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

        public Builder setVolume(long volume) {
            this.volume = volume;
            return this;
        }

        public Builder setVolumeStart(long volumeStart) {
            this.volumeStart = volumeStart;
            return this;
        }

        public Builder setLocationId(long locationId) {
            this.locationId = locationId;
            return this;
        }

        public Builder setTypeId(long typeId) {
            this.typeId = typeId;
            return this;
        }

        public MarketOrder build() {
            MarketOrder order = new MarketOrder();
            order.id = this.id;
            order.isBuyOrder = this.isBuyOrder;
            order.issued = this.issued;
            order.price = this.price;
            order.volume = this.volume;
            order.volumeStart = this.volumeStart;
            order.range = this.range;
            order.href = this.href;
            order.duration = this.duration;
            order.type = this.type;
            order.typeId = this.typeId;
            order.location = this.location;
            order.locationId = this.locationId;

            return order;
        }
    }

}
