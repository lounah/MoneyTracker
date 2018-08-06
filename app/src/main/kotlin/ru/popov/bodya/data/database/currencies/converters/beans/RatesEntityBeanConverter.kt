package ru.popov.bodya.data.database.currencies.converters.beans

import ru.popov.bodya.core.converter.Converter
import ru.popov.bodya.data.database.currencies.entities.RatesEntity
import ru.popov.bodya.data.network.beans.RatesBean

/**
 * @author popovbodya
 */
class RatesEntityBeanConverter : Converter<RatesBean, RatesEntity> {
    override fun reverse(to: RatesEntity): RatesBean {
        return RatesBean(to.usd, to.eur)
    }

    override fun convert(from: RatesBean): RatesEntity {
        return RatesEntity(from.usd, from.eur)
    }
}