package com.lounah.moneytracker.di.modules

import android.app.Application
import android.arch.persistence.room.Room
import com.lounah.moneytracker.data.datasource.local.AppDatabase
import com.lounah.moneytracker.data.datasource.local.BalanceDao
import dagger.Module
import dagger.Provides

@Module
class PersistenceModule {

    @Provides
    fun provideDb(app : Application) =
            Room.databaseBuilder(app, AppDatabase::class.java, "app.db")
                    .fallbackToDestructiveMigration()
                    .build()

    @Provides
    fun provideBalanceDao(db: AppDatabase): BalanceDao = db.balanceDao

    @Provides
    fun provideTransactionsDao(db: AppDatabase) = db.transactionsDao
}