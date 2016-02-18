package com.w9jds.eveapi.Models;

import com.google.gson.annotations.SerializedName;

import java.util.Hashtable;

/**
 * Created by Jeremy on 2/17/2016.
 */
public final class MarketGroup {

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int id;

    @SerializedName("parentGroup")
    private Reference parentGroup;

    @SerializedName("href")
    private String href;

    @SerializedName("description")
    private String description;

    @SerializedName("types")
    private Reference types;

    public Hashtable<Integer, MarketGroup> children = new Hashtable<>();

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getHref() {
        return href;
    }

    public String getTypes() {
        return types.href;
    }

    public String getParentGroup() {
        return parentGroup.href;
    }

    public Integer getParentGroupId() {
        String[] query = this.parentGroup.href.split("/");

        for (int i = query.length; i > 0; i--) {
            if (!query[i-1].equals("")) {
                return Integer.parseInt(query[i-1]);
            }
        }

        return null;
    }

    public boolean hasParent() {
        return this.parentGroup != null && !this.parentGroup.href.equals("");
    }
}
