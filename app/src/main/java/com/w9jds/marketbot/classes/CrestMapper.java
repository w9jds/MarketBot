package com.w9jds.marketbot.classes;

import com.w9jds.marketbot.classes.models.MarketGroup;
import com.w9jds.marketbot.classes.models.MarketOrder;
import com.w9jds.marketbot.classes.models.TypeInfo;

import org.devfleet.crest.model.CrestMarketGroup;
import org.devfleet.crest.model.CrestType;

/**
 * Created by w9jds on 4/10/2016.
 */
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
            .setParentGroup(crestGroup.getParent())
            .setTypes(crestGroup.getType())
            .build();
    }

    public static MarketOrder map() {
        return new MarketOrder.Builder()

    }

}
