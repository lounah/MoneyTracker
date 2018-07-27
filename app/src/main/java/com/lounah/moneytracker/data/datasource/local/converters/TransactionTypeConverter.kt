package com.lounah.moneytracker.data.datasource.local.converters

import android.arch.persistence.room.TypeConverter
import com.lounah.moneytracker.data.entities.TransactionType

class TransactionTypeConverter {

    @TypeConverter
    fun fromTransactionTypeToInt(type: TransactionType): Int =
            when (type) {
                TransactionType.FOOD -> 0
                TransactionType.HOME -> 1
                TransactionType.COMMUNAL_PAYMENTS -> 2
                TransactionType.EDUCATION -> 3
                TransactionType.CLOTHES -> 4
                TransactionType.FAMILY -> 5
                TransactionType.REST -> 6
                TransactionType.TREATMENT -> 7
                TransactionType.AUTO -> 8
                TransactionType.OTHER -> 9
                TransactionType.SALARY -> 10
            }

    @TypeConverter
    fun fromIntToTransactionType(type: Int): TransactionType =
            when (type) {
                0 -> TransactionType.FOOD
                2 -> TransactionType.HOME
                3 -> TransactionType.COMMUNAL_PAYMENTS
                4 -> TransactionType.EDUCATION
                5 -> TransactionType.CLOTHES
                6 -> TransactionType.FAMILY
                7 -> TransactionType.REST
                8 -> TransactionType.TREATMENT
                9 -> TransactionType.OTHER
                10 -> TransactionType.SALARY
                else -> TransactionType.HOME
            }
}