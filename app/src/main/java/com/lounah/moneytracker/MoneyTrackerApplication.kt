package com.lounah.moneytracker

import com.lounah.moneytracker.di.DaggerAppComponent
import com.lounah.wallettracker.BuildConfig
import com.squareup.leakcanary.LeakCanary
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

class MoneyTrackerApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        installLeakCanary()
        installTimber()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication>
            = DaggerAppComponent.builder().application(this).build()

    private fun installLeakCanary() {
        if (!LeakCanary.isInAnalyzerProcess(this))
            LeakCanary.install(this)
    }

    private fun installTimber() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}
