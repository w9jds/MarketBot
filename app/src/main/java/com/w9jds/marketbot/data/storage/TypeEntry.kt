package com.w9jds.marketbot.data.storage

import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.rx2.structure.BaseRXModel
import com.w9jds.marketbot.data.MarketDatabase

@Table(database = MarketDatabase::class, name = "Types")
data class TypeEntry(var type: MarketType): BaseRXModel() {

    @PrimaryKey
    private var id: Long = 0

    @Column
    private var groupId: Long = 0

    @Column
    private var href: String? = ""

    @Column
    private var icon: String? = ""

    @Column
    private var name: String? = ""

//    public static void addNewMarketTypes(List<CrestMarketType> types, BehaviorSubject<Map.Entry<Integer, Integer>> subject) {
//        int size = types.size();
//        List<MarketTypeEntry> entries = new ArrayList<>(size);
//        for (int i = 0; i < size; i++) {
//            CrestMarketType type = types.get(i);
//
//            MarketTypeEntry entry = new MarketTypeEntry();
//            entry.id = type.getTypeId();
//            entry.groupId = type.getGroupId();
//            entry.href = type.getTypeHref();
//            entry.icon = type.getTypeIcon();
//            entry.name = type.getTypeName();
//            entries.add(entry);
//        }
//
//        TransactionManager manager = TransactionManager.getInstance();
//        ProcessModelTransaction transaction = new SaveModelTransaction<>(ProcessModelInfo.withModels(entries));
//
//        transaction.setChangeListener((current, maxProgress, modifiedModel) -> {
//            if (current % 25 == 0 || current == maxProgress) {
//                subject.onNext(new AbstractMap.SimpleEntry<>((int) current, (int) maxProgress));
//            }
//        });
//
//        manager.addTransaction(transaction);
//    }

//    private static Type buildMarketType(MarketTypeEntry entry) {
//        return new Type.Builder()
//            .setName(entry.name)
//            .setGroupId(entry.groupId)
//            .setHref(entry.href)
//            .setId(entry.id)
//            .setIcon(entry.icon)
//            .build();
//    }

//    private static List<Type> buildMarketTypes(List<MarketTypeEntry> entries) {
//        int size = entries.size();
//        List<Type> types = new ArrayList<>(size);
//        for (int i = 0; i < size; i++) {
//            types.add(buildMarketType(entries.get(i)));
//        }
//
//        return types;
//    }

//    public static List<Type> getMarketTypes(long groupId) {
//        return buildMarketTypes(new Select()
//            .from(MarketTypeEntry.class)
//            .where(MarketTypeEntry_Table.groupId.eq(groupId))
//            .orderBy(OrderBy.fromProperty(MarketTypeEntry_Table.name).ascending())
//            .queryList());
//    }

//    public static Type getType(long typeId) {
//        return buildMarketType(new Select()
//            .from(MarketTypeEntry.class)
//            .where(MarketTypeEntry_Table.id.eq(typeId))
//            .querySingle());
//    }

//    public static List<Type> searchMarketTypes(String queryString) {
//        return buildMarketTypes(new Select()
//            .from(MarketTypeEntry.class)
//            .where(MarketTypeEntry_Table.name.like("%" + queryString + "%"))
//            .orderBy(OrderBy.fromProperty(MarketTypeEntry_Table.name).ascending())
//            .queryList());
//    }

}