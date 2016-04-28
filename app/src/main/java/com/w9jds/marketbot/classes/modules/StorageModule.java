package com.w9jds.marketbot.classes.modules;

import android.content.SharedPreferences;

import com.w9jds.marketbot.classes.StorageScope;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jeremy Shore on 3/10/16.
 */
@Module
public class StorageModule {

    public StorageModule() {

    }

    @Provides
    @StorageScope
    String provideServerVersion(SharedPreferences sharedPreferences) {
        return sharedPreferences.getString("serverVersion", "");
    }

    @Provides
    @StorageScope
    boolean provideFirstRun(SharedPreferences sharedPreferences) {
        return sharedPreferences.getBoolean("isFirstRun", true);
    }

    @Provides
    @StorageScope
    long provideRegionId(SharedPreferences sharedPreferences) {
        return sharedPreferences.getLong("regionId", 10000002);
    }

}
