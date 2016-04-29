package com.w9jds.marketbot.data.storage;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.w9jds.marketbot.data.MarketDatabase;

@Table(database = MarketDatabase.class, name = "TypeInfo")
public final class MarketTypeInfoEntry extends BaseModel {

    @PrimaryKey
    long id;

    @Column
    String name;

    @Column
    double capacity;

    @Column
    String description;

    @Column
    long iconId;

    @Column
    double mass;

    @Column
    double radius;

    @Column
    double volume;

    @Column
    double portionSize;


//        public static void createNewTypeInfos(Context context, List<TypeInfo> infos) {
//            SQLiteDatabase database = Database.getInstance(context).getWritableDatabase();
//            database.beginTransaction();
//
//            for (TypeInfo info : infos) {
//                ContentValues thisItem = new ContentValues();
//                thisItem.put(_ID, info.getId());
//                thisItem.put(COLUMN_CAPACITY, info.getCapacity());
//                thisItem.put(COLUMN_DESCRTIPION, info.getDescription());
//                thisItem.put(COLUMN_MASS, info.getMass());
//                thisItem.put(COLUMN_PORTION_SIZE, info.getPortionSize());
//                thisItem.put(COLUMN_RADIUS, info.getRadius());
//                thisItem.put(COLUMN_VOLUME, info.getVolume());
//
//                database.insertWithOnConflict(TABLE_NAME, null, thisItem, SQLiteDatabase.CONFLICT_REPLACE);
//            }
//
//            database.setTransactionSuccessful();
//            database.endTransaction();
//        }
    }