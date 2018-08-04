package ru.popov.bodya.domain.transactions.models

import ru.popov.bodya.domain.currency.model.Currency
import java.util.*

/**
 *  @author popovbodya
 */
data class Transaction(val wallet: WalletType,
                       val currency: Currency,
                       val category: TransactionsCategory,
                       val amount: Double,
                       val date: Date,
                       val description: String)

