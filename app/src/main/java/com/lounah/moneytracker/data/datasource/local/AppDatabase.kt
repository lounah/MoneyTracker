package com.lounah.moneytracker.data.datasource.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.lounah.moneytracker.data.datasource.local.converters.*
import com.lounah.moneytracker.data.entities.Balance
import com.lounah.moneytracker.data.entities.Transaction

@TypeConverters(
        TimeStampConverter::class,
        CurrencyTypeConverter::class,
        TransactionTypeConverter::class,
        ExchangeRateTypeConverter::class,
        WalletTypeConverter::class)
@Database(entities = [Balance::class, Transaction::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val transactionsDao: TransactionsDao
    abstract val balanceDao: BalanceDao
}