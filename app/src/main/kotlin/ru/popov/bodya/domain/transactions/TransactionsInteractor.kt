package ru.popov.bodya.domain.transactions

import ru.popov.bodya.domain.transactions.models.WalletType
import io.reactivex.Single
import ru.popov.bodya.data.repositories.TransactionsRepository
import ru.popov.bodya.domain.transactions.models.Transaction

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
}