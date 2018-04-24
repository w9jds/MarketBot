package com.w9jds.marketbot.classes.modules

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: Application) {

    val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    @Provides
    @Singleton
    fun providesApplication(): Application = application

    @Provides
    @Singleton
    fun provideSharedPreferences(): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(application)

    @Provides
    @Singleton
    fun provideFirebaseDatabase(): FirebaseDatabase {
        database.setPersistenceEnabled(true)

        database.getReference("groups").keepSynced(true)
        database.getReference("types").keepSynced(true)
        database.getReference("regions").keepSynced(true)

        return database
    }
}