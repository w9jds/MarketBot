package com.w9jds.eveapi.Models;


import com.google.gson.annotations.SerializedName;

/**
 * Created by Jeremy Shore on 2/16/16.
 */
public final class Region {

    @SerializedName("name")
    private String name;

    @SerializedName("href")
    private String href;

    @SerializedName("id")
    private Integer id;

    public String getName() {
        return name;
    }

    public String getHref() {
        return href;
    }

    public Integer getId() {
        return id;
    }
}
