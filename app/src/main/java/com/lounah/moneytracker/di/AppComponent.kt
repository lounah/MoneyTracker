package com.lounah.moneytracker.di

import android.app.Application
import com.lounah.moneytracker.MoneyTrackerApplication
import com.lounah.moneytracker.di.modules.MainActivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Component(modules = [
    AppModule::class,
    MainActivityModule::class,
    AndroidSupportInjectionModule::class,
    AndroidInjectionModule::class])
interface AppComponent : AndroidInjector<MoneyTrackerApplication> {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): AppComponent.Builder

        fun build(): AppComponent
    }
}