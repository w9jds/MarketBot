package com.w9jds.marketbot.classes.modules

import android.app.Application
import android.arch.persistence.room.Room
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.firebase.database.FirebaseDatabase
import com.w9jds.marketbot.data.Database
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: Application) {

//    private val firebase: FirebaseDatabase = FirebaseDatabase.getInstance()
//    private val database: Database = Room.databaseBuilder(application,
//            Database::class.java, "market-storage").build()

    @Provides
    @Singleton
    fun providesApplication(): Application = application

    @Provides
    @Singleton
    fun provideSharedPreferences(): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(application)

    @Provides
    @Singleton
    fun provideFirebaseDatabase(): FirebaseDatabase {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        database.setPersistenceEnabled(true)
        return database
    }

    @Provides
    @Singleton
    fun provideDatabase(application: Application): Database {
       return Room.databaseBuilder(application, Database::class.java, "market-storage").build()
    }
}