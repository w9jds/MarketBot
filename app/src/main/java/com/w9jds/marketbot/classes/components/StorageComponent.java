package com.w9jds.marketbot.classes.components;

import com.w9jds.marketbot.activities.InfoActivity;
import com.w9jds.marketbot.classes.StorageScope;
import com.w9jds.marketbot.classes.modules.StorageModule;
import com.w9jds.marketbot.data.DataManager;

import dagger.Component;

/**
 * Created by Jeremy Shore on 3/10/16.
 */
@StorageScope
@Component(dependencies = BaseComponent.class, modules = StorageModule.class)
public interface StorageComponent {

    void inject(DataManager manager);
    void inject(InfoActivity infoActivity);

}
