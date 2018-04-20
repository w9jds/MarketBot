package com.w9jds.marketbot.classes

import android.app.Application
import com.w9jds.marketbot.classes.components.ApplicationComponent

/**
 * Created by w9jds on 1/13/18.
 */
open class MarketBot : Application() {

    companion object {
        @JvmStatic lateinit var base: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()


    }


}