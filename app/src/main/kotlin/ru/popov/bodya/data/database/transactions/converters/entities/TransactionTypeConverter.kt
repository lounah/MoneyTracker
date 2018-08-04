package ru.popov.bodya.data.database.transactions.converters.entities

import android.arch.persistence.room.TypeConverter
import ru.popov.bodya.domain.transactions.models.ExpenseCategory.*
import ru.popov.bodya.domain.transactions.models.IncomeCategory.*
import ru.popov.bodya.domain.transactions.models.TransactionsCategory
import ru.popov.bodya.domain.transactions.models.TransactionsCategory.Expense
import ru.popov.bodya.domain.transactions.models.TransactionsCategory.Income

class TransactionTypeConverter {

    @TypeConverter
    fun fromTransactionTypeToInt(transactionsCategory: TransactionsCategory): String =
            when (transactionsCategory) {
                is Expense -> transactionsCategory.expenseCategory.name
                is TransactionsCategory.Income -> transactionsCategory.incomeCategory.name
            }

    @TypeConverter
    fun fromIntToTransactionType(type: String): TransactionsCategory {
        return when (type) {
            FOOD.name -> Expense(FOOD)
            HOME.name -> Expense(HOME)
            COMMUNAL_PAYMENTS.name -> Expense(COMMUNAL_PAYMENTS)
            EDUCATION.name -> Expense(EDUCATION)
            CLOTHES.name -> Expense(CLOTHES)
            FAMILY.name -> Expense(FAMILY)
            REST.name -> Expense(REST)
            TREATMENT.name -> Expense(TREATMENT)
            OTHER_EXPENSE.name -> Expense(OTHER_EXPENSE)
            SALARY.name -> Income(SALARY)
            TRANSFER.name -> Income(TRANSFER)
            GIFT.name -> Income(GIFT)
            OTHER_INCOME.name -> Income(OTHER_INCOME)
            else -> Expense(OTHER_EXPENSE)
        }
    }
}