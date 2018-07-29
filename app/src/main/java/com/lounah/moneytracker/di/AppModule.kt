package com.lounah.moneytracker.di

import com.lounah.moneytracker.data.datasource.local.BalanceDao
import com.lounah.moneytracker.data.datasource.local.TransactionsDao
import com.lounah.moneytracker.data.datasource.remote.CurrencyApi
import com.lounah.moneytracker.data.repositories.WalletRepository
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

    companion object {
        private const val API_URL = "https://lounah.com"
    }

    @Provides
    fun provideRepository(api: CurrencyApi, balanceDao: BalanceDao, transactionsDao: TransactionsDao)
            = WalletRepository(api, balanceDao, transactionsDao)
}
