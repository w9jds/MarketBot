package com.w9jds.marketbot.data.storage

import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.rx2.structure.BaseRXModel
import com.w9jds.marketbot.data.MarketDatabase

@Table(database = MarketDatabase::class, name = "Info")
data class InfoEntry(var info: MarketInfo): BaseRXModel() {

    @PrimaryKey
    private var id: Long = 0

    @Column
    private var name: String? = null

    @Column
    private var capacity: Double = 0.toDouble()

    @Column
    private var description: String? = null

    @Column
    private var iconId: Long = 0

    @Column
    private var mass: Double = 0.toDouble()

    @Column
    private var radius: Double = 0.toDouble()

    @Column
    private var volume: Double = 0.toDouble()

    @Column
    private var portionSize: Double = 0.toDouble()


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