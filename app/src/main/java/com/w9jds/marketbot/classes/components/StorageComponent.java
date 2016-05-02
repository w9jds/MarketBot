package com.w9jds.marketbot.classes.components;

import com.w9jds.marketbot.data.loader.GroupsLoader;
import com.w9jds.marketbot.data.loader.OrdersLoader;
import com.w9jds.marketbot.data.loader.TypeLoader;
import com.w9jds.marketbot.ui.InfoActivity;
import com.w9jds.marketbot.ui.ItemActivity;
import com.w9jds.marketbot.classes.StorageScope;
import com.w9jds.marketbot.classes.modules.StorageModule;

import dagger.Component;

@StorageScope
@Component(dependencies = BaseComponent.class, modules = StorageModule.class)
public interface StorageComponent {

    void inject(GroupsLoader groupsLoader);
    void inject(OrdersLoader tabsLoader);
    void inject(TypeLoader typeLoader);

    void inject(InfoActivity infoActivity);
    void inject(ItemActivity itemActivity);

}
