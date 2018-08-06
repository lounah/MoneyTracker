package ru.popov.bodya.di.common.modules

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.popov.bodya.di.common.viewmodel.AppViewModelFactory
import ru.popov.bodya.di.common.viewmodel.ViewModelKey
import ru.popov.bodya.presentation.account.AccountViewModel
import ru.popov.bodya.presentation.addtransaction.AddTransactionViewModel
import ru.popov.bodya.presentation.init.InitViewModel

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(AccountViewModel::class)
    fun bindBalanceViewModel(balanceViewModel: AccountViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddTransactionViewModel::class)
    fun bindAddTransactionViewModel(addTransactionViewModel: AddTransactionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(InitViewModel::class)
    fun bindInitViewModel(initViewModel: InitViewModel): ViewModel
}
