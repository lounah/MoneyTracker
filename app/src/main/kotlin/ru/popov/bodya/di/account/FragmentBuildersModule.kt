package ru.popov.bodya.di.account

import com.lounah.moneytracker.ui.charts.ChartFragment
import com.lounah.moneytracker.ui.settings.AboutFragment
import com.lounah.moneytracker.ui.settings.SettingsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.popov.bodya.presentation.account.AccountFragment
import ru.popov.bodya.presentation.addtransaction.AddTransactionFragment

@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeWalletFragment(): AccountFragment

    @ContributesAndroidInjector
    abstract fun contributeSettingsFragment(): SettingsFragment

    @ContributesAndroidInjector
    abstract fun contributeAboutFragment(): AboutFragment

    @ContributesAndroidInjector
    abstract fun contributeChartFragment(): ChartFragment

    @ContributesAndroidInjector
    abstract fun contributeAddTransactionFragment(): AddTransactionFragment
}
