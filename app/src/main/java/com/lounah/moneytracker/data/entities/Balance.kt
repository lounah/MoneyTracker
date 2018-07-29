package com.lounah.moneytracker.data.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.Date

@Entity(tableName = "balance")
data class Balance(@PrimaryKey(autoGenerate = true)
                   val id: Int,
                   val amount: Double = 0.0,
                   val currency: Currency,
                   var lastUpdate: Date)