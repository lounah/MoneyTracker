package com.lounah.moneytracker.data.entities

import android.arch.persistence.room.*
import java.util.Date

@Entity(tableName = "transactions")
data class Transaction(
        @PrimaryKey(autoGenerate = true)
        var id: Int = 0,
        val date: Date,
        var wallet: WalletType = WalletType.CASH,
        val description: String = "",
        val type: TransactionType = TransactionType.OTHER,
        val amount: Double = 0.0,
        val currency: Currency = Currency.RUB)
