package com.w9jds.marketbot.data;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = MarketDatabase.NAME, version = MarketDatabase.VERSION)
public class MarketDatabase {

    public static final String NAME = "MarketBotDb";
    public static final int VERSION = 3;


    public interface TransactionListener {
        void onTransactionProgressUpdate(int progress, int max);
    }
}
