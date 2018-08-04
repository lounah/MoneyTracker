package com.lounah.moneytracker.data.datasource.local.converters

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.popov.bodya.domain.currency.model.Currency
import java.util.*


class ExchangeRateTypeConverter {

    private val gson = Gson()

    @TypeConverter
    fun gsonToExchangeRateList(data: String?): List<Map<Currency, Double>> {
        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<Map<Currency, Double>>>() {}.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun exchangeRateListToGson(someObjects: List<Map<Currency, Double>>): String {
        return gson.toJson(someObjects)
    }
}