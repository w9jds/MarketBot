package com.w9jds.eveapi.Models;


import com.google.gson.annotations.SerializedName;

/**
 * Created by Jeremy Shore on 2/16/16.
 */
public final class Region extends MarketItemBase {

    @SerializedName("href")
    private String href;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Override
    public void setId(long id) {
        super.setId(id);
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }
}
