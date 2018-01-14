package com.w9jds.marketbot.classes.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.w9jds.marketbot.data.models.esi.Type

data class MarketType(
        @JsonProperty("type_id") override val id: Int,
        @JsonProperty override val name: String?,
        @JsonProperty override val description: String,
        @JsonProperty override val published: Boolean,
        @JsonProperty("group_id") override val groupId: Int,
        @JsonProperty("market_group_id") override val marketGroupId: Int,
        @JsonProperty override val radius: Float?,
        @JsonProperty override val volume: Float?,
        @JsonProperty("packaged_volume") override val packagedVolume: Float?,
        @JsonProperty("icon_id") override val iconId: Int?,
        @JsonProperty override val capacity: Float?,
        @JsonProperty("portion_size") override val portionSize: Int?,
        @JsonProperty override val mass: Float?,
        @JsonProperty("graphic_id") override val graphicId: Int?
): Type


//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(this.href);
//        dest.writeString(this.icon);
//        dest.writeString(this.marketGroup);
//        dest.writeString(this.name);
//        dest.writeLong(this.id);
//        dest.writeLong(this.groupId);
//    }
//
//    public Type() {
//
//    }
//
//    protected Type(Parcel in) {
//        this.href = in.readString();
//        this.icon = in.readString();
//        this.marketGroup = in.readString();
//        this.name = in.readString();
//        this.id = in.readLong();
//        this.groupId = in.readLong();
//    }
//
//    public static final Parcelable.Creator<Type> CREATOR = new Parcelable.Creator<Type>() {
//        @Override
//        public Type createFromParcel(Parcel source) {
//            return new Type(source);
//        }
//
//        @Override
//        public Type[] newArray(int size) {
//            return new Type[size];
//        }
//    };