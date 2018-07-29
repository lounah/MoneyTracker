package com.lounah.moneytracker.ui.wallet.addtransaction

import com.lounah.moneytracker.domain.interactors.WalletInteractor
import dagger.Module
import dagger.Provides

@Module
class AddTransactionModule {
    @Provides
    fun providePresenter(interactor: WalletInteractor)
            = AddTransactionPresenter(interactor)
}