package com.w9jds.marketbot.data.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.w9jds.eveapi.Models.TypeInfo;
import com.w9jds.marketbot.data.Database;

import java.util.List;

public final class MarketTypeInfoEntry implements BaseColumns {
        public static final String COLUMN_CAPACITY = "capacity";
        public static final String COLUMN_DESCRTIPION = "description";
        public static final String COLUMN_PORTION_SIZE = "portionSize";
        public static final String COLUMN_ICON_ID = "iconId";
        public static final String COLUMN_VOLUME = "volume";
        public static final String COLUMN_RADIUS = "radius";
        public static final String COLUMN_MASS = "mass";

        public static final String TABLE_NAME = "TypeInfo";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                + _ID + " INTEGER PRIMARY KEY,"
                + COLUMN_CAPACITY + " REAL,"
                + COLUMN_DESCRTIPION + " TEXT,"
                + COLUMN_ICON_ID + " INTEGER,"
                + COLUMN_PORTION_SIZE + " INTEGER,"
                + COLUMN_VOLUME + " REAL,"
                + COLUMN_RADIUS + " REAL,"
                + COLUMN_MASS + " REAL,"
                + " FOREIGN KEY(" + _ID + ") REFERENCES "
                + MarketTypeEntry.TABLE_NAME + " (" + MarketTypeEntry._ID + "),"
                + " UNIQUE (" + _ID + ") ON CONFLICT REPLACE);";

        public static void createNewTypeInfos(Context context, List<TypeInfo> infos) {
            SQLiteDatabase database = Database.getInstance(context).getWritableDatabase();
            database.beginTransaction();

            for (TypeInfo info : infos) {
                ContentValues thisItem = new ContentValues();
                thisItem.put(_ID, info.getId());
                thisItem.put(COLUMN_CAPACITY, info.getCapacity());
                thisItem.put(COLUMN_DESCRTIPION, info.getDescription());
                thisItem.put(COLUMN_MASS, info.getMass());
                thisItem.put(COLUMN_PORTION_SIZE, info.getPortionSize());
                thisItem.put(COLUMN_RADIUS, info.getRadius());
                thisItem.put(COLUMN_VOLUME, info.getVolume());

                database.insertWithOnConflict(TABLE_NAME, null, thisItem, SQLiteDatabase.CONFLICT_REPLACE);
            }

            database.setTransactionSuccessful();
            database.endTransaction();
        }
    }