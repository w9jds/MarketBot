package com.w9jds.marketbot;

import com.facebook.stetho.Stetho;
import com.w9jds.marketbot.classes.MarketBot;

/**
 * Created by w9jds on 3/13/2016.
 */
public class DebugMarketBot extends MarketBot {

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
