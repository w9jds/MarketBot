package com.w9jds.marketbot.classes.components

import android.app.Application
import android.content.SharedPreferences
import com.google.firebase.database.FirebaseDatabase
import com.w9jds.marketbot.classes.modules.ApiModule
import com.w9jds.marketbot.classes.modules.ApplicationModule
import com.w9jds.marketbot.data.Database
import com.w9jds.marketbot.data.model.OrdersLoader
import javax.inject.Singleton
import dagger.Component

@Singleton
@Component(modules = [ApplicationModule::class, ApiModule::class])
interface ApplicationComponent {
    fun application(): Application
    fun firebase(): FirebaseDatabase
    fun preferences(): SharedPreferences
    fun database(): Database

    fun inject(loader: OrdersLoader)
}