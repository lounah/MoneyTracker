package ru.popov.bodya.data.repositories

import io.reactivex.Completable
import io.reactivex.Single
import ru.popov.bodya.data.database.transactions.converters.models.TransactionEntityConverter
import ru.popov.bodya.data.database.transactions.dao.TransactionsDao
import ru.popov.bodya.domain.currency.model.Currency
import ru.popov.bodya.domain.transactions.models.*
import java.util.*

/**
 *  @author popovbodya
 */
class TransactionsRepository(private val transactionsDao: TransactionsDao,
                             private val transactionsEntityConverter: TransactionEntityConverter) {

    fun getAllTransactionsByWallet(walletType: WalletType): Single<List<Transaction>> =
            Single.fromCallable {
                transactionsDao.getAllTransactionsByWallet(walletType)
                        .map { transactionsEntityConverter.convert(it) }
            }
//            Single.fromCallable { createStubTransactionList() }

    fun getIncomeTransactionsByWallet(walletType: WalletType): Single<List<Transaction>> =
            Single.fromCallable {
                transactionsDao.getIncomeTransactionsByWallet(walletType)
                        .map { transactionsEntityConverter.convert(it) }
            }

    fun getExpenseTransactionsByWallet(walletType: WalletType): Single<List<Transaction>> =
            Single.fromCallable {
                transactionsDao.getExpenseTransactionsByWallet(walletType)
                        .map { transactionsEntityConverter.convert(it) }
            }

    fun addIncomeTransaction(transaction: Transaction): Completable {
        return Completable.fromAction { transactionsDao.insert(transactionsEntityConverter.reverse(transaction)) }
    }

    fun addExpenseTransaction(transaction: Transaction): Completable {
        return Completable.fromAction { transactionsDao.insert(transactionsEntityConverter.reverse(transaction)) }
    }

    private fun createStubTransactionList(): List<Transaction> {
        return listOf(
                Transaction(WalletType.CREDIT_CARD, Currency.USD, TransactionsCategory.Expense(ExpenseCategory.FAMILY), 50.0, Calendar.getInstance().time, "1"),
                Transaction(WalletType.CREDIT_CARD, Currency.USD, TransactionsCategory.Expense(ExpenseCategory.FAMILY), 25.0, Calendar.getInstance().time, "2"),
                Transaction(WalletType.CREDIT_CARD, Currency.EUR, TransactionsCategory.Income(IncomeCategory.GIFT), 30.0, Calendar.getInstance().time, "3"),
                Transaction(WalletType.CREDIT_CARD, Currency.USD, TransactionsCategory.Income(IncomeCategory.SALARY), 50.0, Calendar.getInstance().time, "4"),
                Transaction(WalletType.CREDIT_CARD, Currency.RUB, TransactionsCategory.Income(IncomeCategory.OTHER_INCOME), 50.0, Calendar.getInstance().time, "5"),
                Transaction(WalletType.CREDIT_CARD, Currency.RUB, TransactionsCategory.Income(IncomeCategory.OTHER_INCOME), 50.0, Calendar.getInstance().time, "6")
        )
    }
}