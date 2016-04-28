package com.w9jds.marketbot.data.storage;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.w9jds.marketbot.classes.models.Type;
import com.w9jds.marketbot.data.MarketDatabase;

import org.devfleet.crest.model.CrestMarketType;
import org.devfleet.crest.model.CrestType;

import java.util.ArrayList;
import java.util.List;

@Table(database = MarketDatabase.class)
public final class MarketTypeEntry extends BaseModel {

    @PrimaryKey
    long id;

    @Column
    long groupId;

    @Column
    String href;

    @Column
    String icon;

    @Column
    String name;

    public static void addNewMarketTypes(List<CrestMarketType> types) {
        int size = types.size();
        for (int i = 0; i < size; i++) {
            CrestMarketType type = types.get(i);

            MarketTypeEntry entry = new MarketTypeEntry();
            entry.id = type.getTypeId();
            entry.groupId = type.getGroupId();
            entry.href = type.getTypeHref();
            entry.icon = type.getTypeIcon();
            entry.name = type.getTypeName();
            entry.save();
        }
    }

    private static Type buildMarketType(MarketTypeEntry entry) {
        return new Type.Builder()
            .setGroupId(entry.groupId)
            .setHref(entry.href)
            .setId(entry.id)
            .build();
    }

    private static List<Type> buildMarketTypes(List<MarketTypeEntry> entries) {
        int size = entries.size();
        List<Type> types = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            types.add(buildMarketType(entries.get(i)));
        }

        return types;
    }

    public static List<Type> getMarketTypes(long groupId) {
        return buildMarketTypes(new Select()
            .from(MarketTypeEntry.class)
            .where(MarketTypeEntry_Table.groupId.eq(groupId))
            .queryList());
    }

    public static Type getType(long typeId) {
        return buildMarketType(new Select()
            .from(MarketTypeEntry.class)
            .where(MarketTypeEntry_Table.id.eq(typeId))
            .querySingle());
    }

    public static List<Type> searchMarketTypes(String queryString) {
        return buildMarketTypes(new Select()
            .from(MarketTypeEntry.class)
            .where(MarketTypeEntry_Table.name.like("%" + queryString + "%"))
            .queryList());
    }
}