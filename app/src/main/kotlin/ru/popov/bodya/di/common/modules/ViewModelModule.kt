package ru.popov.bodya.di.common.modules

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.lounah.moneytracker.ui.wallet.WalletViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.popov.bodya.di.common.viewmodel.AppViewModelFactory
import ru.popov.bodya.di.common.viewmodel.ViewModelKey

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(WalletViewModel::class)
    fun bindBalanceViewModel(balanceViewModel: WalletViewModel): ViewModel
}
