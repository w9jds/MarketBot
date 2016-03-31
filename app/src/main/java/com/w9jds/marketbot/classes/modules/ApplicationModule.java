package com.w9jds.marketbot.classes.modules;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private Application appModule;

    public ApplicationModule(Application application) {
        appModule = application;
    }

    @Provides
    @Singleton
    Application privideApplication() {
        return appModule;
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

//    @Provides @Named("write")
//    @StorageScope
//    public SQLiteDatabase provideWritableDatabase() {
//        return new Database(context).getWritableDatabaseHelper();
//    }
//
//    @Provides @Named("read")
//    @StorageScope
//    public SQLiteDatabase provideReadableDatabase() {
//        return new Database(context).getReadableDatabaseHelper();
//    }

}