package com.w9jds.marketbot.data.models.esi

/**
 * Created by w9jds on 12/23/2017.
 */
interface MarketGroup {

    val id: Int
    val name: String
    val description: String
    val types: List<Int>
    var parentGroupId: Int?

}