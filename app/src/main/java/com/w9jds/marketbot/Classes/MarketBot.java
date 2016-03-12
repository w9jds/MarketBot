package com.w9jds.marketbot.classes;

import android.app.Application;

import com.w9jds.eveapi.Client.Crest;
import com.w9jds.marketbot.classes.components.DaggerDatabaseComponent;
import com.w9jds.marketbot.classes.components.DaggerNetComponent;
import com.w9jds.marketbot.classes.components.DatabaseComponent;
import com.w9jds.marketbot.classes.components.NetComponent;
import com.w9jds.marketbot.classes.modules.ApplicationModule;
import com.w9jds.marketbot.classes.modules.DatabaseModule;
import com.w9jds.marketbot.classes.modules.NetModule;

/**
 * Created by Jeremy Shore on 3/9/16.
 */
public final class MarketBot extends Application {

    private DatabaseComponent databaseComponent;
    private NetComponent netComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        netComponent = DaggerNetComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .netModule(new NetModule(Crest.PUBLIC_TRANQUILITY))
                .build();

        databaseComponent = DaggerDatabaseComponent.builder()
                .databaseModule(new DatabaseModule(this))
                .build();

    }

    public NetComponent getNetComponent() {
        return netComponent;
    }

    public DatabaseComponent getDatabaseComponent() {
        return databaseComponent;
    }
}
