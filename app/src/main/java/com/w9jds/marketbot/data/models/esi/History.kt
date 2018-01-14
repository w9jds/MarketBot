package com.w9jds.marketbot.data.models.esi

/**
 * Created by w9jds on 12/23/2017.
 */
interface History {

    var date: DateTime
    var orderCount: Int
    var volume: Int
    var highest: Long
    var average: Long
    var lowest: Long

//    date (string): The date of this historical statistic entry ,
//    order_count (integer): Total number of orders happened that day ,
//    volume (integer): Total ,
//    highest (number): highest number ,
//    average (number): average number ,
//    lowest (number): lowest number

}