package com.w9jds.marketbot.classes.components

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.database.FirebaseDatabase
import com.w9jds.marketbot.classes.ApiService
import com.w9jds.marketbot.classes.modules.ApiModule
import com.w9jds.marketbot.classes.modules.ApplicationModule
import com.w9jds.marketbot.data.loader.OrdersLoader
import javax.inject.Singleton
import dagger.Component

@Singleton
@Component(modules = [ApplicationModule::class, ApiModule::class])
interface ApplicationComponent {
    fun application(): Application
    fun database(): FirebaseDatabase
    fun preferences(): SharedPreferences

    fun inject(loader: OrdersLoader)
}