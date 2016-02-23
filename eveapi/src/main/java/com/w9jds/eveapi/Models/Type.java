package com.w9jds.eveapi.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jeremy on 2/17/2016.
 */
public final class Type extends MarketItemBase {

    @SerializedName("type")
    private Item type;


    @SerializedName("marketGroup")
    public Reference marketGroup;


    @Override
    public String getName() {
        return type.name;
    }

    public String getIconLink() {
        return type.icon.href;
    }

    public String getHref() {
        return type.href;
    }

    private class Item {

        @SerializedName("name")
        public String name;

        @SerializedName("href")
        public String href;

        @SerializedName("icon")
        public Reference icon;

    }
}
