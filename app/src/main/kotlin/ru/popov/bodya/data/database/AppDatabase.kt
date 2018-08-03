package ru.popov.bodya.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.lounah.moneytracker.data.datasource.local.converters.RatesTypeConverter
import ru.popov.bodya.data.database.dao.CurrenciesDao
import ru.popov.bodya.data.database.entities.ExchangeRatesEntity

//
//@TypeConverters(
//        TimeStampConverter::class,
//        CurrencyTypeConverter::class,
//        TransactionTypeConverter::class,
//        WalletTypeConverter::class)
//@Database(entities = [Wallet::class, Transaction::class], version = 2, exportSchema = false)
//abstract class AppDatabase : RoomDatabase() {
//    abstract val transactionsDao: TransactionsDao
//    abstract val walletDao: WalletDao
//}

@TypeConverters(RatesTypeConverter::class)
@Database(entities = [ExchangeRatesEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract val currenciesDao: CurrenciesDao

}
