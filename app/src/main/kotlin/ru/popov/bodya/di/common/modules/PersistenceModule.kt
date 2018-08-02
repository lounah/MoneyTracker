package ru.popov.bodya.di.common.modules

import android.app.Application
import android.arch.persistence.room.Room
import com.lounah.moneytracker.data.datasource.local.AppDatabase
import com.lounah.moneytracker.data.datasource.local.WalletDao
import dagger.Module
import dagger.Provides

@Module
class PersistenceModule {

    @Provides
    fun provideDb(app: Application) =
            Room.databaseBuilder(app, AppDatabase::class.java, "app.db")
                    .fallbackToDestructiveMigration()
                    .build()

    @Provides
    fun provideWalletDao(db: AppDatabase): WalletDao = db.walletDao

    @Provides
    fun provideTransactionsDao(db: AppDatabase) = db.transactionsDao
}
