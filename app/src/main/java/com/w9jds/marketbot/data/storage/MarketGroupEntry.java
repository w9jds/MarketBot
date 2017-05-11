package com.w9jds.marketbot.data.storage;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.runtime.DBTransactionQueue;
import com.raizlabs.android.dbflow.runtime.TransactionManager;
import com.raizlabs.android.dbflow.runtime.transaction.process.ProcessModelInfo;
import com.raizlabs.android.dbflow.runtime.transaction.process.SaveModelTransaction;
import com.raizlabs.android.dbflow.sql.language.Condition;
import com.raizlabs.android.dbflow.sql.language.OrderBy;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.w9jds.marketbot.classes.models.MarketGroup;
import com.w9jds.marketbot.data.MarketDatabase;
import com.w9jds.marketbot.data.models.esi.EsiMarketGroup;

import org.devfleet.crest.model.CrestMarketGroup;

import java.util.ArrayList;
import java.util.List;

@Table(database = MarketDatabase.class, name = "MarketGroups")
public final class MarketGroupEntry extends BaseModel {

    @PrimaryKey
    long id;

    @Column
    String name;

    @Column
    String description;

    @Column
    long parentId;

    @Column
    int[] types;

    public static void addNewMarketGroups(List<EsiMarketGroup> groups) {
        int count = groups.size();
        List<MarketGroupEntry> entries = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {
            EsiMarketGroup group = groups.get(i);

            MarketGroupEntry entry = new MarketGroupEntry();
            entry.id = group.getGroupId();
            entry.description = group.getDescription();
            entry.name = group.getName();
            entry.parentId = group.getParentGroupId();
            entry.types = group.getTypes();
            entries.add(entry);
        }


    }

    public static ArrayList<MarketGroup> getMarketGroupsForParent(Long parentId) {
        Condition condition = parentId != null ? MarketGroupEntry_Table.parentId.eq(parentId) :
                MarketGroupEntry_Table.parentId.isNull();

        List<MarketGroupEntry> groups = new Select()
                .from(MarketGroupEntry.class)
                .where(condition)
                .orderBy(OrderBy.fromProperty(MarketGroupEntry_Table.name).ascending())
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

        if (entry.parent != null) {
            builder
                .setParentGroup(entry.parent)
                .setParentId(entry.parentId);
        }

        return builder.build();
    }

    public static MarketGroup getMarketGroup(long id) {
        MarketGroupEntry group = new Select()
            .from(MarketGroupEntry.class)
            .where(MarketGroupEntry_Table.id.eq(id))
            .querySingle();

        return buildMarketGroup(group);
    }

}
