package ru.popov.bodya.presentation.addtransaction

import android.arch.lifecycle.MutableLiveData
import com.lounah.moneytracker.data.entities.Resource
import ru.popov.bodya.core.mvwhatever.AppViewModel
import ru.popov.bodya.core.rx.RxSchedulersTransformer
import ru.popov.bodya.domain.currency.model.Currency
import ru.popov.bodya.domain.transactions.TransactionsInteractor
import ru.popov.bodya.domain.transactions.models.ExpenseCategory
import ru.popov.bodya.domain.transactions.models.IncomeCategory
import ru.popov.bodya.domain.transactions.models.TransactionsCategory
import ru.popov.bodya.domain.transactions.models.TransactionsCategory.Expense
import ru.popov.bodya.domain.transactions.models.TransactionsCategory.Income
import ru.popov.bodya.domain.transactions.models.WalletType
import ru.terrakok.cicerone.Router
import java.util.*
import javax.inject.Inject

/**
 *  @author popovbodya
 */
class AddTransactionViewModel @Inject constructor(private val transactionsInteractor: TransactionsInteractor,
                                                  private val rxSchedulersTransformer: RxSchedulersTransformer,
                                                  private val router: Router) : AppViewModel() {

    val transactionCategoriesLiveData = MutableLiveData<List<TransactionsCategory>>()
    val transactionAddStatus = MutableLiveData<Resource<Boolean>>()

    fun fetchTransactionCategories(isIncome: Boolean) {
        transactionCategoriesLiveData.value = when (isIncome) {
            true -> getAllIncomeTransaction()
            false -> getAllExpenseTransaction()
        }
    }

    fun onAddTransactionButtonClick(selectedWallet: WalletType, selectedCategory: TransactionsCategory, selectedCurrency: Currency, amount: Double, comment: String) {
        transactionAddStatus.postValue(Resource.loading(false))
        when (selectedCategory) {
            is Income -> {
                transactionsInteractor.addIncomeTransaction(selectedWallet, selectedCategory, selectedCurrency, amount, Calendar.getInstance().time, comment)
                        .compose(rxSchedulersTransformer.ioToMainTransformerCompletable())
                        .subscribe(
                                { transactionAddStatus.postValue(Resource.success(true)) },
                                { transactionAddStatus.postValue(Resource.error("", false)) }
                        )
            }
            is Expense -> {
                transactionsInteractor.addExpenseTransaction(selectedWallet, selectedCategory, selectedCurrency, amount, Calendar.getInstance().time, comment)
                        .compose(rxSchedulersTransformer.ioToMainTransformerCompletable())
                        .subscribe(
                                { transactionAddStatus.postValue(Resource.success(true)) },
                                { transactionAddStatus.postValue(Resource.error("", false)) }
                        )
            }
        }
    }

    private fun getAllIncomeTransaction(): ArrayList<Income> {
        val incomeList = arrayListOf<Income>()
        IncomeCategory.values().mapTo(incomeList) { Income(it) }
        return incomeList
    }

    private fun getAllExpenseTransaction(): ArrayList<Expense> {
        val expenseList = arrayListOf<Expense>()
        ExpenseCategory.values().mapTo(expenseList) { Expense(it) }
        return expenseList
    }
}