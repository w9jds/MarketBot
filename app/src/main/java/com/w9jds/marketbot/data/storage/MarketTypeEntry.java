package com.w9jds.marketbot.data.storage;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.runtime.TransactionManager;
import com.raizlabs.android.dbflow.runtime.transaction.process.ProcessModelInfo;
import com.raizlabs.android.dbflow.runtime.transaction.process.ProcessModelTransaction;
import com.raizlabs.android.dbflow.runtime.transaction.process.SaveModelTransaction;
import com.raizlabs.android.dbflow.sql.language.OrderBy;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.w9jds.marketbot.classes.models.Type;
import com.w9jds.marketbot.data.MarketDatabase;

import org.devfleet.crest.model.CrestMarketType;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

@Table(database = MarketDatabase.class, name = "MarketTypes")
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

    public static void addNewMarketTypes(List<CrestMarketType> types, MarketDatabase.TransactionListener listener) {
        int size = types.size();
        List<MarketTypeEntry> entries = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            CrestMarketType type = types.get(i);

            MarketTypeEntry entry = new MarketTypeEntry();
            entry.id = type.getTypeId();
            entry.groupId = type.getGroupId();
            entry.href = type.getTypeHref();
            entry.icon = type.getTypeIcon();
            entry.name = type.getTypeName();
            entries.add(entry);
        }

        TransactionManager manager = TransactionManager.getInstance();
        ProcessModelTransaction transaction = new SaveModelTransaction<>(ProcessModelInfo.withModels(entries));

        transaction.setChangeListener((current, maxProgress, modifiedModel) ->
                listener.onTransactionProgressUpdate((int)current, (int)maxProgress));

        manager.addTransaction(transaction);
    }

    private static Type buildMarketType(MarketTypeEntry entry) {
        return new Type.Builder()
            .setName(entry.name)
            .setGroupId(entry.groupId)
            .setHref(entry.href)
            .setId(entry.id)
            .setIcon(entry.icon)
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
            .orderBy(OrderBy.fromProperty(MarketTypeEntry_Table.name).ascending())
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
            .orderBy(OrderBy.fromProperty(MarketTypeEntry_Table.name).ascending())
            .queryList());
    }
}