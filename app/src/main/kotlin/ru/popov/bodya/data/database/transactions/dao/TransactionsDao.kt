package ru.popov.bodya.data.database.transactions.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import ru.popov.bodya.data.database.BaseDao
import ru.popov.bodya.data.database.transactions.entities.TransactionEntity
import ru.popov.bodya.domain.transactions.models.WalletType

/**
 *  @author popovbodya
 */
@Dao
interface TransactionsDao : BaseDao<TransactionEntity> {

    @Query("SELECT * FROM transactions WHERE wallet=:wallet")
    fun getAllTransactionsByWallet(wallet: WalletType): List<TransactionEntity>

    @Query("SELECT * FROM transactions WHERE wallet=:wallet AND amount>0")
    fun getIncomeTransactionsByWallet(wallet: WalletType): List<TransactionEntity>

    @Query("SELECT * FROM transactions WHERE wallet=:wallet AND amount<0")
    fun getExpenseTransactionsByWallet(wallet: WalletType): List<TransactionEntity>

}