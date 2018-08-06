package ru.popov.bodya.domain.transactions.models

/**
 *  @author popovbodya
 */
sealed class TransactionsCategory {
    class Expense(val expenseCategory: ExpenseCategory) : TransactionsCategory()
    class Income(val incomeCategory: IncomeCategory) : TransactionsCategory()
}
