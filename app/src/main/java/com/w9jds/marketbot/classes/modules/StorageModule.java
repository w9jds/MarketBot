package com.w9jds.marketbot.classes.modules;

import android.content.SharedPreferences;

import com.w9jds.marketbot.classes.StorageScope;

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

}
