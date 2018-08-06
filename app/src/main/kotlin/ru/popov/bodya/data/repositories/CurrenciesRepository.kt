package ru.popov.bodya.data.repositories

import io.reactivex.Single
import ru.popov.bodya.data.database.currencies.converters.beans.ExchangeRatesEntityBeanConverter
import ru.popov.bodya.data.database.currencies.converters.models.ExchangeRatesBeanConverter
import ru.popov.bodya.data.database.currencies.converters.models.ExchangeRatesEntityConverter
import ru.popov.bodya.data.database.currencies.dao.CurrenciesDao
import ru.popov.bodya.data.database.currencies.entities.ExchangeRatesEntity
import ru.popov.bodya.data.network.api.ExchangeRateApiWrapper
import ru.popov.bodya.data.network.beans.ExchangeRatesBean
import ru.popov.bodya.data.network.beans.RatesBean
import ru.popov.bodya.domain.currency.model.ExchangeRates
import timber.log.Timber
import java.util.*

/**
 * @author popovbodya
 */
class CurrenciesRepository(private val exchangeRateApiWrapper: ExchangeRateApiWrapper,
                           private val currenciesDao: CurrenciesDao,
                           private val exchangeRatesEntityBeanConverter: ExchangeRatesEntityBeanConverter,
                           private val exchangeRatesBeanConverter: ExchangeRatesBeanConverter,
                           private val exchangeRatesEntityConverter: ExchangeRatesEntityConverter) {

    companion object {
        const val BASE = "RUB"
        const val USD_RATE = 0.015807
        const val EUR_RATE = 0.013662
    }

    fun getExchangeRate(): Single<ExchangeRates> = exchangeRateApiWrapper
            .getCurrentRate()
            .onErrorResumeNext {
                Timber.e(it)
                Single.just(createStubExchangeRateBean())
            }
            .doOnSuccess { saveExchangeRate(it) }
            .map { exchangeRatesBeanConverter.convert(it) }

    fun getCachedExchangeRate(): Single<ExchangeRates> =
            Single.fromCallable { currenciesDao.getExchangeRate() }
                    .map { exchangeRatesEntityConverter.convert(it) }

    private fun saveExchangeRate(bean: ExchangeRatesBean) {
        val entity = exchangeRatesEntityBeanConverter.convert(bean)
        saveWithDao(entity)
    }

    private fun createStubExchangeRateBean(): ExchangeRatesBean {
        val calendar = Calendar.getInstance()
        return ExchangeRatesBean(true, calendar.timeInMillis, BASE, calendar.time.toString(), RatesBean(USD_RATE, EUR_RATE))
    }

    private fun saveWithDao(entity: ExchangeRatesEntity) {
        val exchangeRate = currenciesDao.getExchangeRate()
        if (exchangeRate != null) {
            currenciesDao.update(entity)
        } else {
            currenciesDao.insert(entity)
        }
    }

}