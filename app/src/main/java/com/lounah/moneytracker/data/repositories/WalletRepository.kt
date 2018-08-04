package com.lounah.moneytracker.data.repositories

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.lounah.moneytracker.data.datasource.local.WalletDao
import com.lounah.moneytracker.data.entities.*
import ru.popov.bodya.domain.currency.model.Currency
import ru.popov.bodya.data.database.transactions.entities.TransactionEntity
import ru.popov.bodya.domain.transactions.models.WalletType
import java.util.*
import javax.inject.Inject

class WalletRepository @Inject constructor(private val dao: WalletDao) {

    fun getWallets() : LiveData<Resource<List<Wallet>>> {
        val result = MutableLiveData<Resource<List<Wallet>>>()
        result.value = Resource.success(listOf(
                Wallet(0, 11641.47, WalletType.CASH, Currency.RUB, Date()),
                Wallet(1, 116324.23, WalletType.CREDIT_CARD, Currency.RUB, Date()),
                Wallet(2, 17324.75, WalletType.BANK_ACCOUNT, Currency.USD, Date())))
        return result
    }

    fun getWalletByType(type: WalletType)
        = dao.getWalletByType(type)

    fun addWallet(wallet: Wallet) {
        dao.insert(wallet)
    }

    fun deleteWallet(wallet: Wallet) {
        dao.delete(wallet)
    }

    fun updateWalletByTransaction(transactionEntity: TransactionEntity) {

    }

}