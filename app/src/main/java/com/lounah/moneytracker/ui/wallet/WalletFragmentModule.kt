package com.lounah.moneytracker.ui.wallet

import com.lounah.moneytracker.data.repositories.CurrencyRepository
import com.lounah.moneytracker.data.repositories.TransactionsRepository
import com.lounah.moneytracker.data.repositories.WalletRepository
import dagger.Module
import dagger.Provides
import ru.popov.bodya.domain.account.WalletInteractor

@Module
class WalletFragmentModule {
    @Provides
    fun provideWalletInteractor(currencyRepository: CurrencyRepository,
                                walletRepository: WalletRepository,
                                transactionsRepository: TransactionsRepository)
            = WalletInteractor(currencyRepository, walletRepository, transactionsRepository)
}