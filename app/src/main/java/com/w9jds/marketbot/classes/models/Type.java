package com.w9jds.marketbot.classes.models;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jeremy on 2/17/2016.
 */
public final class Type extends MarketItemBase implements Parcelable {

    private String href;
    private String icon;
    private String marketGroup;
    private long groupId;

    public String getHref() {
        return href;
    }

    public String getIcon() {
        return icon;
    }

    public long getGroupId() {
        return groupId;
    }

    public String getMarketGroup() {
        return marketGroup;
    }

    public static class Builder {

        private long id;
        private String name;
        private String href;
        private String icon;
        private String marketGroup;
        private long groupId;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setHref(String href) {
            this.href = href;
            return this;
        }

        public Builder setIcon(String icon) {
            this.icon = icon;
            return this;
        }

        public Builder setMarketGroup(String marketGroup) {
            this.marketGroup = marketGroup;
            return this;
        }

        public Builder setGroupId(long groupId) {
            this.groupId = groupId;
            return this;
        }

        public Type build() {
            Type type = new Type();
            type.id = this.id;
            type.name = this.name;
            type.href = this.href;
            type.icon = this.icon;
            type.marketGroup = this.marketGroup;
            type.groupId = this.groupId;

            return type;
        }


    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.href);
        dest.writeString(this.icon);
        dest.writeString(this.marketGroup);
        dest.writeString(this.name);
        dest.writeLong(this.id);
        dest.writeLong(this.groupId);
    }

    public Type() {

    }

    protected Type(Parcel in) {
        this.href = in.readString();
        this.icon = in.readString();
        this.marketGroup = in.readString();
        this.name = in.readString();
        this.id = in.readLong();
        this.groupId = in.readLong();
    }

    public static final Parcelable.Creator<Type> CREATOR = new Parcelable.Creator<Type>() {
        @Override
        public Type createFromParcel(Parcel source) {
            return new Type(source);
        }

        @Override
        public Type[] newArray(int size) {
            return new Type[size];
        }
    };
}
