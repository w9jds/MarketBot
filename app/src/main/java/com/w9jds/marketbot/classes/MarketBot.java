package com.w9jds.marketbot.classes;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.w9jds.marketbot.classes.components.*;
import com.w9jds.marketbot.classes.modules.ApplicationModule;
import com.w9jds.marketbot.classes.modules.NetModule;
import com.w9jds.marketbot.classes.modules.StorageModule;

public class MarketBot extends Application {

    private static BaseComponent baseComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(this);

        baseComponent = DaggerBaseComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .netModule(new NetModule(NetModule.PUBLIC_TRANQUILITY))
                .build();

    }

    public static StorageComponent createNewStorageSession() {
        return DaggerStorageComponent.builder()
                .baseComponent(baseComponent)
                .storageModule(new StorageModule())
                .build();
    }

}
