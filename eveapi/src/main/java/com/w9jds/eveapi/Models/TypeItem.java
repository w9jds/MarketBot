package com.w9jds.eveapi.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jeremy on 2/17/2016.
 */
public final class TypeItem {

    @SerializedName("marketGroup")
    public Reference marketGroup;

    @SerializedName("type")
    public Type type;

    @SerializedName("id")
    public int id;

}
