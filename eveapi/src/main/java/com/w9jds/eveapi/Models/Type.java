package com.w9jds.eveapi.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jeremy on 2/17/2016.
 */
public final class Type extends MarketItemBase implements Parcelable {

    @SerializedName("type")
    private TypeItem type;


    @SerializedName("marketGroup")
    public Reference marketGroup;

    @Override
    public String getName() {
        return type.name;
    }

    public Type() {

    }

    @Override
    public void setId(long id) {
        super.setId(id);
    }

    public void setType(TypeItem type) {
        this.type = type;
    }

    public void setMarketGroup(Reference marketGroup) {
        this.marketGroup = marketGroup;
    }

    protected Type(Parcel in) {
        this.setId(in.readLong());
        this.type = in.readParcelable(TypeItem.class.getClassLoader());
        this.marketGroup = in.readParcelable(Reference.class.getClassLoader());
    }

    public String getIconLink() {
        return type.icon.href;
    }

    public String getHref() {
        return type.href;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.getId());
        dest.writeParcelable(this.type, flags);
        dest.writeParcelable(this.marketGroup, 0);
    }

    public static final Creator<Type> CREATOR = new Creator<Type>() {
        public Type createFromParcel(Parcel source) {
            return new Type(source);
        }

        public Type[] newArray(int size) {
            return new Type[size];
        }
    };

}
