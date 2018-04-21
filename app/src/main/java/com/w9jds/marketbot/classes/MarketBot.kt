package com.w9jds.marketbot.classes

import android.app.Application
import com.w9jds.marketbot.classes.components.ApplicationComponent
import com.w9jds.marketbot.classes.components.DaggerApplicationComponent
import com.w9jds.marketbot.classes.modules.ApplicationModule

/**
 * Created by w9jds on 1/13/18.
 */
open class MarketBot : Application() {

    companion object {
        @JvmStatic lateinit var base: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()

        base = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }


}