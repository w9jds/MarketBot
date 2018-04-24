package com.w9jds.marketbot.classes.models.market

import android.os.Parcel
import android.os.Parcelable
import com.w9jds.marketbot.classes.models.dogma.AttributeBase
import com.w9jds.marketbot.classes.models.dogma.EffectBase


data class MarketType(
        val type_id: Int? = null,
        val name: String = "",
        val description: String = "",
        val published: Boolean = false,
        val group_id: Int? = null,
        val market_group_id: Int? = null,
        val radius: Float? = null,
        val volume: Float? = null,
        val packaged_volume: Float? = null,
        val icon_id: Int? = null,
        val capacity: Float? = null,
        val portion_size: Int? = null,
        val mass: Float? = null,
        val graphic_id: Int? = null,
        val dogma_attributes: List<AttributeBase>? = null,
        val dogma_effects: List<EffectBase>? = null
): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readValue(Float::class.java.classLoader) as? Float,
            parcel.readValue(Float::class.java.classLoader) as? Float,
            parcel.readValue(Float::class.java.classLoader) as? Float,
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readValue(Float::class.java.classLoader) as? Float,
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readValue(Float::class.java.classLoader) as? Float,
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.createTypedArrayList(AttributeBase),
            parcel.createTypedArrayList(EffectBase)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(type_id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeByte(if (published) 1 else 0)
        parcel.writeValue(group_id)
        parcel.writeValue(market_group_id)
        parcel.writeValue(radius)
        parcel.writeValue(volume)
        parcel.writeValue(packaged_volume)
        parcel.writeValue(icon_id)
        parcel.writeValue(capacity)
        parcel.writeValue(portion_size)
        parcel.writeValue(mass)
        parcel.writeValue(graphic_id)
        parcel.writeTypedList(dogma_attributes)
        parcel.writeTypedList(dogma_effects)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MarketType> {
        override fun createFromParcel(parcel: Parcel): MarketType {
            return MarketType(parcel)
        }

        override fun newArray(size: Int): Array<MarketType?> {
            return arrayOfNulls(size)
        }
    }
}
