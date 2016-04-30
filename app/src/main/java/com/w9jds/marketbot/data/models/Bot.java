package com.w9jds.marketbot.data.models;

public final class Bot {

    long interval;
    long typeId;
    long regionId;
    double threshold;
    String href;
    boolean isAbove;
    boolean isBuy;

    public boolean isBuyOrder() {
        return isBuy;
    }

    public boolean isCheckAboveThreshold() {
        return isAbove;
    }

    public String getTypeHref() {
        return href;
    }

    public long getTypeId() {
        return typeId;
    }

    public long getRegionId() {
        return regionId;
    }

    public long getInterval() {
        return interval;
    }

    public double getThreshold() {
        return threshold;
    }

}
