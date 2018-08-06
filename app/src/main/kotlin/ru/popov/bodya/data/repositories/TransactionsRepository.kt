package ru.popov.bodya.data.repositories

import io.reactivex.Completable
import io.reactivex.Single
import ru.popov.bodya.data.database.transactions.converters.models.TransactionEntityConverter
import ru.popov.bodya.data.database.transactions.dao.TransactionsDao
import ru.popov.bodya.domain.transactions.models.Transaction
import ru.popov.bodya.domain.transactions.models.WalletType

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
}