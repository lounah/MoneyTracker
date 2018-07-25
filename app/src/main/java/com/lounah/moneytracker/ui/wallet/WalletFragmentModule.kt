package com.lounah.moneytracker.ui.wallet

import com.lounah.moneytracker.data.repositories.BalanceRepository
import com.lounah.moneytracker.data.repositories.TransactionsRepository
import com.lounah.moneytracker.domain.interactors.WalletInteractor
import dagger.Module
import dagger.Provides

@Module
class WalletFragmentModule {

    @Provides
    fun provideWalletInteractor(balanceRepository: BalanceRepository,
                                transactionsRepository: TransactionsRepository)
            = WalletInteractor(balanceRepository, transactionsRepository)

}