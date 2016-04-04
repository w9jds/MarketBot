package com.w9jds.marketbot.data.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.w9jds.eveapi.Models.MarketGroup;
import com.w9jds.eveapi.Models.Reference;
import com.w9jds.marketbot.data.Database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public final class MarketGroupEntry implements BaseColumns {
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
        SQLiteDatabase database = Database.getInstance(context).getReadableDatabase();
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
        SQLiteDatabase database = Database.getInstance(context).getReadableDatabase();
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
