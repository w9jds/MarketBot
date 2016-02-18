package com.w9jds.eveapi.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Jeremy on 2/17/2016.
 */
public final class Types {

    @SerializedName("pageCount")
    public int pageCount;

    @SerializedName("totalCount")
    public int count;

    @SerializedName("type")
    public ArrayList<TypeItem> items;

}
