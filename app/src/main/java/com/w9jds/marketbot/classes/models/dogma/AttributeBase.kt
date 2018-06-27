package com.w9jds.marketbot.classes.models.dogma

import android.os.Parcel
import android.os.Parcelable

data class AttributeBase(
        var attribute_id: Int? = null,
        var value: Double? = null
): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readValue(Double::class.java.classLoader) as? Double)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(attribute_id)
        parcel.writeValue(value)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AttributeBase> {
        override fun createFromParcel(parcel: Parcel): AttributeBase {
            return AttributeBase(parcel)
        }

        override fun newArray(size: Int): Array<AttributeBase?> {
            return arrayOfNulls(size)
        }
    }
}