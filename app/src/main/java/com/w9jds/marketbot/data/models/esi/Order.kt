package com.w9jds.marketbot.data.models.esi

/**
 * Created by w9jds on 12/23/2017.
 */
interface Order: Base {

    var typeId: Int
    var locationId: Int
    var total: Int
    var remainder: Int
    var min: Int
    var price: Long
    var isBuyOrder: Boolean
    var duration: Int
    var issued: String
    var range: String
    
//    range (string): range string = ['station', 'region', 'solarsystem', '1', '2', '3', '4', '5', '10', '20', '30', '40']

}