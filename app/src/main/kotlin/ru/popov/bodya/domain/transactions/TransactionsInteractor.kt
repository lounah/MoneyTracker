package ru.popov.bodya.domain.transactions

import io.reactivex.Completable
import io.reactivex.Single
import ru.popov.bodya.data.repositories.TransactionsRepository
import ru.popov.bodya.domain.currency.model.Currency
import ru.popov.bodya.domain.transactions.models.Transaction
import ru.popov.bodya.domain.transactions.models.TransactionsCategory
import ru.popov.bodya.domain.transactions.models.WalletType
import java.util.*

/**
 * @author popovbodya
 */
class TransactionsInteractor(private val transactionsRepository: TransactionsRepository) {

    fun getAllTransactionsByWallet(walletType: WalletType): Single<List<Transaction>> =
            transactionsRepository.getAllTransactionsByWallet(walletType)

    fun getIncomeTransactionsByWallet(walletType: WalletType): Single<List<Transaction>> =
            transactionsRepository.getIncomeTransactionsByWallet(walletType)

    fun getExpenseTransactionsByWallet(walletType: WalletType): Single<List<Transaction>> =
            transactionsRepository.getExpenseTransactionsByWallet(walletType)

    fun addIncomeTransaction(selectedWallet: WalletType, selectedCategory: TransactionsCategory.Income, selectedCurrency: Currency, amount: Double, date: Date, comment: String): Completable {
        return transactionsRepository.addIncomeTransaction(Transaction(selectedWallet, selectedCurrency, selectedCategory, amount, date, comment))
    }

    fun addExpenseTransaction(selectedWallet: WalletType, selectedCategory: TransactionsCategory.Expense, selectedCurrency: Currency, amount: Double, date: Date, comment: String): Completable {
        return transactionsRepository.addExpenseTransaction(Transaction(selectedWallet, selectedCurrency, selectedCategory, amount, date, comment))
    }
}