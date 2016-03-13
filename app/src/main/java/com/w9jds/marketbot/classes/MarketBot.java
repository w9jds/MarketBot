package com.w9jds.marketbot.classes;

import android.app.Application;

import com.w9jds.eveapi.Client.Crest;
import com.w9jds.marketbot.classes.components.DaggerNetComponent;
import com.w9jds.marketbot.classes.components.DaggerStorageComponent;
import com.w9jds.marketbot.classes.components.NetComponent;
import com.w9jds.marketbot.classes.components.StorageComponent;
import com.w9jds.marketbot.classes.modules.ApplicationModule;
import com.w9jds.marketbot.classes.modules.StorageModule;
import com.w9jds.marketbot.classes.modules.NetModule;

/**
 * Created by Jeremy Shore on 3/9/16.
 */
public final class MarketBot extends Application {

    private StorageComponent storageComponent;
    private NetComponent netComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        netComponent = DaggerNetComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .netModule(new NetModule(Crest.PUBLIC_TRANQUILITY))
                .build();

        storageComponent = DaggerStorageComponent.builder()
                .storageModule(new StorageModule(this))
                .build();

    }

    public NetComponent getNetComponent() {
        return netComponent;
    }

    public StorageComponent getStorageComponent() {
        return storageComponent;
    }
}
