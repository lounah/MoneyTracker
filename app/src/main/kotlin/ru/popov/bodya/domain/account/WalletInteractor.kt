package ru.popov.bodya.domain.account

import com.lounah.moneytracker.data.entities.Currency
import com.lounah.moneytracker.data.entities.Transaction
import com.lounah.moneytracker.data.entities.WalletType
import com.lounah.moneytracker.data.repositories.CurrencyRepository
import com.lounah.moneytracker.data.repositories.TransactionsRepository
import com.lounah.moneytracker.data.repositories.WalletRepository
import javax.inject.Inject

class WalletInteractor @Inject constructor(private val currencyRepository: CurrencyRepository,
                                           private val walletRepository: WalletRepository,
                                           private val transactionsRepository: TransactionsRepository) {

    fun createTransaction(transaction: Transaction) {
        transactionsRepository.addTransaction(transaction)
        walletRepository.updateWalletByTransaction(transaction)
    }

    fun getAllTransactionsByWallet(wallet: WalletType)
        = transactionsRepository.getAllTransactionsByWallet(wallet)

    fun getIncomeTransactionsByWallet(wallet: WalletType)
        = transactionsRepository.getIncomeTransactionsByWallet(wallet)

    fun getExpenseTransactionsByWallet(wallet: WalletType)
        = transactionsRepository.getExpenseTransactionsByWallet(wallet)

    fun getTransactions()
            = transactionsRepository.getAllTransactions()

    fun getAllIncomeTransactions()
        = transactionsRepository.getAllIncomeTransactions()

    fun getAllExpenseTransactions()
        = transactionsRepository.getAllExpenseTransactions()

    fun getAccountBalanceByWallet(wallet: WalletType)
        = walletRepository.getWalletByType(wallet)

    fun getWallets() = walletRepository.getWallets()

    fun getExchangeRate(from: String, to: String) =
            currencyRepository.getExchangeRate(from, to)

    fun getExchangeRates(ratesMap: Map<String, String>) {

    }

    fun convertWalletToCurrency(wallet: WalletType, toCurrency: Currency) {

    }
}