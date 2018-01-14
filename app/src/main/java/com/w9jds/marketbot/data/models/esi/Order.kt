package com.w9jds.marketbot.data.models.esi

/**
 * Created by w9jds on 12/23/2017.
 */
interface Order: Base {

    val typeId: Int
    val locationId: Long
    val totalVolume: Int
    val volumeLeft: Int
    val minVolume: Int
    val price: Double
    val isBuyOrder: Boolean
    val duration: Int
    val issued: String
    val range: String

//    range (string): range string = ['station', 'region', 'solarsystem', '1', '2', '3', '4', '5', '10', '20', '30', '40']

}