//package com.w9jds.marketbot.data;
//
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//import com.w9jds.marketbot.data.storage.BotEntry;
//import com.w9jds.marketbot.data.storage.MarketGroupEntry;
//import com.w9jds.marketbot.data.storage.MarketTypeEntry;
//import com.w9jds.marketbot.data.storage.MarketTypeInfoEntry;
//import com.w9jds.marketbot.data.storage.RegionEntry;
//
//public final class Database {
//
//    private static final int DATABASE_VERSION = 2;
//    private static final String DATABASE_NAME = "MarketBotDb";
//
//    private static Helper mDatabaseOpenHelper;
//
//    public static SQLiteOpenHelper getInstance(Context context) {
//        if (mDatabaseOpenHelper == null) {
//            mDatabaseOpenHelper = new Helper(context.getApplicationContext());
//        }
//
//        return mDatabaseOpenHelper;
//    }
//
//    private static class Helper extends SQLiteOpenHelper {
//
//        public Helper(Context context) {
//            super(context, DATABASE_NAME, null, DATABASE_VERSION);
//        }
//
//        @Override
//        public void onCreate(SQLiteDatabase database) {
//            database.beginTransaction();
//
//            database.execSQL(BotEntry.CREATE_TABLE);
//            database.execSQL(RegionEntry.CREATE_TABLE);
//            database.execSQL(MarketGroupEntry.CREATE_TABLE);
//            database.execSQL(MarketTypeEntry.CREATE_TABLE);
//            database.execSQL(MarketTypeInfoEntry.CREATE_TABLE);
//
//            database.setTransactionSuccessful();
//            database.endTransaction();
//        }
//
//        @Override
//        public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
//            database.beginTransaction();
//
//            database.execSQL("DROP TABLE IF EXISTS " + BotEntry.TABLE_NAME);
//            database.execSQL("DROP TABLE IF EXISTS " + RegionEntry.TABLE_NAME);
//            database.execSQL("DROP TABLE IF EXISTS " + MarketGroupEntry.TABLE_NAME);
//            database.execSQL("DROP TABLE IF EXISTS " + MarketTypeEntry.TABLE_NAME);
//            database.execSQL("DROP TABLE IF EXISTS " + MarketTypeInfoEntry.TABLE_NAME);
//
//            database.setTransactionSuccessful();
//            database.endTransaction();
//
//            onCreate(database);
//        }
//    }
//}