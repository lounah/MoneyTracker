package ru.popov.bodya.di.common.app

import ru.popov.bodya.app.MoneyTrackerApplication

/**
 *  @author popovbodya
 */
object AppInjector {

    fun init(moneyTrackerApp: MoneyTrackerApplication) {

        DaggerAppComponent.builder()
                .application(moneyTrackerApp)
                .appContext(moneyTrackerApp)
                .build()
                .inject(moneyTrackerApp)
    }
}