package ru.popov.bodya.data.database.transactions.converters.models

import ru.popov.bodya.core.converter.Converter
import ru.popov.bodya.data.database.transactions.entities.TransactionEntity
import ru.popov.bodya.domain.transactions.models.Transaction

/**
 *  @author popovbodya
 */
class TransactionEntityConverter : Converter<TransactionEntity, Transaction> {
    override fun reverse(to: Transaction): TransactionEntity {
        return TransactionEntity(0, to.wallet, to.currency, to.category, to.amount, to.date, to.description)
    }

    override fun convert(from: TransactionEntity): Transaction {
        return Transaction(from.wallet, from.currency, from.category, from.amount, from.date, from.description)
    }
}