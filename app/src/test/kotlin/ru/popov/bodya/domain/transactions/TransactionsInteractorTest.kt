package ru.popov.bodya.domain.transactions

import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import ru.popov.bodya.data.repositories.TransactionsRepository
import ru.popov.bodya.domain.currency.model.Currency
import ru.popov.bodya.domain.transactions.models.ExpenseCategory
import ru.popov.bodya.domain.transactions.models.IncomeCategory
import ru.popov.bodya.domain.transactions.models.Transaction
import ru.popov.bodya.domain.transactions.models.TransactionsCategory
import ru.popov.bodya.domain.transactions.models.WalletType.CREDIT_CARD
import java.util.*

/**
 * @author popovbodya
 */
class TransactionsInteractorTest {

    private lateinit var transactionsInteractor: TransactionsInteractor
    private lateinit var transactionsRepository: TransactionsRepository

    @Before
    fun setUp() {
        transactionsRepository = mock(TransactionsRepository::class.java)
        transactionsInteractor = TransactionsInteractor(transactionsRepository)
    }

    @Test
    fun testGetAllTransactionsByWallet() {
        val expected = createStubTransactionList()
        `when`(transactionsRepository.getAllTransactionsByWallet(CREDIT_CARD)).thenReturn(Single.just(expected))
        transactionsInteractor.getAllTransactionsByWallet(CREDIT_CARD)
                .test()
                .assertValue(expected)
        verify(transactionsRepository).getAllTransactionsByWallet(CREDIT_CARD)
        verifyNoMoreInteractions(transactionsRepository)
    }

    @Test
    fun testGetIncomeTransactionsByWallet() {
        val expected = createStubIncomeTransactionList()
        `when`(transactionsRepository.getIncomeTransactionsByWallet(CREDIT_CARD)).thenReturn(Single.just(expected))
        transactionsInteractor.getIncomeTransactionsByWallet(CREDIT_CARD)
                .test()
                .assertValue(expected)
        verify(transactionsRepository).getIncomeTransactionsByWallet(CREDIT_CARD)
        verifyNoMoreInteractions(transactionsRepository)
    }

    @Test
    fun testGetExpenseTransactionsByWallet() {
        val expected = createStubExpenseTransactionList()
        `when`(transactionsRepository.getExpenseTransactionsByWallet(CREDIT_CARD)).thenReturn(Single.just(expected))
        transactionsInteractor.getExpenseTransactionsByWallet(CREDIT_CARD)
                .test()
                .assertValue(expected)
        verify(transactionsRepository).getExpenseTransactionsByWallet(CREDIT_CARD)
        verifyNoMoreInteractions(transactionsRepository)
    }

    @Test
    fun testAddIncomeTransaction() {
        val amount = 50.0
        val time = Calendar.getInstance().time
        val desc = "gift"
        val category = TransactionsCategory.Income(IncomeCategory.GIFT)
        val expectedParam = Transaction(CREDIT_CARD, Currency.EUR, category, amount, time, desc)
        `when`(transactionsRepository.addIncomeTransaction(expectedParam)).thenReturn(Completable.complete())
        transactionsInteractor.addIncomeTransaction(CREDIT_CARD, category, Currency.EUR, amount, time, desc)
                .test()
                .assertComplete()
    }

    @Test
    fun testAddExpenseTransaction() {
        val amount = 50.0
        val time = Calendar.getInstance().time
        val desc = "food"
        val category = TransactionsCategory.Expense(ExpenseCategory.FOOD)
        val expectedParam = Transaction(CREDIT_CARD, Currency.USD, category, amount, time, desc)
        `when`(transactionsRepository.addExpenseTransaction(expectedParam)).thenReturn(Completable.complete())
        transactionsInteractor.addExpenseTransaction(CREDIT_CARD, category, Currency.USD, amount, time, desc)
    }

    private fun createStubTransactionList(): List<Transaction> {
        val list: MutableList<Transaction> = mutableListOf()
        list.addAll(createStubIncomeTransactionList())
        list.addAll(createStubExpenseTransactionList())
        return list
    }

    private fun createStubExpenseTransactionList(): List<Transaction> {
        val time = Calendar.getInstance().time
        return listOf(
                Transaction(CREDIT_CARD, Currency.USD, TransactionsCategory.Expense(ExpenseCategory.FAMILY), 50.0, time, "1"),
                Transaction(CREDIT_CARD, Currency.USD, TransactionsCategory.Expense(ExpenseCategory.FAMILY), 25.0, time, "2")
        )
    }

    private fun createStubIncomeTransactionList(): List<Transaction> {
        val time = Calendar.getInstance().time
        return listOf(
                Transaction(CREDIT_CARD, Currency.EUR, TransactionsCategory.Income(IncomeCategory.GIFT), 30.0, time, "3"),
                Transaction(CREDIT_CARD, Currency.USD, TransactionsCategory.Income(IncomeCategory.SALARY), 50.0, time, "4"),
                Transaction(CREDIT_CARD, Currency.RUB, TransactionsCategory.Income(IncomeCategory.OTHER_INCOME), 50.0, time, "5"),
                Transaction(CREDIT_CARD, Currency.RUB, TransactionsCategory.Income(IncomeCategory.OTHER_INCOME), 50.0, time, "6")
        )
    }

}