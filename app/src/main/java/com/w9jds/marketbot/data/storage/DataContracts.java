package com.w9jds.marketbot.data.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.w9jds.eveapi.Models.MarketGroup;
import com.w9jds.eveapi.Models.Reference;
import com.w9jds.eveapi.Models.Type;
import com.w9jds.eveapi.Models.TypeInfo;
import com.w9jds.eveapi.Models.TypeItem;
import com.w9jds.marketbot.classes.utils.StorageUtils;
import com.w9jds.marketbot.data.models.Bot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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

        public static final List<String> columns = Arrays.asList(COLUMN_NAME, COLUMN_HREF,
                COLUMN_DESCRIPTION, COLUMN_PARENT_ID, COLUMN_TYPES_LOCATION);

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

        public static void createNewMarketGroups(Context context, Collection<MarketGroup> groups) {
            SQLiteDatabase database = new Database(context).getWritableDatabaseHelper();

            database.beginTransaction();

            for (MarketGroup group : groups) {
                ContentValues thisItem = new ContentValues();
                thisItem.put(_ID, group.getId());
                thisItem.put(COLUMN_NAME, group.getName());
                thisItem.put(COLUMN_HREF, group.getHref());
                thisItem.put(COLUMN_DESCRIPTION, group.getDescription());
                if (group.hasParent()) {
                    thisItem.put(COLUMN_PARENT_ID, group.getParentGroupId());
                }
                thisItem.put(COLUMN_TYPES_LOCATION, group.getTypesLocation());

                database.insertWithOnConflict(TABLE_NAME, null, thisItem, SQLiteDatabase.CONFLICT_REPLACE);
            }

            database.setTransactionSuccessful();
            database.endTransaction();
            database.close();
        }

        private static MarketGroup buildMarketGroup(Cursor cursor) {
            MarketGroup group = new MarketGroup();
            group.setId(cursor.getLong(cursor.getColumnIndex(_ID)));

            for (String column : columns) {
                int columnIndex = cursor.getColumnIndex(column);
                if (columnIndex != -1) {
                    switch(column) {
                        case COLUMN_NAME:
                            group.setName(cursor.getString(columnIndex));
                            break;
                        case COLUMN_PARENT_ID:
                            long id = cursor.getLong(columnIndex);

                            if (id != 0) {
                                group.setParentGroup(new Reference("https://public-crest.eveonline.com/market/groups/" +
                                        id + "/"));
                            }
                            break;
                        case COLUMN_HREF:
                            group.setHref(cursor.getString(columnIndex));
                            break;
                        case COLUMN_TYPES_LOCATION:
                            group.setTypes(new Reference(cursor.getString(columnIndex)));
                            break;
                        case COLUMN_DESCRIPTION:
                            group.setDescription(cursor.getString(columnIndex));
                            break;
                    }
                }
            }

            return group;
        }

        public static ArrayList<MarketGroup> getMarketGroupsforParent(Context context, Long parentId) {
            SQLiteDatabase database = new Database(context).getReadableDatabaseHelper();

            ArrayList<MarketGroup> groups = new ArrayList<>();
            String query = "SELECT * FROM " + TABLE_NAME + " WHERE ";

            if (parentId == null) {
                query += COLUMN_PARENT_ID + " IS NULL";
            }
            else {
                query += COLUMN_PARENT_ID + "='" + parentId + "'";
            }

            database.beginTransaction();
            Cursor cursor = database.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                while(!cursor.isAfterLast()) {
                    groups.add(buildMarketGroup(cursor));
                    cursor.moveToNext();
                }
            }

            database.setTransactionSuccessful();
            database.endTransaction();
            database.close();
            return groups;
        }

        public static MarketGroup getMarketGroup(Context context, long id) {
            SQLiteDatabase database = new Database(context).getReadableDatabaseHelper();
            ArrayList<MarketGroup> groups = new ArrayList<>();

            database.beginTransaction();
            Cursor cursor = database.query(TABLE_NAME, null, _ID + "=?",
                    new String[]{String.valueOf(id)}, null, null, null);

            if (cursor.moveToFirst()) {
                while(!cursor.isAfterLast()) {
                    groups.add(buildMarketGroup(cursor));
                    cursor.moveToNext();
                }
            }

            database.setTransactionSuccessful();
            database.endTransaction();
            database.close();
            return groups.get(0);
        }
    }

    public static final class MarketTypeEntry implements BaseColumns {
        public static final String COLUMN_GROUP_ID = "groupId";
        public static final String COLUMN_HREF = "href";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ICON_LOC = "iconLoc";

        public static final String TABLE_NAME = "MarketTypes";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                + _ID + " INTEGER PRIMARY KEY,"
                + COLUMN_GROUP_ID + " INTEGER,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_HREF + " TEXT,"
                + COLUMN_ICON_LOC + " TEXT,"
                + " FOREIGN KEY (" + COLUMN_GROUP_ID + ") REFERENCES "
                + MarketGroupEntry.TABLE_NAME + " (" + MarketGroupEntry._ID + "),"
                + " UNIQUE (" + _ID + ") ON CONFLICT REPLACE);";

        public static void createNewMarketTypes(Context context, Collection<Type> types) {
            SQLiteDatabase database = new Database(context).getWritableDatabaseHelper();

            database.beginTransaction();

            for (Type type : types) {
                ContentValues thisItem = new ContentValues();
                thisItem.put(_ID, type.getId());
                thisItem.put(COLUMN_GROUP_ID, type.marketGroup.getId());
                thisItem.put(COLUMN_NAME, type.getName());
                thisItem.put(COLUMN_HREF, type.getHref());
                thisItem.put(COLUMN_ICON_LOC, type.getIconLink());

                database.insertWithOnConflict(TABLE_NAME, null, thisItem, SQLiteDatabase.CONFLICT_REPLACE);
            }

            database.setTransactionSuccessful();
            database.endTransaction();
            database.close();
        }

        public static ArrayList<Type> getMarketTypes(Context context, long groupId) {
            SQLiteDatabase database = new Database(context).getReadableDatabaseHelper();
            ArrayList<Type> types = new ArrayList<>();

            database.beginTransaction();

            Cursor cursor = database.query(TABLE_NAME, null, COLUMN_GROUP_ID + "=?",
                    new String[]{String.valueOf(groupId)}, null, null, null);

            if (cursor.moveToFirst()) {
                while(!cursor.isAfterLast()) {
                    Type type = new Type();
                    type.setId(cursor.getLong(cursor.getColumnIndex(_ID)));

                    TypeItem item = new TypeItem();
                    item.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                    item.setHref(cursor.getString(cursor.getColumnIndex(COLUMN_HREF)));
                    item.setIcon(new Reference(cursor.getString(cursor.getColumnIndex(COLUMN_ICON_LOC))));
                    type.setType(item);
                    long marketGroupId = cursor.getLong(cursor.getColumnIndex(COLUMN_GROUP_ID));

                    Reference group = new Reference("https://public-crest.eveonline.com/market/groups/" + marketGroupId + "/");
                    group.setId(marketGroupId);
                    type.setMarketGroup(group);

                    types.add(type);
                    cursor.moveToNext();
                }
            }

            database.setTransactionSuccessful();
            database.endTransaction();
            database.close();
            return types;
        }
    }

    public static final class MarketTypeInfoEntry implements BaseColumns {
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
            SQLiteDatabase database = new Database(context).getWritableDatabaseHelper();

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
            database.close();
        }
    }
}