package com.w9jds.marketbot.classes.modules;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;

import com.w9jds.marketbot.data.storage.Database;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jeremy Shore on 3/10/16.
 */
@Module
public class StorageModule {

    private Context context;

    public StorageModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides @Named("write")
    @Singleton
    public SQLiteDatabase provideWritableDatabase() {
        return new Database(context).getWritableDatabaseHelper();
    }

    @Provides @Named("read")
    @Singleton
    public SQLiteDatabase provideReadableDatabase() {
        return new Database(context).getReadableDatabaseHelper();
    }
}
