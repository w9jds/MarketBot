package com.w9jds.marketbot.classes.components;

import com.w9jds.eveapi.Client.Crest;
import com.w9jds.marketbot.classes.modules.StorageModule;
import com.w9jds.marketbot.data.DataManager;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Jeremy Shore on 3/10/16.
 */
@Singleton
@Component(modules={StorageModule.class})
public interface StorageComponent {

    void inject(DataManager dataManager);
    void inject(Crest crest);

}
