package com.lounah.moneytracker.data.datasource.local.converters

import android.arch.persistence.room.TypeConverter
import com.lounah.moneytracker.data.entities.Currency

class CurrencyTypeConverter {

    @TypeConverter
    fun fromType(type: Currency): Int =
            when (type) {
                Currency.EUR -> 0
                Currency.USD -> 1
                Currency.RUR -> 2
            }

    @TypeConverter
    fun toCurrency(type: Int): Currency =
            when (type) {
                0 -> Currency.EUR
                1 -> Currency.USD
                2 -> Currency.RUR
                else -> Currency.RUR
            }
}