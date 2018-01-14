package com.w9jds.marketbot

import com.facebook.stetho.Stetho
import com.w9jds.marketbot.classes.MarketBot

class DebugMarketBot : MarketBot() {

    override fun onCreate() {
        super.onCreate()

        Stetho.initialize(
            Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build())
    }

}