package com.w9jds.eveapi.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jeremy on 2/17/2016.
 */
public final class Type {

    @SerializedName("href")
    public String href;

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    @SerializedName("icon")
    public Reference icon;

}
