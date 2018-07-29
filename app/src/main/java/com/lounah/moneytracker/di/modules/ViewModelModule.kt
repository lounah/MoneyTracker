package com.lounah.moneytracker.di.modules

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.lounah.moneytracker.di.ViewModelKey
import com.lounah.moneytracker.ui.wallet.WalletViewModel
import com.lounah.moneytracker.util.AppViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(WalletViewModel::class)
    abstract fun bindBalanceViewModel(balanceViewModel: WalletViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory
}
