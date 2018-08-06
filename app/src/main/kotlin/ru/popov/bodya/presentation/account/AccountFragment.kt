package ru.popov.bodya.presentation.account

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.lounah.moneytracker.data.entities.Status
import com.lounah.wallettracker.R
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_wallet.*
import ru.popov.bodya.core.mvwhatever.AppFragment
import ru.popov.bodya.domain.transactions.models.Transaction
import ru.popov.bodya.presentation.common.Screens.ADD_NEW_TRANSACTION_SCREEN
import ru.popov.bodya.presentation.transactions.TransactionsRVAdapter
import ru.terrakok.cicerone.Router
import java.text.DecimalFormat
import javax.inject.Inject

class AccountFragment : AppFragment() {

    @Inject
    lateinit var router: Router
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    @Inject
    lateinit var viewModel: AccountViewModel

    private val formatter = DecimalFormat("#0.00")
    private lateinit var transactionsAdapter: TransactionsRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val parentView = inflater.inflate(R.layout.fragment_wallet, container, false)
        setHasOptionsMenu(true)
        return parentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    override fun onStart() {
        super.onStart()
        subscribeToViewModel()
        viewModel.fetchInitialData()
    }

    override fun onResume() {
        super.onResume()
        fab_add.collapse()
    }

    override fun onStop() {
        super.onStop()
        removeObservers()
    }

    private fun initUI() {
        initFab()
        initCurrenciesRadioGroup()
        initTransactionsList()
        initCurrencies()
    }

    private fun initFab() {
        fab_new_income.setOnClickListener { addNewIncome() }
        fab_new_expense.setOnClickListener { addNewExpense() }
    }

    private fun addNewIncome() {
        router.navigateTo(ADD_NEW_TRANSACTION_SCREEN, true)
    }

    private fun addNewExpense() {
        router.navigateTo(ADD_NEW_TRANSACTION_SCREEN, false)
    }

    private fun initCurrenciesRadioGroup() {
        rg_currencies.check(R.id.btn_rub)
        rg_currencies.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {

            }
        }
    }

    private fun initTransactionsList() {
        transactionsAdapter = TransactionsRVAdapter()
        rv_actions.adapter = transactionsAdapter
        rv_actions.layoutManager = LinearLayoutManager(context)
        rv_actions.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (dy > 20 && fab_add.visibility == View.VISIBLE) {
                    fab_add.collapse()
                    fab_add.visibility = View.GONE
                    return
                }
                if (dy < -20 && fab_add.visibility != View.VISIBLE) {
                    fab_add.visibility = View.VISIBLE
                    return
                }
            }
        })
    }

    private fun initCurrencies() {}

    private fun subscribeToViewModel() {
        viewModel.currentBalanceLiveData.observe(this, Observer { resource ->
            when (resource?.status) {
                Status.SUCCESS -> resource.data?.let { processSuccessBalanceResponse(resource.data) }
                Status.LOADING -> processLoadingState()
                Status.ERROR -> processErrorState()
            }
        })

        viewModel.transactionsLiveData.observe(this, Observer { resource ->
            when (resource?.status) {
                Status.SUCCESS -> resource.data?.let { processSuccessTransactionsResponse(it) }
                Status.LOADING -> processLoadingState()
                Status.ERROR -> processErrorState()
            }
        })

        viewModel.incomeLiveData.observe(this, Observer { amount ->
            amount?.let { tv_incomes.amount = amount.toFloat() }
        })

        viewModel.expenseLiveData.observe(this, Observer { amount ->
            amount?.let { tv_expenses.amount = amount.toFloat() }
        })
        viewModel.usdExchangeRateLiveData.observe(this, Observer { response ->
            when (response?.status) {
                Status.ERROR -> processErrorState()
                Status.SUCCESS -> processSuccessFirstExchangeRate(response.data)
                Status.LOADING -> processLoadingState()
            }
        })

        viewModel.eurExchangeRateLiveData.observe(this, Observer { response ->
            when (response?.status) {
                Status.ERROR -> processErrorState()
                Status.SUCCESS -> processSuccessSecondExchangeRate(response.data)
                Status.LOADING -> processLoadingState()
            }
        })
    }

    private fun removeObservers() {
        viewModel.usdExchangeRateLiveData.removeObservers(this)
        viewModel.eurExchangeRateLiveData.removeObservers(this)
        viewModel.currentBalanceLiveData.removeObservers(this)
        viewModel.transactionsLiveData.removeObservers(this)
        viewModel.incomeLiveData.removeObservers(this)
        viewModel.expenseLiveData.removeObservers(this)
    }

    private fun processErrorState() {
        hideProgressBar()
        showToast(R.string.error_loading_data)
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    private fun processSuccessFirstExchangeRate(rate: Double?) {
        currency_top_exchange_rate.text = formatter.format(rate)
        hideProgressBar()
    }

    private fun processSuccessSecondExchangeRate(rate: Double?) {
        currency_bottom_exchange_rate.text = formatter.format(rate)
        hideProgressBar()
    }

    private fun processLoadingState() {
        showProgressBar()
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun processSuccessBalanceResponse(amount: Double) {
        tv_balance.setSymbol(getString(R.string.rub_sign))
        tv_balance.amount = amount.toFloat()
    }

    private fun processSuccessTransactionsResponse(data: List<Transaction>) {
        transactionsAdapter.updateDataSet(data)
    }

    private fun showToast(msgId: Int) {
        Toast.makeText(context, msgId, Toast.LENGTH_SHORT).show()
    }
}