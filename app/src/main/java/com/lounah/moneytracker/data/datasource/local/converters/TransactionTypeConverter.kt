package com.lounah.moneytracker.data.datasource.local.converters

import android.arch.persistence.room.TypeConverter
import com.lounah.moneytracker.data.entities.TransactionType

class TransactionTypeConverter {

    @TypeConverter
    fun fromTransactionType(type : TransactionType) : Int =
            when(type) {
                TransactionType.EXPENSE -> 0
                TransactionType.INCOME -> 1
            }

    @TypeConverter
    fun toTransaction(type : Int) : TransactionType =
            when(type) {
                0 -> TransactionType.EXPENSE
                1 -> TransactionType.INCOME
                else -> TransactionType.INCOME
            }
}