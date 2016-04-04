package com.w9jds.marketbot.data.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;

import com.w9jds.eveapi.Models.Reference;
import com.w9jds.eveapi.Models.Type;
import com.w9jds.eveapi.Models.TypeItem;
import com.w9jds.marketbot.data.Database;

import java.util.ArrayList;
import java.util.Collection;

public final class MarketTypeEntry implements BaseColumns {
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

    public static ArrayList<Type> getMarketTypes(Context context, long groupId) {
        SQLiteDatabase database = Database.getInstance(context).getReadableDatabase();
        ArrayList<Type> types = new ArrayList<>();

        database.beginTransaction();

        Cursor cursor = database.query(TABLE_NAME, null, COLUMN_GROUP_ID + "=?",
                new String[]{String.valueOf(groupId)}, null, null, null);

        if (cursor.moveToFirst()) {
            while(!cursor.isAfterLast()) {
                types.add(buildType(cursor));
                cursor.moveToNext();
            }
        }

        database.setTransactionSuccessful();
        database.endTransaction();
        database.close();
        return types;
    }

    @Nullable
    public static Type getType(Context context, long typeId) {
        SQLiteDatabase database = Database.getInstance(context).getReadableDatabase();
        ArrayList<Type> types = new ArrayList<>();

        database.beginTransaction();

        Cursor cursor = database.query(TABLE_NAME, null, _ID + "=?",
                new String[]{String.valueOf(typeId)}, null, null, null);

        if (cursor.moveToFirst()) {
            while(!cursor.isAfterLast()) {
                types.add(buildType(cursor));
                cursor.moveToNext();
            }
        }

        database.setTransactionSuccessful();
        database.endTransaction();
        database.close();

        if (types.size() > 0) {
            return types.get(0);
        }
        else {
            return null;
        }
    }

    private static Type buildType(Cursor cursor) {
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
        return type;
    }

    public static ArrayList<Type> searchMarketTypes(Context context, String queryString) {
        SQLiteDatabase database = Database.getInstance(context).getReadableDatabase();
        ArrayList<Type> types = new ArrayList<>();

        database.beginTransaction();

        Cursor cursor = database.query(TABLE_NAME, null, COLUMN_NAME + " LIKE ?",
                new String[]{"%" + queryString + "%"}, null, null, null);

        if (cursor.moveToFirst()) {
            while(!cursor.isAfterLast()) {
                types.add(buildType(cursor));
                cursor.moveToNext();
            }
        }

        database.setTransactionSuccessful();
        database.endTransaction();
        return types;
    }
}