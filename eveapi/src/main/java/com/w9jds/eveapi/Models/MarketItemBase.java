package com.w9jds.eveapi.Models;

import com.google.gson.annotations.SerializedName;

import java.math.BigInteger;

/**
 * Created by Jeremy Shore on 2/21/16.
 */
public class MarketItemBase {

    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String name;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    void setId(long id) {
        this.id = id;
    }

    void setName(String name) {
        this.name = name;
    }

}
