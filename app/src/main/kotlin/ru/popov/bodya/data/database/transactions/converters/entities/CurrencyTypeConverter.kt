package ru.popov.bodya.data.database.transactions.converters.entities

import android.arch.persistence.room.TypeConverter
import ru.popov.bodya.domain.currency.model.Currency
import ru.popov.bodya.domain.currency.model.Currency.*

class CurrencyTypeConverter {

    @TypeConverter
    fun fromType(type: Currency): String = type.name

    @TypeConverter
    fun toCurrency(type: String): Currency =
            when (type) {
                RUB.name -> RUB
                USD.name -> USD
                EUR.name -> EUR
                else -> RUB
            }
}