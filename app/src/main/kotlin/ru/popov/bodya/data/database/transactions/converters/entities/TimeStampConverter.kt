package ru.popov.bodya.data.database.transactions.converters.entities

import android.arch.persistence.room.TypeConverter
import java.util.*

class TimeStampConverter {
    @TypeConverter
    fun fromDate(date: Date) = date.time

    @TypeConverter
    fun toDate(millisSinceEpoch: Long) = Date(millisSinceEpoch)
}