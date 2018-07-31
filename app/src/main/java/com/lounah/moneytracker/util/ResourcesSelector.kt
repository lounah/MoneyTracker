package com.lounah.moneytracker.util

import android.view.View
import com.lounah.moneytracker.data.entities.TransactionType
import com.lounah.wallettracker.R

class ResourcesSelector private constructor() {

    companion object {
        fun fromTransactionTypeToString(type: TransactionType, view: View) = when (type) {
            TransactionType.AUTO -> view.resources.getString(R.string.auto)
            TransactionType.TREATMENT -> view.resources.getString(R.string.treatment)
            TransactionType.REST -> view.resources.getString(R.string.rest)
            TransactionType.FAMILY -> view.resources.getString(R.string.family)
            TransactionType.CLOTHES -> view.resources.getString(R.string.clothes)
            TransactionType.EDUCATION -> view.resources.getString(R.string.education)
            TransactionType.COMMUNAL_PAYMENTS -> view.resources.getString(R.string.communal_payments)
            TransactionType.HOME -> view.resources.getString(R.string.home)
            TransactionType.FOOD -> view.resources.getString(R.string.food)
            TransactionType.OTHER -> view.resources.getString(R.string.other)
            TransactionType.SALARY -> view.resources.getString(R.string.salary)
        }!!

        fun fromTransactionTypeToDrawable(type: TransactionType) = when (type) {
            TransactionType.AUTO -> R.drawable.ic_auto
            TransactionType.TREATMENT -> R.drawable.ic_treatment
            TransactionType.REST -> R.drawable.ic_rest
            TransactionType.FAMILY -> R.drawable.ic_family
            TransactionType.CLOTHES -> R.drawable.ic_clothes
            TransactionType.EDUCATION -> R.drawable.ic_education
            TransactionType.COMMUNAL_PAYMENTS -> R.drawable.ic_communal_payments
            TransactionType.HOME -> R.drawable.ic_home
            TransactionType.FOOD -> R.drawable.ic_food
            TransactionType.OTHER -> R.drawable.ic_other
            TransactionType.SALARY -> R.drawable.ic_salary
        }
    }
}