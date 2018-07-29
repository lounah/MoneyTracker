package com.lounah.moneytracker.data.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.Date

@Entity(tableName = "wallets")
data class Wallet(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        val amount: Double = 0.0,
        val balanceType: WalletType,
        val currency: Currency,
        var lastUpdate: Date)