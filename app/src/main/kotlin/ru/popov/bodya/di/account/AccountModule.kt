package ru.popov.bodya.di.account

import dagger.Module
import dagger.Provides
import ru.popov.bodya.data.database.converters.beans.ExchangeRatesEntityBeanConverter
import ru.popov.bodya.data.database.converters.beans.RatesEntityBeanConverter
import ru.popov.bodya.data.database.converters.model.ExchangeRatesBeanConverter
import ru.popov.bodya.data.database.converters.model.ExchangeRatesEntityConverter
import ru.popov.bodya.data.database.converters.model.RatesBeanConverter
import ru.popov.bodya.data.database.converters.model.RatesEntityConverter
import ru.popov.bodya.data.database.dao.CurrenciesDao
import ru.popov.bodya.data.network.api.ExchangeRateApiWrapper
import ru.popov.bodya.data.repositories.CurrenciesRepository
import ru.popov.bodya.domain.currency.CurrencyInteractor

/**
 * @author popovbodya
 */
@Module
class AccountModule {

    @Provides
    fun provideCurrenciesInteractor(currenciesRepository: CurrenciesRepository): CurrencyInteractor =
            CurrencyInteractor(currenciesRepository)

    @Provides
    fun provideCurrenciesRepository(exchangeRateApiWrapper: ExchangeRateApiWrapper, currenciesDao: CurrenciesDao): CurrenciesRepository =
            CurrenciesRepository(exchangeRateApiWrapper, currenciesDao, ExchangeRatesEntityBeanConverter(RatesEntityBeanConverter()),
                    ExchangeRatesBeanConverter(RatesBeanConverter()), ExchangeRatesEntityConverter(RatesEntityConverter()))

}