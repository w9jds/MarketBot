package com.w9jds.marketbot.data.storage;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.w9jds.marketbot.classes.models.MarketGroup;
import com.w9jds.marketbot.data.MarketDatabase;

import java.util.ArrayList;
import java.util.List;

import core.eve.crest.CrestMarketGroup;

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
    String parentId;

    @Column
    String types;

    public static void addNewMarketGroups(List<CrestMarketGroup> groups) {
        int count = groups.size();
        for (int i = 0; i < count; i++) {
            CrestMarketGroup group = groups.get(i);

            MarketGroupEntry entry = new MarketGroupEntry();
            entry.id = group.getId();
            entry.description = group.getDescription();
            entry.name = group.getName();
            entry.href = group.getRef();
            entry.parentId = group.getParent();
            entry.types = group.getType();
        }
    }

    public static ArrayList<MarketGroup> getMarketGroupsForParent(Long parentId) {
        Condition condition = MarketGroupEntry_Table.parentId.eq(buildParentGroupLink(parentId));
        Condition nullCondition = MarketGroupEntry_Table.parentId.isNull();

        List<MarketGroupEntry> groups = new Select()
                .from(MarketGroupEntry.class)
                .where(parentId != null ? condition : nullCondition)
                .queryList();

        ArrayList<MarketGroup> builtGroups = new ArrayList<>(groups.size());
        for (MarketGroupEntry entry : groups) {
            builtGroups.add(buildMarketGroup(entry));
        }

        return builtGroups;
    }

    private static MarketGroup buildMarketGroup(MarketGroupEntry entry) {
        return new MarketGroup.Builder()
            .setDescription(entry.description)
            .setHref(entry.href)
            .setId(entry.id)
            .setName(entry.name)
            .setParentGroup(entry.parentId)
            .setTypes(entry.types)
            .build();
    }

    private static String buildParentGroupLink(long id) {
        return "https://public-crest.eveonline.com/market/groups/" + id + "/";
    }

    public static MarketGroup getMarketGroup(long id) {
        MarketGroupEntry group = new Select()
                .from(MarketGroupEntry.class)
                .where(MarketGroupEntry_Table.parentId.eq(buildParentGroupLink(id)))
                .querySingle();

        return buildMarketGroup(group);
    }

}
