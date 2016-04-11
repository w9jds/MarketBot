package com.w9jds.marketbot.data.storage;

import android.accounts.Account;
import android.provider.BaseColumns;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.w9jds.marketbot.data.MarketDatabase;

@Table(database = MarketDatabase.class)
public final class MarketGroupEntry extends BaseModel {

    @PrimaryKey
    long id;

    @Column
    String name;

    @Column
    String href;

    @Column
    String description;

    @Column
    long parentId;

    @Column
    String location;

//    private static MarketGroup buildMarketGroup(Cursor cursor) {
//        MarketGroup group = new MarketGroup();
//        group.setId(cursor.getLong(cursor.getColumnIndex(_ID)));
//
//        for (String column : columns) {
//            int columnIndex = cursor.getColumnIndex(column);
//            if (columnIndex != -1) {
//                switch(column) {
//                    case COLUMN_NAME:
//                        group.setName(cursor.getString(columnIndex));
//                        break;
//                    case COLUMN_PARENT_ID:
//                        long id = cursor.getLong(columnIndex);
//
//                        if (id != 0) {
//                            group.setParentGroup(new Reference("https://public-crest.eveonline.com/market/groups/" +
//                                    id + "/"));
//                        }
//                        break;
//                    case COLUMN_HREF:
//                        group.setHref(cursor.getString(columnIndex));
//                        break;
//                    case COLUMN_TYPES_LOCATION:
//                        group.setTypes(new Reference(cursor.getString(columnIndex)));
//                        break;
//                    case COLUMN_DESCRIPTION:
//                        group.setDescription(cursor.getString(columnIndex));
//                        break;
//                }
//            }
//        }
//
//        return group;
//    }

//    public static ArrayList<MarketGroup> getMarketGroupsforParent(Context context, Long parentId) {
//        SQLiteDatabase database = Database.getInstance(context).getReadableDatabase();
//        ArrayList<MarketGroup> groups = new ArrayList<>();
//        String query = "SELECT * FROM " + TABLE_NAME + " WHERE ";
//
//        if (parentId == null) {
//            query += COLUMN_PARENT_ID + " IS NULL";
//        }
//        else {
//            query += COLUMN_PARENT_ID + "='" + parentId + "'";
//        }
//
//        database.beginTransaction();
//        Cursor cursor = database.rawQuery(query, null);
//
//        if (cursor.moveToFirst()) {
//            while(!cursor.isAfterLast()) {
//                groups.add(buildMarketGroup(cursor));
//                cursor.moveToNext();
//            }
//        }
//
//        database.setTransactionSuccessful();
//        database.endTransaction();
//        database.close();
//        return groups;
//    }

//    public static MarketGroup getMarketGroup(Context context, long id) {
//        SQLiteDatabase database = Database.getInstance(context).getReadableDatabase();
//        ArrayList<MarketGroup> groups = new ArrayList<>();
//
//        database.beginTransaction();
//        Cursor cursor = database.query(TABLE_NAME, null, _ID + "=?",
//                new String[]{String.valueOf(id)}, null, null, null);
//
//        if (cursor.moveToFirst()) {
//            while(!cursor.isAfterLast()) {
//                groups.add(buildMarketGroup(cursor));
//                cursor.moveToNext();
//            }
//        }
//
//        database.setTransactionSuccessful();
//        database.endTransaction();
//        database.close();
//        return groups.get(0);
//    }
}
