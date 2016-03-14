package com.w9jds.eveapi.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jeremy on 2/17/2016.
 */
public final class Reference extends MarketItemBase implements Parcelable {

    public Reference(String href) {
        this.href = href;
    }

    @SerializedName("href")
    public String href;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void setId(long id) {
        super.setId(id);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(getId());
        dest.writeString(getName());
        dest.writeString(this.href);
    }

    protected Reference(Parcel in) {
        this.setId(in.readLong());
        this.setName(in.readString());
        this.href = in.readString();
    }

    public static final Parcelable.Creator<Reference> CREATOR = new Parcelable.Creator<Reference>() {
        public Reference createFromParcel(Parcel source) {
            return new Reference(source);
        }

        public Reference[] newArray(int size) {
            return new Reference[size];
        }
    };
}
