package com.w9jds.marketbot.classes.components

import android.app.Application
import android.content.Context
import com.w9jds.marketbot.classes.modules.ApplicationModule
import com.w9jds.marketbot.classes.modules.EsiModule
import dagger.Component
import javax.inject.Singleton
import android.content.SharedPreferences
import com.google.firebase.database.FirebaseDatabase
import com.w9jds.marketbot.classes.EsiService
import com.w9jds.marketbot.data.DataManager
import com.w9jds.marketbot.data.loader.GroupsLoader
import com.w9jds.marketbot.ui.MainActivity

@Singleton
@Component(modules = [ApplicationModule::class, EsiModule::class])
interface ApplicationComponent {

    fun application(): Application
    fun database(): FirebaseDatabase
    fun preferences(): SharedPreferences

    fun inject(context: Context)

}