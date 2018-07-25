package com.lounah.moneytracker.data.repositories

import com.lounah.moneytracker.data.datasource.local.TransactionsDao
import com.lounah.moneytracker.data.entities.Transaction
import javax.inject.Inject

class TransactionsRepository @Inject constructor(dao: TransactionsDao) {

    fun addTransaction(transaction: Transaction) {

    }

    fun addTransactions(transactions: List<Transaction>) {

    }

    fun getAllTransactions() {

    }

}