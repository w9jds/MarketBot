package com.w9jds.eveapi.Models.containers;

import com.google.gson.annotations.SerializedName;
import com.w9jds.eveapi.Models.Region;

import java.util.ArrayList;

/**
 * Created by Jeremy on 3/1/2016.
 */
public final class Regions {

    @SerializedName("items")
    public ArrayList<Region> items;

    @SerializedName("totalCount")
    public int size;

}
