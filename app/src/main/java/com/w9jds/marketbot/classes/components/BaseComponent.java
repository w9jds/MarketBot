package com.w9jds.marketbot.classes.components;

import android.app.Application;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import com.w9jds.eveapi.Client.Crest;
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
    Crest crest();

}
