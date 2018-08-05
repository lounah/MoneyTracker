package ru.popov.bodya.di.init

import dagger.Module
import dagger.Provides
import ru.popov.bodya.data.database.currencies.converters.beans.ExchangeRatesEntityBeanConverter
import ru.popov.bodya.data.database.currencies.converters.beans.RatesEntityBeanConverter
import ru.popov.bodya.data.database.currencies.converters.models.ExchangeRatesBeanConverter
import ru.popov.bodya.data.database.currencies.converters.models.ExchangeRatesEntityConverter
import ru.popov.bodya.data.database.currencies.converters.models.RatesBeanConverter
import ru.popov.bodya.data.database.currencies.converters.models.RatesEntityConverter
import ru.popov.bodya.data.database.currencies.dao.CurrenciesDao
import ru.popov.bodya.data.network.api.ExchangeRateApiWrapper
import ru.popov.bodya.data.repositories.CurrenciesRepository
import ru.popov.bodya.domain.currency.CurrencyInteractor

/**
 *  @author popovbodya
 */
@Module
class InitModule {
    @Provides
    fun provideCurrenciesInteractor(currenciesRepository: CurrenciesRepository): CurrencyInteractor =
            CurrencyInteractor(currenciesRepository)

    @Provides
    fun provideCurrenciesRepository(exchangeRateApiWrapper: ExchangeRateApiWrapper, currenciesDao: CurrenciesDao): CurrenciesRepository =
            CurrenciesRepository(exchangeRateApiWrapper, currenciesDao, ExchangeRatesEntityBeanConverter(RatesEntityBeanConverter()),
                    ExchangeRatesBeanConverter(RatesBeanConverter()), ExchangeRatesEntityConverter(RatesEntityConverter()))
}