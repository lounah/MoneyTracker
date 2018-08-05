package ru.popov.bodya.data.database.currencies.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import ru.popov.bodya.data.database.BaseDao
import ru.popov.bodya.data.database.currencies.entities.ExchangeRatesEntity

/**
 * @author popovbodya
 */
@Dao
interface CurrenciesDao : BaseDao<ExchangeRatesEntity> {

    @Query("SELECT * FROM exchangeRates")
    fun getExchangeRate(): ExchangeRatesEntity?

}