package com.w9jds.marketbot.data.models

interface Bot {

    val interval: Int
    val typeId: Int
    val regionId: Int
    val threshold: Double
    val isAbove: Boolean
    val isBuy: Boolean

}