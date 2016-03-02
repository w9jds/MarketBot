package com.w9jds.marketbot.ui.fragments;

import com.w9jds.eveapi.Models.Region;
import com.w9jds.eveapi.Models.Type;

/**
 * Created by Jeremy on 3/1/2016.
 */
public interface onRegionChanged {

    void updateOrdersList(Region region, Type type);

}
