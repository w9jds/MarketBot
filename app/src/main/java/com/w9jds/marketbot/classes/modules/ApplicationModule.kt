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

    @Provides
    @Singleton
    fun providesApplication(): Application {
        return application
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application)
    }

    @Provides
    @Singleton
    fun provideFirebaseDatabase(): FirebaseDatabase {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()

        database.setPersistenceEnabled(true)
        database.getReference("groups").keepSynced(true)
        database.getReference("types").keepSynced(true)
        database.getReference("regions").keepSynced(true)

        return database
    }
}