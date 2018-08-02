package ru.popov.bodya.di.common

import android.app.Application
import android.content.Context
import com.lounah.moneytracker.di.AppModule
import com.lounah.moneytracker.di.modules.MainActivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import ru.popov.bodya.app.MoneyTrackerApplication
import ru.popov.bodya.core.dagger.ApplicationContext

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
