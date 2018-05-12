package com.w9jds.marketbot.classes.models.market

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import com.w9jds.marketbot.classes.models.dogma.AttributeBase
import com.w9jds.marketbot.classes.models.dogma.EffectBase


@Entity
data class MarketType(

        @PrimaryKey(autoGenerate = false)
        val type_id: Int? = null,

        @ColumnInfo(name = "name")
        val name: String = "",

        @ColumnInfo(name = "description")
        val description: String = "",

        @ColumnInfo(name = "published")
        val published: Boolean = false,

        @ColumnInfo(name = "group_id")
        val group_id: Int? = null,

        @ColumnInfo(name = "market_group_id")
        val market_group_id: Int? = null,

        @ColumnInfo(name = "radius")
        val radius: Float? = null,

        @ColumnInfo(name = "volume")
        val volume: Float? = null,

        @ColumnInfo(name = "packaged_volume")
        val packaged_volume: Float? = null,

        @ColumnInfo(name = "icon_id")
        val icon_id: Int? = null,

        @ColumnInfo(name = "capacity")
        val capacity: Float? = null,

        @ColumnInfo(name = "portion_size")
        val portion_size: Int? = null,

        @ColumnInfo(name = "mass")
        val mass: Float? = null,

        @ColumnInfo(name = "graphic_id")
        val graphic_id: Int? = null,

        @ColumnInfo(name = "dogma_attributes")
        val dogma_attributes: List<AttributeBase>? = null,

        @ColumnInfo(name = "dogma_effects")
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
