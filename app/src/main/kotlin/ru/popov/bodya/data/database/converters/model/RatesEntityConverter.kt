package ru.popov.bodya.data.database.converters.model

import ru.popov.bodya.core.converter.OneWayConverter
import ru.popov.bodya.data.database.entities.RatesEntity
import ru.popov.bodya.domain.currency.model.Rates

/**
 * @author popovbodya
 */
class RatesEntityConverter: OneWayConverter<RatesEntity, Rates> {
    override fun convert(from: RatesEntity): Rates {
        return Rates(from.usd, from.eur)
    }
}