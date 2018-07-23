package com.lounah.moneytracker.data.datasource.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.lounah.moneytracker.data.datasource.local.converters.CurrencyTypeConverter
import com.lounah.moneytracker.data.datasource.local.converters.ExchangeRateTypeConverter
import com.lounah.moneytracker.data.datasource.local.converters.TimeStampConverter
import com.lounah.moneytracker.data.datasource.local.converters.TransactionTypeConverter
import com.lounah.moneytracker.data.entities.Balance
import com.lounah.moneytracker.data.entities.Transaction

@TypeConverters(TimeStampConverter::class,
        CurrencyTypeConverter::class,
        TransactionTypeConverter::class,
        ExchangeRateTypeConverter::class)
@Database(entities = [Balance::class, Transaction::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val transactionsDao : TransactionsDao
    abstract val balanceDao : BalanceDao
}