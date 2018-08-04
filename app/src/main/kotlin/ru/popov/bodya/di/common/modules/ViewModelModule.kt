package ru.popov.bodya.di.common.modules

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import ru.popov.bodya.presentation.account.AccountViewModel
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
    @ViewModelKey(AccountViewModel::class)
    fun bindBalanceViewModel(balanceViewModel: AccountViewModel): ViewModel
}
