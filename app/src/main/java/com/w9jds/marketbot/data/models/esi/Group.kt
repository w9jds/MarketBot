package com.w9jds.marketbot.data.models.esi

/**
 * Created by w9jds on 12/23/2017.
 */
interface Group: Base {

    val description: String
    val types: List<Int>
    val parentGroupId: Int?

    fun hasParentGroup(): Boolean {
        return this.parentGroupId != null
    }

}