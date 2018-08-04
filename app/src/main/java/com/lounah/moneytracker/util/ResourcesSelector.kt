package com.lounah.moneytracker.util

import ru.popov.bodya.domain.transactions.models.ExpenseCategory
import com.lounah.wallettracker.R
import ru.popov.bodya.domain.transactions.models.IncomeCategory
import ru.popov.bodya.domain.transactions.models.TransactionsCategory

class ResourcesSelector private constructor() {

    companion object {

        fun fromTransactionCategoryToString(type: TransactionsCategory) = when (type) {
            is TransactionsCategory.Expense -> getExpenseTransactionName(type.expenseCategory)
            is TransactionsCategory.Income -> getIncomeTransactionName(type.incomeCategory)
        }

        fun fromTransactionCategoryToDrawable(type: TransactionsCategory) = when (type) {
            is TransactionsCategory.Expense -> getExpenseTransactionDrawable(type.expenseCategory)
            is TransactionsCategory.Income -> getIncomeTransactionDrawable(type.incomeCategory)
        }

        private fun getIncomeTransactionDrawable(incomeCategory: IncomeCategory): Int = when (incomeCategory) {
            IncomeCategory.SALARY -> R.drawable.ic_salary
            IncomeCategory.TRANSFER -> R.drawable.ic_family
            IncomeCategory.GIFT -> R.drawable.ic_rest
            IncomeCategory.OTHER_INCOME -> R.drawable.ic_other
        }

        private fun getExpenseTransactionDrawable(expenseCategory: ExpenseCategory) = when (expenseCategory) {
            ExpenseCategory.AUTO -> R.drawable.ic_auto
            ExpenseCategory.TREATMENT -> R.drawable.ic_treatment
            ExpenseCategory.REST -> R.drawable.ic_rest
            ExpenseCategory.FAMILY -> R.drawable.ic_family
            ExpenseCategory.CLOTHES -> R.drawable.ic_clothes
            ExpenseCategory.EDUCATION -> R.drawable.ic_education
            ExpenseCategory.COMMUNAL_PAYMENTS -> R.drawable.ic_communal_payments
            ExpenseCategory.HOME -> R.drawable.ic_home
            ExpenseCategory.FOOD -> R.drawable.ic_food
            ExpenseCategory.OTHER_EXPENSE -> R.drawable.ic_other
        }

        private fun getExpenseTransactionName(expenseCategory: ExpenseCategory): Int {
            return when (expenseCategory) {
                ExpenseCategory.AUTO -> R.string.auto
                ExpenseCategory.TREATMENT -> R.string.treatment
                ExpenseCategory.REST -> R.string.rest
                ExpenseCategory.FAMILY -> R.string.family
                ExpenseCategory.CLOTHES -> R.string.clothes
                ExpenseCategory.EDUCATION -> R.string.education
                ExpenseCategory.COMMUNAL_PAYMENTS -> R.string.communal_payments
                ExpenseCategory.HOME -> R.string.home
                ExpenseCategory.FOOD -> R.string.food
                ExpenseCategory.OTHER_EXPENSE -> R.string.other
            }
        }

        private fun getIncomeTransactionName(incomeCategory: IncomeCategory): Int {
            return when (incomeCategory) {
                IncomeCategory.GIFT -> R.string.gift
                IncomeCategory.OTHER_INCOME -> R.string.other
                IncomeCategory.SALARY -> R.string.salary
                IncomeCategory.TRANSFER -> R.string.transfer
            }
        }
    }
}