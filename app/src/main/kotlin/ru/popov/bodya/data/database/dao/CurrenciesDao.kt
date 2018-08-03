package ru.popov.bodya.data.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.lounah.moneytracker.data.datasource.local.BaseDao
import ru.popov.bodya.data.database.entities.ExchangeRatesEntity

/**
 * @author popovbodya
 */
@Dao
interface CurrenciesDao : BaseDao<ExchangeRatesEntity> {

    @Query("SELECT * FROM exchangeRates")
    fun getExchangeRate(): ExchangeRatesEntity?

}