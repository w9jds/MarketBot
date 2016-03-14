package com.w9jds.marketbot.classes.components;

import com.w9jds.marketbot.classes.modules.StorageModule;
import com.w9jds.marketbot.data.DataManager;

import dagger.Component;

/**
 * Created by Jeremy Shore on 3/10/16.
 */
@StorageScope
@Component(dependencies = NetComponent.class, modules = StorageModule.class)
public interface StorageComponent {

    void inject(DataManager manager);

}
