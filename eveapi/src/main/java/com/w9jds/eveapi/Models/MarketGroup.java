package com.w9jds.eveapi.Models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Size;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by Jeremy on 2/17/2016.
 */
public final class MarketGroup extends MarketItemBase implements Parcelable {

    @SerializedName("parentGroup")
    private Reference parentGroup;

    @SerializedName("href")
    private String href;

    @SerializedName("description")
    private String description;

    @SerializedName("types")
    private Reference types;

    public HashMap<Integer, MarketGroup> children = new HashMap<>();
    public Map<Integer, Type> items = new HashMap<>();

    public MarketGroup() {

    }

    public String getDescription() {
        return description;
    }

    public String getHref() {
        return href;
    }

    public String getTypesLocation() {
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.getId());
        dest.writeString(this.getName());
        dest.writeParcelable(this.parentGroup, 0);
        dest.writeString(this.href);
        dest.writeString(this.description);
        dest.writeParcelable(this.types, 0);

        dest.writeInt(children.size());
        for (MarketGroup group : children.values()) {
            dest.writeParcelable(group, 0);
        }
    }

    protected MarketGroup(Parcel in) {
        this.setId(in.readInt());
        this.setName(in.readString());
        this.parentGroup = in.readParcelable(Reference.class.getClassLoader());
        this.href = in.readString();
        this.description = in.readString();
        this.types = in.readParcelable(Reference.class.getClassLoader());

        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            MarketGroup child = in.readParcelable(MarketGroup.class.getClassLoader());
            this.children.put(child.getId(), child);
        }
    }

    public static final Creator<MarketGroup> CREATOR = new Creator<MarketGroup>() {
        public MarketGroup createFromParcel(Parcel source) {
            return new MarketGroup(source);
        }

        public MarketGroup[] newArray(int size) {
            return new MarketGroup[size];
        }
    };
}
