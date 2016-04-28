package com.w9jds.marketbot.data.storage;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.w9jds.marketbot.classes.models.MarketGroup;
import com.w9jds.marketbot.classes.models.MarketOrder;
import com.w9jds.marketbot.data.MarketDatabase;

import org.devfleet.crest.model.CrestMarketGroup;

import java.util.ArrayList;
import java.util.List;

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
    String parent;

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
            entry.href = group.getHref();
            if (group.hasParent()) {
                entry.parent = group.getParentRef();
                entry.parentId = getParentId(group.getParentRef());
            }
            entry.types = group.getTypeRef();
            entry.save();
        }
    }

    private static long getParentId(String href) {
        String[] query = href.split("/");

        for (int i = query.length; i > 0; i--) {
            if (!query[i-1].equals("")) {
                return Long.parseLong(query[i-1]);
            }
        }

        return 0L;
    }

    public static ArrayList<MarketGroup> getMarketGroupsForParent(Long parentId) {
        Condition condition = parentId != null ? MarketGroupEntry_Table.parentId.eq(parentId) :
                MarketGroupEntry_Table.parentId.isNull();

        List<MarketGroupEntry> groups = new Select()
                .from(MarketGroupEntry.class)
                .where(condition)
                .queryList();

        ArrayList<MarketGroup> builtGroups = new ArrayList<>(groups.size());
        for (MarketGroupEntry entry : groups) {
            builtGroups.add(buildMarketGroup(entry));
        }

        return builtGroups;
    }

    private static MarketGroup buildMarketGroup(MarketGroupEntry entry) {
        MarketGroup.Builder builder = new MarketGroup.Builder()
            .setDescription(entry.description)
            .setHref(entry.href)
            .setId(entry.id)
            .setName(entry.name)
            .setTypes(entry.types);

        if (!entry.parent.equals("")) {
            builder
                .setParentGroup(entry.parent)
                .setParentId(entry.parentId);
        }

        return builder.build();
    }

    private static String buildParentGroupLink(long id) {
        return "https://public-crest.eveonline.com/market/groups/" + id + "/";
    }

    public static MarketGroup getMarketGroup(long id) {
        MarketGroupEntry group = new Select()
            .from(MarketGroupEntry.class)
            .where(MarketGroupEntry_Table.parentId.eq(id))
            .querySingle();

        return buildMarketGroup(group);
    }

}
