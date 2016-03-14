package com.w9jds.marketbot.classes.modules;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;

import com.w9jds.eveapi.Client.Crest;
import com.w9jds.marketbot.classes.components.StorageScope;
import com.w9jds.marketbot.data.storage.Database;

import javax.inject.Named;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Jeremy Shore on 3/10/16.
 */
@Module
public class StorageModule {

    private SharedPreferences sharedPreferences;

    public StorageModule() {

    }

    @Provides
    @StorageScope
    SharedPreferences provideSharedPreferences(Application application) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(application);
        return sharedPreferences;
    }

    @Provides
    @StorageScope
    String provideServerVersion() {
        return sharedPreferences.getString("serverVersion", "");
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

    @Provides @Named("public_traq")
    @StorageScope
    public Crest providePublicCrest(Retrofit retrofit) {
        return new Crest(retrofit);
    }
}
