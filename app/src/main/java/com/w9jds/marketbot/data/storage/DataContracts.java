package com.w9jds.marketbot.data.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.w9jds.eveapi.Models.MarketGroup;
import com.w9jds.marketbot.classes.utils.StorageUtils;
import com.w9jds.marketbot.data.models.Bot;

import java.util.ArrayList;

public final class DataContracts {

    public static final class BotEntry implements BaseColumns {
        public static final String COLUMN_INTERVAL = "interval";
        public static final String COLUMN_TYPE_ID = "typeId";
        public static final String COLUMN_REGION_ID = "regionId";
        public static final String COLUMN_TYPE_HREF = "typeHref";
        public static final String COLUMN_THRESHOLD = "threshold";
        public static final String COLUMN_IS_BUY = "isBuy";
        public static final String COLUMN_IS_ABOVE = "isAbove";

        // Table name
        public static final String TABLE_NAME = "Bots";

        // Create Command
        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                + _ID + " INTEGER PRIMARY KEY,"
                + COLUMN_INTERVAL + " INTEGER,"
                + COLUMN_TYPE_ID + " INTEGER,"
                + COLUMN_REGION_ID + " INTEGER,"
                + COLUMN_THRESHOLD + " REAL,"
                + COLUMN_TYPE_HREF + " TEXT,"
                + COLUMN_IS_BUY + " BOOLEAN,"
                + COLUMN_IS_ABOVE + " BOOLEAN,"
                + " UNIQUE (" + _ID + ") ON CONFLICT REPLACE);";

        public static long createNewBot(Context context, Bot bot) {
            SQLiteDatabase database = new Database(context).getWritableDatabaseHelper();
            long newId;

            database.beginTransaction();

            ContentValues thisItem = new ContentValues();
            thisItem.put(COLUMN_INTERVAL, bot.getInterval());
            thisItem.put(COLUMN_TYPE_ID, bot.getTypeId());
            thisItem.put(COLUMN_REGION_ID, bot.getRegionId());
            thisItem.put(COLUMN_THRESHOLD, bot.getThreshold());
            thisItem.put(COLUMN_IS_ABOVE, bot.isCheckAboveThreshold());
            thisItem.put(COLUMN_IS_BUY, bot.isBuyOrder());
            thisItem.put(COLUMN_TYPE_HREF, bot.getTypeHref());

            newId = database.insertWithOnConflict(TABLE_NAME, null, thisItem, SQLiteDatabase.CONFLICT_IGNORE);

            database.setTransactionSuccessful();
            database.endTransaction();
            database.close();
            return newId;
        }

        public static Bot getBot(Context context, long id) {
            SQLiteDatabase database = new Database(context).getWritableDatabaseHelper();

            database.beginTransaction();

            ArrayList<Bot> bots = new ArrayList<>();

            Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME +
                    " WHERE " + _ID + "='" + id + "'", null);

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    bots.add(StorageUtils.buildClass(new Bot(), cursor));
                    cursor.moveToNext();
                }
            }

            database.setTransactionSuccessful();
            database.endTransaction();
            database.close();

            if (bots.size() > 0) {
                return bots.get(0);
            }
            else {
                return null;
            }
        }
    }

    public static final class MarketGroupEntry implements BaseColumns {
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_HREF = "href";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_PARENT_ID = "parentId";
        public static final String COLUMN_TYPES_LOCATION = "typesHref";

        // Table name
        public static final String TABLE_NAME = "MarketGroups";

        // Create Command
        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                + _ID + " INTEGER PRIMARY KEY,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_HREF + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_PARENT_ID + " INTEGER,"
                + COLUMN_TYPES_LOCATION + " TEXT,"
                + " UNIQUE (" + _ID + ") ON CONFLICT REPLACE);";

        public static long createNewMarketGroup(Context context, MarketGroup group) {
            SQLiteDatabase database = new Database(context).getWritableDatabaseHelper();
            long newId;

            database.beginTransaction();

            ContentValues thisItem = new ContentValues();
            thisItem.put(_ID, group.getId());
            thisItem.put(COLUMN_NAME, group.getName());
            thisItem.put(COLUMN_HREF, group.getHref());
            thisItem.put(COLUMN_DESCRIPTION, group.getDescription());
            thisItem.put(COLUMN_PARENT_ID, group.getParentGroupId());
            thisItem.put(COLUMN_TYPES_LOCATION, group.getTypesLocation());

            newId = database.insertWithOnConflict(TABLE_NAME, null, thisItem, SQLiteDatabase.CONFLICT_IGNORE);

            database.setTransactionSuccessful();
            database.endTransaction();
            database.close();
            return newId;
        }

        public static void createNewMarketGroups(SQLiteDatabase database, ArrayList<MarketGroup> groups) {
            long newId;

            database.beginTransaction();

            int count = groups.size();
            for (int i = 0; i < count; i++) {
                MarketGroup group = groups.get(i);

                ContentValues thisItem = new ContentValues();
                thisItem.put(_ID, group.getId());
                thisItem.put(COLUMN_NAME, group.getName());
                thisItem.put(COLUMN_HREF, group.getHref());
                thisItem.put(COLUMN_DESCRIPTION, group.getDescription());
                thisItem.put(COLUMN_PARENT_ID, group.getParentGroupId());
                thisItem.put(COLUMN_TYPES_LOCATION, group.getTypesLocation());

                newId = database.insertWithOnConflict(TABLE_NAME, null, thisItem, SQLiteDatabase.CONFLICT_IGNORE);
            }

            database.setTransactionSuccessful();
            database.endTransaction();
            database.close();
        }
    }
}