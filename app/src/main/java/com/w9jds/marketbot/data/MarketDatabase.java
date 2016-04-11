package com.w9jds.marketbot.data;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by w9jds on 4/10/2016.
 */
@Database(name = MarketDatabase.NAME, version = MarketDatabase.VERSION)
public class MarketDatabase {

    public static final String NAME = "MarketBotDb";
    public static final int VERSION = 3;

}
