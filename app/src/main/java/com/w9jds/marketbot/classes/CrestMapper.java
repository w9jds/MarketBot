package com.w9jds.marketbot.classes;

import com.w9jds.marketbot.classes.models.MarketGroup;
import com.w9jds.marketbot.classes.models.MarketHistory;
import com.w9jds.marketbot.classes.models.MarketOrder;
import com.w9jds.marketbot.classes.models.TypeInfo;

import org.devfleet.crest.model.CrestMarketGroup;
import org.devfleet.crest.model.CrestMarketHistory;
import org.devfleet.crest.model.CrestMarketOrder;
import org.devfleet.crest.model.CrestType;

public class CrestMapper {

    public static TypeInfo map(CrestType crestType) {
        return new TypeInfo.Builder()
            .setId(crestType.getId())
            .setCapacity(crestType.getCapacity())
            .setDescription(crestType.getDescription())
            .setMass(crestType.getMass())
            .setName(crestType.getName())
            .setPortionSize(crestType.getPortionSize())
            .setRadius(crestType.getRadius())
            .setVolume(crestType.getVolume())
            .build();
    }

    public static MarketGroup map(CrestMarketGroup crestGroup) {
        return new MarketGroup.Builder()
            .setId(crestGroup.getId())
            .setName(crestGroup.getName())
            .setDescription(crestGroup.getDescription())
            .setHref(crestGroup.getHref())
            .setParentGroup(crestGroup.getParentRef())
            .setTypes(crestGroup.getTypeRef())
            .build();
    }

    public static MarketOrder map(CrestMarketOrder order) {
        return new MarketOrder.Builder()
            .setBuyOrder(order.isBuyOrder())
            .setDuration(order.getDuration())
            .setHref(order.getHref())
            .setLocation(order.getLocationName())
            .setLocationId(order.getLocationId())
            .setVolume(order.getVolume())
            .setVolumeStart(order.getVolumeEntered())
            .setPrice(order.getPrice())
            .setRange(order.getRange())
            .setType(order.getTypeName())
            .setTypeId(order.getTypeId())
            .build();
    }

    public static MarketHistory map(CrestMarketHistory history) {
        return new MarketHistory.Builder()
            .setAveragePrice(history.getAveragePrice())
            .setHighPrice(history.getHighPrice())
            .setLowPrice(history.getLowPrice())
//            .setOrderCount(history.get())
            .setRecordDate(history.getDate())
            .setVolume(history.getVolume())
            .build();
    }

}
