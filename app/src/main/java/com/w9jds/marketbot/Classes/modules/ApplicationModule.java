package com.w9jds.marketbot.classes.modules;

import android.app.Application;

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
}