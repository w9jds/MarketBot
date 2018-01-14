package com.w9jds.marketbot.data.models.esi

/**
 * Created by w9jds on 12/23/2017.
 */
interface History {

    val date: String
    val orderCount: Long
    val volume: Long
    val highest: Double
    val average: Double
    val lowest: Double

}