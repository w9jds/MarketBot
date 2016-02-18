package com.w9jds.eveapi.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

/**
 * Created by Jeremy Shore on 2/16/16.
 */

public final class MarketGroups {

    @SerializedName("totalCount")
    public int count;

    @SerializedName("items")
    public ArrayList<MarketGroup> groups;

    @SerializedName("pageCount")
    public int pageCount;

}
