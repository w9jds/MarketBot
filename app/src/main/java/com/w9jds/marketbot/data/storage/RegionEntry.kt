package com.w9jds.marketbot.data.storage

import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.rx2.structure.BaseRXModel
import com.w9jds.marketbot.classes.models.MarketRegion
import com.w9jds.marketbot.data.MarketDatabase

@Table(database = MarketDatabase::class, name = "Regions")
data class RegionEntry(var region: MarketRegion): BaseRXModel() {

    @PrimaryKey
    private val id: Int = region.id

    @Column
    private val name: String? = region.name

//    public static void addRegions(List<CrestItem> regions) {
//        int size = regions.size();
//        List<RegionEntry> entries = new ArrayList<>(size);
//        for (int i = 0; i < size; i++) {
//            CrestItem region = regions.get(i);
//
//            RegionEntry entry = new RegionEntry();
//            entry.id = region.getId();
//            entry.name = region.getName();
//            entry.href = region.getHref();
//            entries.add(entry);
//        }
//
//        TransactionManager.getInstance().addTransaction(new SaveModelTransaction<>(
//                ProcessModelInfo.withModels(entries)));
//    }

//    private static List<Region> buildRegions(List<RegionEntry> entries) {
//        int size = entries.size();
//        List<Region> regions = new ArrayList<>(size);
//        for (int i = 0; i < size; i++) {
//            regions.add(buildRegion(entries.get(i)));
//        }
//
//        return regions;
//    }

//    private static Region buildRegion(RegionEntry entry) {
//        return new Region.Builder()
//            .setHref(entry.href)
//            .setName(entry.name)
//            .setId(entry.id)
//            .build();
//    }

//    public static List<Region> getAllRegions(boolean includeWormholes) {
//        List<RegionEntry> regions = new Select()
//            .from(RegionEntry.class)
//            .where()
//            .orderBy(OrderBy.fromProperty(RegionEntry_Table.name).ascending())
//            .queryList();
//
//        return buildRegions(regions);
//    }

//    public static ArrayList<Region> getRegions(Context context, boolean includeWormholes) {
//        SQLiteDatabase database = Database.getInstance(context).getReadableDatabase();
//        database.beginTransaction();
//        ArrayList<Region> regions = new ArrayList<>();
//
//        Cursor cursor;
//        if (includeWormholes) {
//            cursor = database.query(TABLE_NAME, null, null, null, null, null, COLUMN_NAME);
//        }
//        else {
//            cursor = database.query(TABLE_NAME, null, COLUMN_NAME + " NOT GLOB ?",
//                    new String[]{  }, null, null, COLUMN_NAME);
//        }
//
//        if (cursor.moveToFirst()) {
//            while(!cursor.isAfterLast()) {
//                regions.add(buildRegion(cursor));
//                cursor.moveToNext();
//            }
//        }
//
//        database.setTransactionSuccessful();
//        database.endTransaction();
//        database.close();
//        return regions;
//    }

}