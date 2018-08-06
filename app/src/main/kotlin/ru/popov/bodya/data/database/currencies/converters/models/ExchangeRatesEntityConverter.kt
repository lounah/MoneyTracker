package ru.popov.bodya.data.database.currencies.converters.models

import ru.popov.bodya.core.converter.OneWayConverter
import ru.popov.bodya.data.database.currencies.entities.ExchangeRatesEntity
import ru.popov.bodya.domain.currency.model.ExchangeRates

/**
 * @author popovbodya
 */
class ExchangeRatesEntityConverter(private val ratesEntityConverter: RatesEntityConverter) : OneWayConverter<ExchangeRatesEntity, ExchangeRates> {
    override fun convert(from: ExchangeRatesEntity): ExchangeRates {
        return ExchangeRates(from.timestamp, from.base, from.date, ratesEntityConverter.convert(from.rates))
    }
}