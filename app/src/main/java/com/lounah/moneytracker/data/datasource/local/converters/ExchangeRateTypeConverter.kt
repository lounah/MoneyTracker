package com.lounah.moneytracker.data.datasource.local.converters

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lounah.moneytracker.data.entities.Currency
import java.util.*
import java.util.Collections.emptyList


class ExchangeRateTypeConverter {

    val gson = Gson()

    @TypeConverter
    fun stringToSomeObjectList(data: String?): List<Map<Currency, Double>> {
        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<Map<Currency, Double>>>() {

        }.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: List<Map<Currency, Double>>): String {
        return gson.toJson(someObjects)
    }
}