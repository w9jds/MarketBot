package com.w9jds.eveapi.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

/**
 * Created by Jeremy Shore on 2/16/16.
 */
public final class MarketGroup {

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int id;

    @SerializedName("parentGroup")
    private String parentGroup;

    @SerializedName("href")
    private String href;

    @SerializedName("description")
    private String description;

    @SerializedName("types")
    private ArrayList<String> types;

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

    public ArrayList<String> getTypes() {
        return types;
    }

    public String getParentGroup() {
        return parentGroup;
    }

    public Integer getParentGroupId() {
        String[] query = this.parentGroup.split("///g/");

        for (int i = query.length; i > 0; i--) {
            if (!query[i-1].equals("")) {
                return Integer.parseInt(query[i-1]);
            }
        }

        return null;
    }

    public boolean hasParent() {
        return this.parentGroup != null && !this.parentGroup.equals("");
    }
}
