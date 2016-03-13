package com.w9jds.eveapi.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public final class TypeItem implements Parcelable {

    @SerializedName("name")
    public String name;

    @SerializedName("href")
    public String href;

    @SerializedName("icon")
    public Reference icon;

    public TypeItem() {

    }

    protected TypeItem(Parcel in) {
        this.name = in.readString();
        this.href = in.readString();
        this.icon = in.readParcelable(Reference.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.href);
        dest.writeParcelable(this.icon, 0);
    }

    public static final Creator<TypeItem> CREATOR = new Creator<TypeItem>() {
        public TypeItem createFromParcel(Parcel source) {
            return new TypeItem(source);
        }

        public TypeItem[] newArray(int size) {
            return new TypeItem[size];
        }
    };
}