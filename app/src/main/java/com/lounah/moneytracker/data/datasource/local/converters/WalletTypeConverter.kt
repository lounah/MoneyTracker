package com.lounah.moneytracker.data.datasource.local.converters

import android.arch.persistence.room.TypeConverter
import com.lounah.moneytracker.data.entities.WalletType

class WalletTypeConverter {

    @TypeConverter
    fun fromType(type: WalletType): Int =
            when (type) {
                WalletType.BANK_ACCOUNT -> 0
                WalletType.CASH -> 1
                WalletType.CREDIT_CARD -> 2
            }

    @TypeConverter
    fun toCurrency(type: Int): WalletType =
            when (type) {
                0 -> WalletType.BANK_ACCOUNT
                1 -> WalletType.CASH
                2 -> WalletType.CREDIT_CARD
                else -> WalletType.CASH
            }
}
