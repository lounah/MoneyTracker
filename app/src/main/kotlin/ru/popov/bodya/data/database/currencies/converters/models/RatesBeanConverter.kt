package ru.popov.bodya.data.database.currencies.converters.models

import ru.popov.bodya.core.converter.OneWayConverter
import ru.popov.bodya.data.network.beans.RatesBean
import ru.popov.bodya.domain.currency.model.Rates

/**
 * @author popovbodya
 */
class RatesBeanConverter : OneWayConverter<RatesBean, Rates> {
    override fun convert(from: RatesBean): Rates = Rates(from.usd, from.eur)
}