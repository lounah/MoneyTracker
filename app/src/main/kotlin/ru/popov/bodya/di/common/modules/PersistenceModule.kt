package ru.popov.bodya.di.common.modules

import android.app.Application
import android.arch.persistence.room.Room
import dagger.Module
import dagger.Provides
import ru.popov.bodya.data.database.AppDatabase
import ru.popov.bodya.data.database.currencies.dao.CurrenciesDao
import ru.popov.bodya.data.database.transactions.dao.TransactionsDao
import javax.inject.Singleton

@Module
class PersistenceModule {

    @Singleton
    @Provides
    fun provideTransactionsDao(db: AppDatabase): TransactionsDao = db.transactionsDao

    @Singleton
    @Provides
    fun provideCurrenciesDao(db: AppDatabase): CurrenciesDao = db.currenciesDao

    @Singleton
    @Provides
    fun provideDb(app: Application) =
            Room.databaseBuilder(app, AppDatabase::class.java, "MoneyTracker.db")
                    .fallbackToDestructiveMigration()
                    .build()
}
