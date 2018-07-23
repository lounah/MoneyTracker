package com.lounah.moneytracker.data

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import com.lounah.moneytracker.data.datasource.local.AppDatabase
import com.lounah.moneytracker.data.datasource.local.BalanceDao
import com.lounah.moneytracker.data.datasource.local.TransactionsDao
import com.lounah.moneytracker.data.datasource.remote.CurrencyApi
import com.lounah.moneytracker.data.entities.Currency
import com.lounah.moneytracker.data.entities.Transaction
import com.lounah.moneytracker.data.entities.TransactionType
import com.lounah.moneytracker.data.repositories.WalletRepository
import io.reactivex.Single
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.util.*
import org.junit.rules.TestRule
import org.junit.Rule



@RunWith(JUnit4::class)
class WalletRepositoryTest {

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var repository: WalletRepository
    private val balanceDao = mock(BalanceDao::class.java)
    private val transactionsDao = mock(TransactionsDao::class.java)
    private val api = mock(CurrencyApi::class.java)

    @Before
    fun init() {
        val db = Mockito.mock(AppDatabase::class.java)
        `when`(db.balanceDao).thenReturn(balanceDao)
        `when`(db.transactionsDao).thenReturn(transactionsDao)
        `when`(db.runInTransaction(ArgumentMatchers.any())).thenCallRealMethod()
        repository = WalletRepository(api, balanceDao, transactionsDao)
    }

    @Test
    fun testTransactionsSum() {
        val transactions = MutableLiveData<List<Transaction>>()
        transactions.value = listOf(Transaction(0, Date(), TransactionType.INCOME, 100.00,  Currency.RUR, listOf(mapOf(Pair(Currency.USD, 0.016), Pair(Currency.RUR, 1.0)))),
                Transaction(5, Date(), TransactionType.INCOME, 200.00,  Currency.RUR, listOf(mapOf(Pair(Currency.USD, 0.016), Pair(Currency.RUR, 1.0)))))
        `when`(transactionsDao.getTransactions()).thenReturn(transactions)

        assertTrue(repository.getTransactionsSum(Currency.RUR) == 300.0)
    }

}