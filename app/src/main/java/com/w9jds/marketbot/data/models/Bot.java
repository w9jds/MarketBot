package com.w9jds.marketbot.data.models;

import android.os.PersistableBundle;

import com.w9jds.marketbot.data.storage.BotEntry;

/**
 * Created by Jeremy on 3/5/2016.
 */
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
