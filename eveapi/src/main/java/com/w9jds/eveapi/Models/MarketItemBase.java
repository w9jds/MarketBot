package com.w9jds.eveapi.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jeremy Shore on 2/21/16.
 */
public class MarketItemBase {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    public boolean visited = false;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    void setId(int id) {
        this.id = id;
    }

    void setName(String name) {
        this.name = name;
    }

}
