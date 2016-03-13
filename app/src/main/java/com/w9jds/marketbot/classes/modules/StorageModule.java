package com.w9jds.marketbot.classes.modules;

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
    private SharedPreferences sharedPreferences;

    public StorageModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences;
    }

    @Provides
    @Singleton
    String provideServerVersion() {
        return sharedPreferences.getString("serverVersion", "");
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
