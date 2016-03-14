package com.w9jds.eveapi.Models.containers;

import com.google.gson.annotations.SerializedName;
import com.w9jds.eveapi.Models.Reference;
import com.w9jds.eveapi.Models.Type;

import java.util.ArrayList;

/**
 * Created by Jeremy on 2/17/2016.
 */
public final class Types {

    @SerializedName("pageCount")
    public int pageCount;

    @SerializedName("totalCount")
    public int count;

    @SerializedName("items")
    public ArrayList<Type> items;

    @SerializedName("next")
    public Reference next;

}
