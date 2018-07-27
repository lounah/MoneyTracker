package com.lounah.moneytracker.data.entities

import android.arch.persistence.room.*
import java.util.Date

@Entity(tableName = "transactions")
data class Transaction(
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        val date: Date,
        val wallet: WalletType,
        val description: String = "",
        val type: TransactionType,
        val amount: Double,
        val currency: Currency)
