package com.w9jds.marketbot.data.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.w9jds.eveapi.Models.Region;
import com.w9jds.marketbot.data.Database;

import java.util.ArrayList;

/**
 * Created by w9jds on 4/3/2016.
 */
public final class RegionEntry implements BaseColumns {
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_HREF = "href";

    public static final String TABLE_NAME = "Regions";

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
            + _ID + " INTEGER PRIMARY KEY,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_HREF + " TEXT,"
            + " UNIQUE (" + _ID + ") ON CONFLICT REPLACE);";

    private static Region buildRegion(Cursor cursor) {
        Region region = new Region();
        region.setHref(cursor.getString(cursor.getColumnIndex(COLUMN_HREF)));
        region.setId(cursor.getLong(cursor.getColumnIndex(_ID)));
        region.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));

        return region;
    }

    public static ArrayList<Region> getRegions(Context context, boolean includeWormholes) {
        SQLiteDatabase database = Database.getInstance(context).getReadableDatabase();
        database.beginTransaction();
        ArrayList<Region> regions = new ArrayList<>();

        Cursor cursor;
        if (includeWormholes) {
            cursor = database.query(TABLE_NAME, null, null, null, null, null, COLUMN_NAME);
        }
        else {
            cursor = database.query(TABLE_NAME, null, COLUMN_NAME + " NOT GLOB ?",
                    new String[]{ "*[A-Z]-R*" }, null, null, COLUMN_NAME);
        }

        if (cursor.moveToFirst()) {
            while(!cursor.isAfterLast()) {
                regions.add(buildRegion(cursor));
                cursor.moveToNext();
            }
        }

        database.setTransactionSuccessful();
        database.endTransaction();
        database.close();
        return regions;
    }

}
