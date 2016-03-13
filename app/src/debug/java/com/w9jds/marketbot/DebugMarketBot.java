package com.w9jds.marketbot;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by w9jds on 3/13/2016.
 */
public class DebugMarketBot extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initialize(
            Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());

    }
}
