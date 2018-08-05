package ru.popov.bodya.domain.currency

import io.reactivex.Single
import ru.popov.bodya.data.repositories.CurrenciesRepository
import ru.popov.bodya.domain.currency.model.ExchangeRates

/**
 * @author popovbodya
 */
class CurrencyInteractor(private val currenciesRepository: CurrenciesRepository) {

    fun getExchangeRate(): Single<ExchangeRates> = currenciesRepository.getExchangeRate()

    fun getCachedExchangeRate(): Single<ExchangeRates> = currenciesRepository.getCachedExchangeRate()

    fun getUsdRate(exchangeRates: ExchangeRates): Double = 1 / exchangeRates.rates.usd

    fun getEurRate(exchangeRates: ExchangeRates): Double = 1 / exchangeRates.rates.eur

}