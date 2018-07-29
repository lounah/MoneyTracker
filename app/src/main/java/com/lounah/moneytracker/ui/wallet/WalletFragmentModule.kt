package com.lounah.moneytracker.ui.wallet

import com.lounah.moneytracker.data.repositories.CurrencyRepository
import com.lounah.moneytracker.data.repositories.TransactionsRepository
import com.lounah.moneytracker.data.repositories.WalletRepository
import com.lounah.moneytracker.domain.interactors.WalletInteractor
import dagger.Module
import dagger.Provides

@Module
class WalletFragmentModule {
    @Provides
    fun provideWalletInteractor(currencyRepository: CurrencyRepository,
                                walletRepository: WalletRepository,
                                transactionsRepository: TransactionsRepository)
            = WalletInteractor(currencyRepository, walletRepository, transactionsRepository)
}