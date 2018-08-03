package ru.popov.bodya.di.addtransaction

import com.lounah.moneytracker.ui.wallet.addtransaction.AddTransactionPresenter
import dagger.Module
import dagger.Provides
import ru.popov.bodya.domain.account.WalletInteractor

@Module
class AddTransactionModule {
    @Provides
    fun providePresenter(interactor: WalletInteractor)
            = AddTransactionPresenter(interactor)
}