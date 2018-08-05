package ru.popov.bodya.data.database.currencies.converters.beans

import ru.popov.bodya.core.converter.Converter
import ru.popov.bodya.data.database.currencies.entities.ExchangeRatesEntity
import ru.popov.bodya.data.network.beans.ExchangeRatesBean

/**
 * @author popovbodya
 */
class ExchangeRatesEntityBeanConverter constructor(private val ratesEntityBeanConverter: RatesEntityBeanConverter) :
        Converter<ExchangeRatesBean, ExchangeRatesEntity> {

    override fun reverse(to: ExchangeRatesEntity): ExchangeRatesBean {
        return ExchangeRatesBean(true, to.timestamp, to.base, to.date, ratesEntityBeanConverter.reverse(to.rates))
    }

    override fun convert(from: ExchangeRatesBean): ExchangeRatesEntity {
        return ExchangeRatesEntity(1, from.timestamp, from.base, from.date, ratesEntityBeanConverter.convert(from.rates))
    }
}