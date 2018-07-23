package com.lounah.moneytracker.di.modules

import com.lounah.moneytracker.ui.settings.AboutFragment
import com.lounah.moneytracker.ui.settings.SettingsFragment
import com.lounah.moneytracker.ui.wallet.WalletFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeWalletFragment(): WalletFragment

    @ContributesAndroidInjector
    abstract fun contributeSettingsFragment(): SettingsFragment

    @ContributesAndroidInjector
    abstract fun contributAboutFragment(): AboutFragment
}