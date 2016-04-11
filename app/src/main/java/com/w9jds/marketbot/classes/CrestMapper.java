package com.w9jds.marketbot.classes;

import com.w9jds.marketbot.classes.models.MarketGroup;
import com.w9jds.marketbot.classes.models.Region;
import com.w9jds.marketbot.classes.models.TypeInfo;

import core.eve.crest.CrestItem;
import core.eve.crest.CrestMarketGroup;
import core.eve.crest.CrestType;

/**
 * Created by w9jds on 4/10/2016.
 */
public class CrestMapper {

    public TypeInfo build(CrestType crestType) {
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

    public MarketGroup build(CrestMarketGroup crestGroup) {
        return new MarketGroup.Builder()
            .setId(crestGroup.getId())
            .setName(crestGroup.getName())
            .setDescription(crestGroup.getDescription())
            .setHref(crestGroup.getRef())
            .setParentGroup(crestGroup.getParent())
            .setTypes(crestGroup.getType())
            .build();
    }

//    public Region build(CrestItem crestItem) {
//
//    }

}
