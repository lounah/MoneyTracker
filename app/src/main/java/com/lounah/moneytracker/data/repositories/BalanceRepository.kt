package com.lounah.moneytracker.data.repositories

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.lounah.moneytracker.data.datasource.local.BalanceDao
import com.lounah.moneytracker.data.entities.*
import com.lounah.moneytracker.data.entities.Currency
import java.util.*
import javax.inject.Inject

class BalanceRepository @Inject constructor(dao: BalanceDao) {

    fun getBalance() : LiveData<Resource<List<Balance>>> {
        val result = MutableLiveData<Resource<List<Balance>>>()
        result.value = Resource.success(listOf(
                Balance(0, 11641.47, WalletType.CASH, Currency.RUB, Date()),
                Balance(0, 116324.23, WalletType.CREDIT_CARD, Currency.RUB, Date()),
                Balance(1, 17324.75, WalletType.BANK_ACCOUNT, Currency.USD, Date())))
        return result
    }

    fun getBalanceByCurrency(currency: Currency) {

    }

    fun getBalanceByType(type: WalletType) {

    }

    fun updateBalanceByTransaction(transaction: Transaction) {

    }

}