package com.w9jds.marketbot.data.models.esi

/**
 * Created by w9jds on 12/23/2017.
 */
interface Type: Base {

    val description: String
    val published: Boolean
    val groupId: Int

    val marketGroupId: Int
    val radius: Float?
    val volume: Float?
    val packagedVolume: Float?

    val iconId: Int?
    val capacity: Float?
    val portionSize: Int?
    val mass: Float?
    val graphicId: Int?

}