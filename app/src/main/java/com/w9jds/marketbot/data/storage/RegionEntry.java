package com.w9jds.marketbot.data.storage;

import android.provider.BaseColumns;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.w9jds.marketbot.classes.models.Region;
import com.w9jds.marketbot.data.MarketDatabase;

import org.devfleet.crest.model.CrestItem;

import java.util.List;

/**
 * Created by w9jds on 4/3/2016.
 */
@Table(database = MarketDatabase.class)
public final class RegionEntry extends BaseModel {

    @PrimaryKey
    long id;

    @Column
    String name;

    @Column
    String href;

    public static void addRegions(List<CrestItem> regions) {
        int size = regions.size();
        for (int i = 0; i < size; i++) {
            CrestItem region = regions.get(i);

            RegionEntry entry = new RegionEntry();
            entry.id = region.getId();
            entry.name = region.getName();
            entry.href = region.getHref();
            entry.save();
        }
    }

//    public static List<Region> getRegions(boolean includeWormholes) {
//        new Select()
//            .from(RegionEntry.class)
//            .where(RegionEntry_Table.name.glob("*[A-Z]-R*"))
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
