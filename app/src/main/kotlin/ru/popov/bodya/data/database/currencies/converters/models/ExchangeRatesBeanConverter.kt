package ru.popov.bodya.data.database.currencies.converters.models

import ru.popov.bodya.core.converter.OneWayConverter
import ru.popov.bodya.data.network.beans.ExchangeRatesBean
import ru.popov.bodya.domain.currency.model.ExchangeRates

/**
 * @author popovbodya
 */
class ExchangeRatesBeanConverter(private val ratesBeanConverter: RatesBeanConverter) : OneWayConverter<ExchangeRatesBean, ExchangeRates> {
    override fun convert(from: ExchangeRatesBean): ExchangeRates {
        return ExchangeRates(from.timestamp, from.base, from.date, ratesBeanConverter.convert(from.rates))
    }
}