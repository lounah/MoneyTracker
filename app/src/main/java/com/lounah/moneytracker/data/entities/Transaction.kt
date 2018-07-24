package com.lounah.moneytracker.data.entities

import android.arch.persistence.room.*
import java.util.Date

@Entity(tableName = "transactions")
data class Transaction(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        var date: Date,
        val type: TransactionType,
        val amount: Double,
        val currency: Currency,
        val exchangeRate: List<Map<Currency, Double>>)
