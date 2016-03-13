package com.w9jds.marketbot.classes.components;

import com.w9jds.marketbot.activities.ItemActivity;
import com.w9jds.marketbot.activities.MainActivity;
import com.w9jds.marketbot.classes.modules.ApplicationModule;
import com.w9jds.marketbot.classes.modules.NetModule;
import com.w9jds.marketbot.data.DataManager;

import java.lang.annotation.Retention;

import javax.inject.Scope;
import javax.inject.Singleton;
import dagger.Component;

/**
 * Created by Jeremy Shore on 3/9/16.
 */

@Component(modules={ApplicationModule.class, NetModule.class})
public interface NetComponent {
    void inject(MainActivity activity);
    void inject(ItemActivity activity);
}