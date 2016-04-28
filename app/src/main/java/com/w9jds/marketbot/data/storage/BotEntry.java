package com.w9jds.marketbot.data.storage;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.w9jds.marketbot.data.MarketDatabase;

@Table(database = MarketDatabase.class)
public final class BotEntry extends BaseModel {

    @PrimaryKey(autoincrement = true)
    long id;

    @Column
    long typeId;

    @Column
    long regionId;

    @Column
    String typeHref;

    @Column
    double threshold;

    @Column
    boolean isBuy;

    @Column
    boolean isAbove;



//    public static final String COLUMN_INTERVAL = "interval";
//    public static final String COLUMN_TYPE_ID = "typeId";
//    public static final String COLUMN_REGION_ID = "regionId";
//    public static final String COLUMN_TYPE_HREF = "typeHref";
//    public static final String COLUMN_THRESHOLD = "threshold";
//    public static final String COLUMN_IS_BUY = "isBuy";
//    public static final String COLUMN_IS_ABOVE = "isAbove";
//
//    // Table name
//    public static final String TABLE_NAME = "Bots";
//
//    // Create Command
//    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
//            + _ID + " INTEGER PRIMARY KEY,"
//            + COLUMN_INTERVAL + " INTEGER,"
//            + COLUMN_TYPE_ID + " INTEGER,"
//            + COLUMN_REGION_ID + " INTEGER,"
//            + COLUMN_THRESHOLD + " REAL,"
//            + COLUMN_TYPE_HREF + " TEXT,"
//            + COLUMN_IS_BUY + " BOOLEAN,"
//            + COLUMN_IS_ABOVE + " BOOLEAN,"
//            + " UNIQUE (" + _ID + ") ON CONFLICT REPLACE);";
//
//    public static long createNewBot(SQLiteDatabase database, Bot bot) {
//        long newId;
//
//        database.beginTransaction();
//
//        ContentValues thisItem = new ContentValues();
//        thisItem.put(COLUMN_INTERVAL, bot.getInterval());
//        thisItem.put(COLUMN_TYPE_ID, bot.getTypeId());
//        thisItem.put(COLUMN_REGION_ID, bot.getRegionId());
//        thisItem.put(COLUMN_THRESHOLD, bot.getThreshold());
//        thisItem.put(COLUMN_IS_ABOVE, bot.isCheckAboveThreshold());
//        thisItem.put(COLUMN_IS_BUY, bot.isBuyOrder());
//        thisItem.put(COLUMN_TYPE_HREF, bot.getTypeHref());
//
//        newId = database.insertWithOnConflict(TABLE_NAME, null, thisItem, SQLiteDatabase.CONFLICT_IGNORE);
//
//        database.setTransactionSuccessful();
//        database.endTransaction();
//        return newId;
//    }
//
//    public static Bot getBot(SQLiteDatabase database, long id) {
//        database.beginTransaction();
//
//        ArrayList<Bot> bots = new ArrayList<>();
//
//        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME +
//                " WHERE " + _ID + "='" + id + "'", null);
//
//        if (cursor.moveToFirst()) {
//            while (!cursor.isAfterLast()) {
//                bots.add(StorageUtils.buildClass(new Bot(), cursor));
//                cursor.moveToNext();
//            }
//        }
//
//        database.setTransactionSuccessful();
//        database.endTransaction();
//
//        if (bots.size() > 0) {
//            return bots.get(0);
//        }
//        else {
//            return null;
//        }
//    }
}