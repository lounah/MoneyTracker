package com.lounah.moneytracker.di.modules

import com.lounah.moneytracker.ui.charts.ChartFragment
import com.lounah.moneytracker.ui.settings.AboutFragment
import com.lounah.moneytracker.ui.settings.SettingsFragment
import com.lounah.moneytracker.ui.wallet.addtransaction.AddTransactionFragment
import com.lounah.moneytracker.ui.wallet.WalletFragment
import com.lounah.moneytracker.ui.wallet.WalletFragmentModule
import com.lounah.moneytracker.ui.wallet.addtransaction.AddTransactionModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector(modules = [WalletFragmentModule::class])
    abstract fun contributeWalletFragment(): WalletFragment

    @ContributesAndroidInjector
    abstract fun contributeSettingsFragment(): SettingsFragment

    @ContributesAndroidInjector
    abstract fun contributeAboutFragment(): AboutFragment

    @ContributesAndroidInjector
    abstract fun contributeChartFragment(): ChartFragment

    @ContributesAndroidInjector(modules = [AddTransactionModule::class])
    abstract fun contributeAddTransactionFragment(): AddTransactionFragment
}
