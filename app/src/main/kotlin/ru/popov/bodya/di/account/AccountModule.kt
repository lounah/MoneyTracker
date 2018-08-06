package ru.popov.bodya.di.account

import dagger.Module
import dagger.Provides
import ru.popov.bodya.data.database.currencies.converters.beans.ExchangeRatesEntityBeanConverter
import ru.popov.bodya.data.database.currencies.converters.beans.RatesEntityBeanConverter
import ru.popov.bodya.data.database.currencies.converters.models.ExchangeRatesBeanConverter
import ru.popov.bodya.data.database.currencies.converters.models.ExchangeRatesEntityConverter
import ru.popov.bodya.data.database.currencies.converters.models.RatesBeanConverter
import ru.popov.bodya.data.database.currencies.converters.models.RatesEntityConverter
import ru.popov.bodya.data.database.currencies.dao.CurrenciesDao
import ru.popov.bodya.data.database.transactions.converters.models.TransactionEntityConverter
import ru.popov.bodya.data.database.transactions.dao.TransactionsDao
import ru.popov.bodya.data.network.api.ExchangeRateApiWrapper
import ru.popov.bodya.data.repositories.CurrenciesRepository
import ru.popov.bodya.data.repositories.TransactionsRepository
import ru.popov.bodya.domain.currency.CurrencyInteractor
import ru.popov.bodya.domain.transactions.TransactionsInteractor

/**
 * @author popovbodya
 */
@Module
class AccountModule {

    @Provides
    fun provideTransactionsInteractor(transactionsRepository: TransactionsRepository): TransactionsInteractor =
            TransactionsInteractor(transactionsRepository)

    @Provides
    fun provideTransactionsRepository(transactionsDao: TransactionsDao) =
            TransactionsRepository(transactionsDao, TransactionEntityConverter())

    @Provides
    fun provideCurrenciesInteractor(currenciesRepository: CurrenciesRepository): CurrencyInteractor =
            CurrencyInteractor(currenciesRepository)

    @Provides
    fun provideCurrenciesRepository(exchangeRateApiWrapper: ExchangeRateApiWrapper, currenciesDao: CurrenciesDao): CurrenciesRepository =
            CurrenciesRepository(exchangeRateApiWrapper, currenciesDao, ExchangeRatesEntityBeanConverter(RatesEntityBeanConverter()),
                    ExchangeRatesBeanConverter(RatesBeanConverter()), ExchangeRatesEntityConverter(RatesEntityConverter()))

}