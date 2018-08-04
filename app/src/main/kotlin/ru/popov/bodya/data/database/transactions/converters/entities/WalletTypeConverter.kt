package ru.popov.bodya.data.database.transactions.converters.entities

import android.arch.persistence.room.TypeConverter
import ru.popov.bodya.domain.transactions.models.WalletType
import ru.popov.bodya.domain.transactions.models.WalletType.*

class WalletTypeConverter {

    @TypeConverter
    fun fromType(type: WalletType): String = type.name

    @TypeConverter
    fun toCurrency(type: String): WalletType =
            when (type) {
                CREDIT_CARD.name -> CREDIT_CARD
                BANK_ACCOUNT.name -> BANK_ACCOUNT
                CASH.name -> CASH
                else -> CREDIT_CARD
            }
}
