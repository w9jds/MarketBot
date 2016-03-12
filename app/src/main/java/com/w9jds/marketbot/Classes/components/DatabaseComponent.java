package com.w9jds.marketbot.classes.components;

import com.w9jds.eveapi.Client.Crest;
import com.w9jds.marketbot.classes.modules.DatabaseModule;
import com.w9jds.marketbot.data.DataManager;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Jeremy Shore on 3/10/16.
 */
@Singleton
@Component(modules={ DatabaseModule.class })
public interface DatabaseComponent {
    void inject(DataManager dataManager);
    void inject(Crest crest);
}
