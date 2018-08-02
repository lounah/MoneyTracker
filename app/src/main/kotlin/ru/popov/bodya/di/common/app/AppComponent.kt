package ru.popov.bodya.di.common.app

import android.app.Application
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import ru.popov.bodya.app.MoneyTrackerApplication
import ru.popov.bodya.core.dagger.ApplicationContext
import ru.popov.bodya.di.account.MainActivityModule
import ru.popov.bodya.di.common.modules.AppModule

@Component(modules = [
    AppModule::class,
    MainActivityModule::class,
    AndroidSupportInjectionModule::class,
    AndroidInjectionModule::class])
interface AppComponent {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun appContext(@ApplicationContext context: Context): Builder

        fun build(): AppComponent
    }

    fun inject(howMoneyApp: MoneyTrackerApplication)
}
