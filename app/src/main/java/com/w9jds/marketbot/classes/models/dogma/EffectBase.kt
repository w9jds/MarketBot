package com.w9jds.marketbot.classes.models.dogma

import android.os.Parcel
import android.os.Parcelable

data class EffectBase (
        var effect_id: Int? = null,
        var is_default: Boolean? = null
): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readValue(Boolean::class.java.classLoader) as? Boolean
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(effect_id)
        parcel.writeValue(is_default)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EffectBase> {
        override fun createFromParcel(parcel: Parcel): EffectBase {
            return EffectBase(parcel)
        }

        override fun newArray(size: Int): Array<EffectBase?> {
            return arrayOfNulls(size)
        }
    }
}