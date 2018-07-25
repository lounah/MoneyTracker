package com.lounah.moneytracker.di

import com.lounah.moneytracker.data.datasource.local.BalanceDao
import com.lounah.moneytracker.data.datasource.local.TransactionsDao
import com.lounah.moneytracker.data.datasource.remote.CurrencyApi
import com.lounah.moneytracker.data.repositories.BalanceRepository
import com.lounah.moneytracker.data.repositories.TransactionsRepository
import com.lounah.moneytracker.di.modules.NetworkModule
import com.lounah.moneytracker.di.modules.PersistenceModule
import com.lounah.moneytracker.di.modules.ViewModelModule
import dagger.Module
import dagger.Provides

@Module(includes = [
    ViewModelModule::class,
    NetworkModule::class,
    PersistenceModule::class])
class AppModule {

    @Provides
    fun provideBalanceRepository(balanceDao: BalanceDao)
            = BalanceRepository(balanceDao)

    @Provides
    fun provideTransactionsRepository(transactionsDao: TransactionsDao)
            = TransactionsRepository(transactionsDao)
}
