package com.w9jds.marketbot.data.models;

import android.os.PersistableBundle;

import com.w9jds.marketbot.classes.StorageColumn;
import com.w9jds.marketbot.data.DataContracts.MarketBotEntry;

/**
 * Created by Jeremy on 3/5/2016.
 */
public class MarketBot {

    @StorageColumn(MarketBotEntry.COLUMN_INTERVAL)
    long interval;

    @StorageColumn(MarketBotEntry.COLUMN_TYPE_ID)
    long typeId;

    @StorageColumn(MarketBotEntry.COLUMN_REGION_ID)
    long regionId;

    @StorageColumn(MarketBotEntry.COLUMN_THRESHOLD)
    double threshold;

    @StorageColumn(MarketBotEntry.COLUMN_TYPE_HREF)
    String href;

    @StorageColumn(MarketBotEntry.COLUMN_IS_ABOVE)
    boolean isAbove;

    @StorageColumn(MarketBotEntry.COLUMN_IS_BUY)
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

        bundle.putLong(MarketBotEntry.COLUMN_INTERVAL, this.interval);
        bundle.putLong(MarketBotEntry.COLUMN_TYPE_ID, this.typeId);
        bundle.putLong(MarketBotEntry.COLUMN_REGION_ID, this.regionId);
        bundle.putDouble(MarketBotEntry.COLUMN_THRESHOLD, this.threshold);
        bundle.putString(MarketBotEntry.COLUMN_TYPE_HREF, this.href);
        bundle.putInt(MarketBotEntry.COLUMN_IS_ABOVE, this.isAbove ? 0 : 1);
        bundle.putInt(MarketBotEntry.COLUMN_IS_BUY, this.isBuy ? 0 : 1);

        return bundle;
    }

    public static MarketBot fromPersistableBundle(PersistableBundle bundle) {
        MarketBot bot = new MarketBot();

        bot.interval = bundle.getLong(MarketBotEntry.COLUMN_INTERVAL);
        bot.typeId = bundle.getLong(MarketBotEntry.COLUMN_TYPE_ID);
        bot.regionId = bundle.getLong(MarketBotEntry.COLUMN_REGION_ID);
        bot.threshold = bundle.getDouble(MarketBotEntry.COLUMN_THRESHOLD);
        bot.href = bundle.getString(MarketBotEntry.COLUMN_TYPE_HREF);
        bot.isAbove = bundle.getInt(MarketBotEntry.COLUMN_IS_ABOVE) == 0;
        bot.isBuy = bundle.getInt(MarketBotEntry.COLUMN_IS_BUY) == 0;

        return bot;
    }
}
