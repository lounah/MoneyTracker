package com.lounah.moneytracker.data.datasource.local

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.lounah.moneytracker.data.entities.Transaction
import com.lounah.moneytracker.data.entities.WalletType

@Dao
interface TransactionsDao : BaseDao<Transaction> {
    @Query("SELECT * FROM transactions")
    fun getAllTransactions(): LiveData<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE amount>0")
    fun getAllIncomeTransactions(): LiveData<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE amount<0")
    fun getAllExpenseTransactions(): LiveData<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE wallet=:wallet")
    fun getAllTransactionsByWallet(wallet: WalletType): LiveData<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE wallet=:wallet AND amount>0")
    fun getIncomeTransactionsByWallet(wallet: WalletType): LiveData<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE wallet=:wallet AND amount<0")
    fun getExpenseTransactionsByWallet(wallet: WalletType): LiveData<List<Transaction>>
}
