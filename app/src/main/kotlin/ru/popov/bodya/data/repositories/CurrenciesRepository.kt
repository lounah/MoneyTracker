package ru.popov.bodya.data.repositories

import io.reactivex.Single
import ru.popov.bodya.data.database.converters.beans.ExchangeRatesEntityBeanConverter
import ru.popov.bodya.data.database.converters.model.ExchangeRatesBeanConverter
import ru.popov.bodya.data.database.converters.model.ExchangeRatesEntityConverter
import ru.popov.bodya.data.database.dao.CurrenciesDao
import ru.popov.bodya.data.network.api.ExchangeRateApiWrapper
import ru.popov.bodya.data.network.beans.ExchangeRatesBean
import ru.popov.bodya.domain.currency.model.ExchangeRates

/**
 * @author popovbodya
 */
class CurrenciesRepository(private val exchangeRateApiWrapper: ExchangeRateApiWrapper,
                           private val currenciesDao: CurrenciesDao,
                           private val exchangeRatesEntityBeanConverter: ExchangeRatesEntityBeanConverter,
                           private val exchangeRatesBeanConverter: ExchangeRatesBeanConverter,
                           private val exchangeRatesEntityConverter: ExchangeRatesEntityConverter) {

    fun getExchangeRate(): Single<ExchangeRates> = exchangeRateApiWrapper
            .getCurrentRate()
            .doOnSuccess { saveExchangeRate(it) }
            .map { exchangeRatesBeanConverter.convert(it) }

    fun getCachedExchangeRate(): Single<ExchangeRates> =
            Single.fromCallable { currenciesDao.getExchangeRate() }
                    .map { exchangeRatesEntityConverter.convert(it) }

    private fun saveExchangeRate(bean: ExchangeRatesBean) {
        val entity = exchangeRatesEntityBeanConverter.convert(bean)
        val exchangeRate = currenciesDao.getExchangeRate()
        if (exchangeRate != null) {
            currenciesDao.update(entity)
        } else {
            currenciesDao.insert(entity)
        }
    }
}