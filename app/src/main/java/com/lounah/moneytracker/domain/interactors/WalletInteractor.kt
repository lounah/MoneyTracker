package com.lounah.moneytracker.domain.interactors

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.lounah.moneytracker.data.entities.*
import com.lounah.moneytracker.data.entities.Currency
import com.lounah.moneytracker.data.repositories.BalanceRepository
import com.lounah.moneytracker.data.repositories.TransactionsRepository
import java.util.*
import javax.inject.Inject

class WalletInteractor @Inject constructor(private val balanceRepo: BalanceRepository,
                                           private val transactionsRepository: TransactionsRepository) {

    fun createTransaction(transaction: Transaction) {

    }

    fun getTransactions() : LiveData<List<Transaction>> {
        val result = MutableLiveData<List<Transaction>>()
        result.value = listOf(Transaction(0, Date(), TransactionType.INCOME, 164.00, Currency.RUB, listOf(mapOf(Pair(Currency.USD, 0.016)))),
                Transaction(1, Date(), TransactionType.EXPENSE, -1644.00, Currency.RUB, listOf(mapOf(Pair(Currency.USD, 0.016)))),
                Transaction(2, Date(), TransactionType.INCOME, 11564.00, Currency.RUB, listOf(mapOf(Pair(Currency.USD, 0.016)))),
                Transaction(3, Date(), TransactionType.EXPENSE, -104.00, Currency.RUB, listOf(mapOf(Pair(Currency.USD, 0.016)))),
                Transaction(4, Date(), TransactionType.EXPENSE, -1632.00, Currency.RUB, listOf(mapOf(Pair(Currency.USD, 0.016)))),
                Transaction(4, Date(), TransactionType.EXPENSE, -1632.00, Currency.RUB, listOf(mapOf(Pair(Currency.USD, 0.016)))),
                Transaction(4, Date(), TransactionType.EXPENSE, -1632.00, Currency.RUB, listOf(mapOf(Pair(Currency.USD, 0.016)))),
                Transaction(4, Date(), TransactionType.EXPENSE, -1632.00, Currency.RUB, listOf(mapOf(Pair(Currency.USD, 0.016)))),
                Transaction(4, Date(), TransactionType.EXPENSE, -1632.00, Currency.RUB, listOf(mapOf(Pair(Currency.USD, 0.016)))),
                Transaction(5, Date(), TransactionType.INCOME, 16423.00, Currency.RUB, listOf(mapOf(Pair(Currency.USD, 0.016)))))
        return result

    }

    fun getAccountBalanceByCurrency(currency: Currency) {

    }

    fun getAccountBalance() : LiveData<Resource<List<Balance>>> {
        val result = MutableLiveData<Resource<List<Balance>>>()
        result.value = Resource.success(listOf(Balance(0, 1164.00, Currency.RUB, Date()),
                Balance(1, 174.00, Currency.USD, Date())))
        return result

    }
}