package com.w9jds.eveapi.Models.containers;

import com.google.gson.annotations.SerializedName;
import com.w9jds.eveapi.Models.MarketOrder;

import java.util.ArrayList;

/**
 * Created by Jeremy on 3/1/2016.
 */
public final class MarketOrders {

    @SerializedName("totalCount")
    public int size;

    @SerializedName("items")
    public ArrayList<MarketOrder> orders;

}
