package com.w9jds.marketbot.classes.components;

import android.app.Application;

import com.w9jds.eveapi.Client.Crest;
import com.w9jds.marketbot.activities.ItemActivity;
import com.w9jds.marketbot.activities.MainActivity;
import com.w9jds.marketbot.classes.modules.ApplicationModule;
import com.w9jds.marketbot.classes.modules.NetModule;
import com.w9jds.marketbot.data.DataManager;

import java.lang.annotation.Retention;

import javax.inject.Named;
import javax.inject.Scope;
import javax.inject.Singleton;
import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by Jeremy Shore on 3/9/16.
 */
@Singleton
@Component(modules={ApplicationModule.class, NetModule.class})
public interface NetComponent {

    Application application();
    Retrofit retrofit();

}