package com.lounah.moneytracker.data.datasource.local

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.lounah.moneytracker.data.entities.Transaction

@Dao
interface TransactionsDao : BaseDao<Transaction> {
    @Query("SELECT * FROM transactions")
    fun getTransactions(): LiveData<List<Transaction>>
}