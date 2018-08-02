package com.lounah.moneytracker.data.domain

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.lounah.moneytracker.data.datasource.local.AppDatabase
import com.lounah.moneytracker.data.datasource.local.TransactionsDao
import com.lounah.moneytracker.data.datasource.local.WalletDao
import com.lounah.moneytracker.data.datasource.remote.CurrencyApi
import com.lounah.moneytracker.data.repositories.CurrencyRepository
import com.lounah.moneytracker.data.repositories.TransactionsRepository
import com.lounah.moneytracker.data.repositories.WalletRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.*
import ru.popov.bodya.domain.account.WalletInteractor

@RunWith(JUnit4::class)
class WalletInteractorTest {

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()


    private lateinit var currencyRepository: CurrencyRepository
    private lateinit var transactionsRepository: TransactionsRepository
    private lateinit var walletRepository: WalletRepository
    private lateinit var interactor: WalletInteractor

    private val walletDao = mock(WalletDao::class.java)
    private val transactionsDao = mock(TransactionsDao::class.java)
    private val api = mock(CurrencyApi::class.java)

    @Before
    fun init() {
        val db = Mockito.mock(AppDatabase::class.java)
        `when`(db.walletDao).thenReturn(walletDao)
        `when`(db.transactionsDao).thenReturn(transactionsDao)
        `when`(db.runInTransaction(ArgumentMatchers.any())).thenCallRealMethod()
        currencyRepository = CurrencyRepository(api)
        transactionsRepository = TransactionsRepository(transactionsDao)
        walletRepository = WalletRepository(walletDao)
        interactor = WalletInteractor(currencyRepository, walletRepository, transactionsRepository)
    }

    @Test
    fun loadTransactions() {
        transactionsRepository.getAllExpenseTransactions()
        verify(transactionsDao).getAllExpenseTransactions()

        transactionsRepository.getAllIncomeTransactions()
        verify(transactionsDao).getAllExpenseTransactions()
    }

}