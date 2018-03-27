package com.w9jds.marketbot.classes.components

import android.app.Application
import com.w9jds.marketbot.classes.modules.ApplicationModule
import com.w9jds.marketbot.classes.modules.EsiModule
import dagger.Component
import javax.inject.Singleton
import android.content.SharedPreferences
import com.w9jds.marketbot.classes.EsiService
import com.w9jds.marketbot.data.DataManager
import com.w9jds.marketbot.ui.MainActivity

@Singleton
@Component(modules = [ApplicationModule::class, EsiModule::class])
interface ApplicationComponent {

    fun application(): Application
    fun preferences(): SharedPreferences
    fun esi(): EsiService

    fun inject(mainActivity: MainActivity)
    fun inject(manager: DataManager)
}