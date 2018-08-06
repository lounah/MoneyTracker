package ru.popov.bodya.data.database.currencies.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * @author popovbodya
 */
@Entity(tableName = "exchangeRates")
data class ExchangeRatesEntity(
        @PrimaryKey(autoGenerate = true)
        var id: Int = 0,
        val timestamp: Long,
        val base: String,
        val date: String,
        val rates: RatesEntity
)