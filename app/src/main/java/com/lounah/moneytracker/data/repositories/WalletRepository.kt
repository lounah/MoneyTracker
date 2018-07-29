package com.lounah.moneytracker.data.repositories

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.lounah.moneytracker.data.datasource.local.BalanceDao
import com.lounah.moneytracker.data.datasource.local.TransactionsDao
import com.lounah.moneytracker.data.datasource.remote.CurrencyApi
import com.lounah.moneytracker.data.entities.*
import com.lounah.moneytracker.data.entities.Currency
import io.reactivex.Completable
import java.util.*
import javax.inject.Inject


open class WalletRepository @Inject constructor(private val api: CurrencyApi,
                                                private val balanceDao: BalanceDao,
                                                private val transactionsDao: TransactionsDao) {

    fun getBalance(): LiveData<Resource<List<Balance>>> {
        val result = MutableLiveData<Resource<List<Balance>>>()
        result.value = Resource.success(listOf(Balance(0, 1164.00, Currency.RUR, Date()),
                Balance(1, 174.00, Currency.USD, Date())))
        return result
    }

    fun getTransactions(): LiveData<List<Transaction>> {
        val result = MutableLiveData<List<Transaction>>()
        result.value = listOf(Transaction(0, Date(), TransactionType.INCOME, 164.00, Currency.RUR, listOf(mapOf(Pair(Currency.USD, 0.016)))),
                Transaction(1, Date(), TransactionType.EXPENSE, -1644.00, Currency.RUR, listOf(mapOf(Pair(Currency.USD, 0.016)))),
                Transaction(2, Date(), TransactionType.INCOME, 11564.00, Currency.RUR, listOf(mapOf(Pair(Currency.USD, 0.016)))),
                Transaction(3, Date(), TransactionType.EXPENSE, -104.00, Currency.RUR, listOf(mapOf(Pair(Currency.USD, 0.016)))),
                Transaction(4, Date(), TransactionType.EXPENSE, -1632.00, Currency.RUR, listOf(mapOf(Pair(Currency.USD, 0.016)))),
                Transaction(5, Date(), TransactionType.INCOME, 16423.00, Currency.RUR, listOf(mapOf(Pair(Currency.USD, 0.016)))))
        return result
    }

    fun addTransaction(transaction: Transaction) = Completable.fromAction { transactionsDao.insert(transaction) }

    // Лютая хрень,но пока что ничего лучше придумать не смог
    fun getTransactionsSum(forCurrency: Currency): Double {
        var sum = 0.0
        transactionsDao.getTransactions().value?.forEach { transaction ->
            var convertedAmount = 0.0
            transaction.exchangeRate.forEach { rate -> if (rate.containsKey(forCurrency)) convertedAmount = transaction.amount * rate[forCurrency]!! }
            sum += convertedAmount
        }
        return sum
    }

}