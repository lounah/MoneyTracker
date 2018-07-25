package com.lounah.moneytracker.data.repositories

import com.lounah.moneytracker.data.datasource.local.BalanceDao
import com.lounah.moneytracker.data.entities.Currency
import com.lounah.moneytracker.data.entities.Transaction
import javax.inject.Inject

class BalanceRepository @Inject constructor(dao: BalanceDao) {

    fun getBalanceByCurrency(currency: Currency) {

    }

    fun updateBalanceByTransaction(transaction: Transaction) {

    }

}