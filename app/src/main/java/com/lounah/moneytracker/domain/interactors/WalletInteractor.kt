package com.lounah.moneytracker.domain.interactors

import com.lounah.moneytracker.data.datasource.remote.CurrencyApi
import com.lounah.moneytracker.data.entities.*
import com.lounah.moneytracker.data.entities.Currency
import com.lounah.moneytracker.data.repositories.BalanceRepository
import com.lounah.moneytracker.data.repositories.TransactionsRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class WalletInteractor @Inject constructor(private val api: CurrencyApi,
                                           private val balanceRepo: BalanceRepository,
                                           private val transactionsRepository: TransactionsRepository) {

    fun createTransaction(transaction: Transaction) {
        transactionsRepository.addTransaction(transaction)
    }

    fun getTransactionsByWalletType(type: WalletType) {

    }

    fun getTransactionsByCurrency(currency: Currency) {

    }

    fun getTransactions() = transactionsRepository.getAllTransactions()

    fun getAccountBalanceByCurrency(currency: Currency) {
        balanceRepo.getBalanceByCurrency(currency)
    }

    fun getAccountBalanceByType(type: WalletType) {
        balanceRepo.getBalanceByType(type)
    }

    fun getAccountBalance() = balanceRepo.getBalance()

    fun getExchangeRate(from: String, to: String) =
            api.getExchangeRate("${from}_$to")

    fun getExchangeRates(ratesMap: Map<String, String>) {

    }
}