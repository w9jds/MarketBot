package com.w9jds.marketbot.classes.components;

import com.w9jds.marketbot.classes.modules.ApplicationModule;
import com.w9jds.marketbot.classes.modules.NetModule;

import javax.inject.Singleton;
import dagger.Component;

/**
 * Created by Jeremy Shore on 3/9/16.
 */
@Singleton
@Component(modules={ApplicationModule.class, NetModule.class})
public interface NetComponent {
//    void inject(DataManager dataManager);
}

//@Component(modules = {Modules.ApplicationModule.class, Modules.NetModule.class})
//interface MyComponent {
//    MyWidget myWidget();
//
//    @Component.Builder
//    interface Builder {
//        MyComponent build();
//        Builder backendModule(BackendModule bm);
//        Builder frontendModule(FrontendModule fm);
//    }
//}