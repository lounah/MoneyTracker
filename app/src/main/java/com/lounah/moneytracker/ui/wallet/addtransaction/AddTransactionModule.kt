package com.lounah.moneytracker.ui.wallet.addtransaction

import dagger.Module
import dagger.Provides
import ru.popov.bodya.domain.account.WalletInteractor

@Module
class AddTransactionModule {
    @Provides
    fun providePresenter(interactor: WalletInteractor)
            = AddTransactionPresenter(interactor)
}