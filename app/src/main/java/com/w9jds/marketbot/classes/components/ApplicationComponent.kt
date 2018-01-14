package com.w9jds.marketbot.classes.components

import com.w9jds.marketbot.classes.modules.ApplicationModule
import com.w9jds.marketbot.classes.modules.EsiModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, EsiModule::class])
interface ApplicationComponent