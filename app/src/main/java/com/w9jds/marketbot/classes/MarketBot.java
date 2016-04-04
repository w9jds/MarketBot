package com.w9jds.marketbot.classes;

import android.app.Application;

import com.w9jds.eveapi.Client.Crest;
import com.w9jds.marketbot.classes.components.*;
import com.w9jds.marketbot.classes.modules.ApplicationModule;
import com.w9jds.marketbot.classes.modules.NetModule;
import com.w9jds.marketbot.classes.modules.StorageModule;

/**
 * Created by Jeremy Shore on 3/9/16.
 */
public class MarketBot extends Application {

    private static BaseComponent baseComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        baseComponent = DaggerBaseComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .netModule(new NetModule(Crest.PUBLIC_TRANQUILITY))
                .build();

    }

    public static StorageComponent createNewStorageSession() {
        return DaggerStorageComponent.builder()
                .baseComponent(baseComponent)
                .storageModule(new StorageModule())
                .build();
    }

}
