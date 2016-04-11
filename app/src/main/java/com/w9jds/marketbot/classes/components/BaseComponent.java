package com.w9jds.marketbot.classes.components;

import android.app.Application;
import android.content.SharedPreferences;

import com.w9jds.marketbot.classes.CrestService;
import com.w9jds.marketbot.classes.modules.ApplicationModule;
import com.w9jds.marketbot.classes.modules.NetModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Jeremy Shore on 3/31/16.
 */
@Singleton
@Component(modules = { ApplicationModule.class, NetModule.class })
public interface BaseComponent {

    Application application();
    SharedPreferences sharedPreferences();
    CrestService crest();

}
