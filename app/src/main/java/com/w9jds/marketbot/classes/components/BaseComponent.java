package com.w9jds.marketbot.classes.components;

import com.w9jds.marketbot.classes.modules.ApplicationModule;
import com.w9jds.marketbot.classes.modules.EsiModule;
import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = { ApplicationModule.class, EsiModule.class })
public interface BaseComponent {


}
