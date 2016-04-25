package com.w9jds.marketbot.classes.components;

import com.w9jds.marketbot.data.loader.GroupsLoader;
import com.w9jds.marketbot.data.loader.TabsLoader;
import com.w9jds.marketbot.data.loader.TypeLoader;
import com.w9jds.marketbot.ui.InfoActivity;
import com.w9jds.marketbot.ui.ItemActivity;
import com.w9jds.marketbot.classes.StorageScope;
import com.w9jds.marketbot.classes.modules.StorageModule;

import dagger.Component;

/**
 * Created by Jeremy Shore on 3/10/16.
 */
@StorageScope
@Component(dependencies = BaseComponent.class, modules = StorageModule.class)
public interface StorageComponent {

    void inject(GroupsLoader groupsLoader);
    void inject(TabsLoader tabsLoader);
    void inject(TypeLoader typeLoader);

    void inject(InfoActivity infoActivity);
    void inject(ItemActivity itemActivity);

}
