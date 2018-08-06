package ru.popov.bodya.data.database.transactions.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import ru.popov.bodya.domain.currency.model.Currency
import ru.popov.bodya.domain.transactions.models.TransactionsCategory
import ru.popov.bodya.domain.transactions.models.WalletType
import java.util.*

@Entity(tableName = "transactions")
data class TransactionEntity(
        @PrimaryKey(autoGenerate = true)
        var id: Int,
        var wallet: WalletType,
        val currency: Currency,
        val category: TransactionsCategory,
        val amount: Double,
        val date: Date,
        val description: String
)
