package com.w9jds.marketbot.classes

import android.app.Application
import com.raizlabs.android.dbflow.config.FlowManager
import com.w9jds.marketbot.classes.components.ApplicationComponent
import dagger.internal.DaggerCollections

/**
 * Created by w9jds on 1/13/18.
 */
open class MarketBot : Application() {

    companion object {
        @JvmStatic lateinit var base: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        FlowManager.init(this)

//        base = DaggerApplicationComponent
    }


}