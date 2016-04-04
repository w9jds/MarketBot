package com.w9jds.marketbot.data.models;

import android.os.PersistableBundle;

import com.w9jds.marketbot.classes.StorageColumn;
import com.w9jds.marketbot.data.storage.BotEntry;

/**
 * Created by Jeremy on 3/5/2016.
 */
public final class Bot {

    @StorageColumn(BotEntry.COLUMN_INTERVAL)
    long interval;

    @StorageColumn(BotEntry.COLUMN_TYPE_ID)
    long typeId;

    @StorageColumn(BotEntry.COLUMN_REGION_ID)
    long regionId;

    @StorageColumn(BotEntry.COLUMN_THRESHOLD)
    double threshold;

    @StorageColumn(BotEntry.COLUMN_TYPE_HREF)
    String href;

    @StorageColumn(BotEntry.COLUMN_IS_ABOVE)
    boolean isAbove;

    @StorageColumn(BotEntry.COLUMN_IS_BUY)
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

    public PersistableBundle toPersistableBundle() {
        PersistableBundle bundle = new PersistableBundle();

        bundle.putLong(BotEntry.COLUMN_INTERVAL, this.interval);
        bundle.putLong(BotEntry.COLUMN_TYPE_ID, this.typeId);
        bundle.putLong(BotEntry.COLUMN_REGION_ID, this.regionId);
        bundle.putDouble(BotEntry.COLUMN_THRESHOLD, this.threshold);
        bundle.putString(BotEntry.COLUMN_TYPE_HREF, this.href);
        bundle.putInt(BotEntry.COLUMN_IS_ABOVE, this.isAbove ? 0 : 1);
        bundle.putInt(BotEntry.COLUMN_IS_BUY, this.isBuy ? 0 : 1);

        return bundle;
    }

    public static Bot fromPersistableBundle(PersistableBundle bundle) {
        Bot bot = new Bot();

        bot.interval = bundle.getLong(BotEntry.COLUMN_INTERVAL);
        bot.typeId = bundle.getLong(BotEntry.COLUMN_TYPE_ID);
        bot.regionId = bundle.getLong(BotEntry.COLUMN_REGION_ID);
        bot.threshold = bundle.getDouble(BotEntry.COLUMN_THRESHOLD);
        bot.href = bundle.getString(BotEntry.COLUMN_TYPE_HREF);
        bot.isAbove = bundle.getInt(BotEntry.COLUMN_IS_ABOVE) == 0;
        bot.isBuy = bundle.getInt(BotEntry.COLUMN_IS_BUY) == 0;

        return bot;
    }
}
