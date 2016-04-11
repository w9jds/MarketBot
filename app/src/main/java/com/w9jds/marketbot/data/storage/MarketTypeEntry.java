package com.w9jds.marketbot.data.storage;

import android.provider.BaseColumns;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.w9jds.marketbot.classes.models.Type;
import com.w9jds.marketbot.data.MarketDatabase;

import java.util.ArrayList;

import rx.Observable;

@Table(database = MarketDatabase.class)
public final class MarketTypeEntry extends BaseModel {

    @PrimaryKey
    long id;

    @Column
    long groupId;

    @Column
    String href;

    @Column
    String name;

    @Column
    String icon;

    public static void buildNewMarketTypes(ArrayList<Type> types) {

        int size = types.size();
        for (int i = 0; i < size; i++) {
            Type type = types.get(i);

            MarketTypeEntry entry = new MarketTypeEntry();
            entry.id = type.getId();

            entry.href = type.getHref();
            entry.name = type.getName();
            entry.icon = type.getIcon();
            entry.save();
        }

    }

//    public static ArrayList<Type> getMarketTypes(long groupId) {
//        SQLite.select().from(MarketTypeEntry.class).where()
//    }

//    public static ArrayList<Type> getMarketTypes(Context context, long groupId) {
//        SQLiteDatabase database = Database.getInstance(context).getReadableDatabase();
//        ArrayList<Type> types = new ArrayList<>();
//
//        database.beginTransaction();
//
//        Cursor cursor = database.query(TABLE_NAME, null, COLUMN_GROUP_ID + "=?",
//                new String[]{String.valueOf(groupId)}, null, null, null);
//
//        if (cursor.moveToFirst()) {
//            while(!cursor.isAfterLast()) {
//                types.add(buildType(cursor));
//                cursor.moveToNext();
//            }
//        }
//
//        database.setTransactionSuccessful();
//        database.endTransaction();
//        database.close();
//        return types;
//    }

//    @Nullable
//    public static Type getType(Context context, long typeId) {
//        SQLiteDatabase database = Database.getInstance(context).getReadableDatabase();
//        ArrayList<Type> types = new ArrayList<>();
//
//        database.beginTransaction();
//
//        Cursor cursor = database.query(TABLE_NAME, null, _ID + "=?",
//                new String[]{String.valueOf(typeId)}, null, null, null);
//
//        if (cursor.moveToFirst()) {
//            while(!cursor.isAfterLast()) {
//                types.add(buildType(cursor));
//                cursor.moveToNext();
//            }
//        }
//
//        database.setTransactionSuccessful();
//        database.endTransaction();
//        database.close();
//
//        if (types.size() > 0) {
//            return types.get(0);
//        }
//        else {
//            return null;
//        }
//    }

//    private static Type buildType(Cursor cursor) {
//        Type type = new Type();
//        type.setId(cursor.getLong(cursor.getColumnIndex(_ID)));
//
//        TypeItem item = new TypeItem();
//        item.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
//        item.setHref(cursor.getString(cursor.getColumnIndex(COLUMN_HREF)));
//        item.setIcon(new Reference(cursor.getString(cursor.getColumnIndex(COLUMN_ICON_LOC))));
//        type.setType(item);
//        long marketGroupId = cursor.getLong(cursor.getColumnIndex(COLUMN_GROUP_ID));
//
//        Reference group = new Reference("https://public-crest.eveonline.com/market/groups/" + marketGroupId + "/");
//        group.setId(marketGroupId);
//        type.setMarketGroup(group);
//        return type;
//    }

//    public static ArrayList<Type> searchMarketTypes(Context context, String queryString) {
//        SQLiteDatabase database = Database.getInstance(context).getReadableDatabase();
//        ArrayList<Type> types = new ArrayList<>();
//
//        database.beginTransaction();
//
//        Cursor cursor = database.query(TABLE_NAME, null, COLUMN_NAME + " LIKE ?",
//                new String[]{"%" + queryString + "%"}, null, null, null);
//
//        if (cursor.moveToFirst()) {
//            while(!cursor.isAfterLast()) {
//                types.add(buildType(cursor));
//                cursor.moveToNext();
//            }
//        }
//
//        database.setTransactionSuccessful();
//        database.endTransaction();
//        return types;
//    }
}