package com.lounah.moneytracker.data.datasource.local.converters

import android.arch.persistence.room.TypeConverter
import ru.popov.bodya.data.database.entities.RatesEntity

/**
 * @author popovbodya
 */
class RatesTypeConverter {

    companion object {
        const val delimiter = ":"
    }

    @TypeConverter
    fun fromType(type: RatesEntity): String {
        return "${type.usd}$delimiter${type.eur}"
    }

    @TypeConverter
    fun toRates(type: String): RatesEntity {
        val tokens = type.split(delimiter)
        return RatesEntity(tokens[0].toDouble(), tokens[1].toDouble())
    }
}