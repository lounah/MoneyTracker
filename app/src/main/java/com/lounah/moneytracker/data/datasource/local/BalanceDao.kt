package com.lounah.moneytracker.data.datasource.local

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.lounah.moneytracker.data.entities.Balance


@Dao
interface BalanceDao : BaseDao<Balance> {
    @Query("SELECT * FROM balance")
    fun getBalance(): LiveData<List<Balance>>
}