package com.w9jds.marketbot.data.models.esi

/**
 * Created by w9jds on 12/23/2017.
 */
interface Type: Base {
    
    var groupId: Int
    var published: Boolean
    var description: String

    var marketGroupId: Int?
    var packagedVolume: Long?
    var iconId: Int?
    var portionSize: Int?
    var graphicId: Long?


    var radius: Long?
    var volume: Long?
    var capacity: Long?
    var mass: Long?

}