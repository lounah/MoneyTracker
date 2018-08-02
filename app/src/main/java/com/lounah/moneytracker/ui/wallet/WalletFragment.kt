package com.lounah.moneytracker.ui.wallet

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.lounah.moneytracker.data.entities.Status
import com.lounah.moneytracker.data.entities.Transaction
import com.lounah.moneytracker.data.entities.Wallet
import com.lounah.moneytracker.ui.MainActivity
import com.lounah.moneytracker.ui.common.BaseFragment
import com.lounah.moneytracker.ui.wallet.addtransaction.AddTransactionFragment
import com.lounah.moneytracker.util.ZoomOutPageTransformer
import com.lounah.wallettracker.R
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_wallet.*
import java.text.DecimalFormat
import javax.inject.Inject


class WalletFragment : BaseFragment() {

    override val layoutRes: Int
        get() = R.layout.fragment_wallet

    override val TAG: String
        get() = "WALLET_FRAGMENT"

    private val formatter = DecimalFormat("#0.00")

    private lateinit var pagerAdapter: BalanceVPAdapter
    private lateinit var transactionsAdapter: TransactionsRVAdapter

    @Inject
    lateinit var viewModel: WalletViewModel

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(WalletViewModel::class.java)
        fetchFavouritesExchangeRates()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbarTitle(R.string.wallet)
        initUI()
    }

    override fun onResume() {
        super.onResume()
        fab_add.collapse()
    }

    // fav exchange rates будут браться из sharedPrefs, а туда заноситься из настроек
    // после ПР реализую это, и подгружать буду в Map, а не в отдельные поля
    // пока что это ужасно
    // смотри UI -- карточка с текущими курсами валют
    private fun fetchFavouritesExchangeRates() {
        viewModel.fetchFirstFieldExchangeRate("USD", "RUB")
        viewModel.fetchSecondFieldExchangeRate("EUR", "RUB")
    }

    private fun initUI() {
        initFab()
        initCurrenciesRadioGroup()
        initWalletViewPager()
        initTransactionsList()
        initCurrencies()
    }

    private fun initFab() {
        fab_new_income.setOnClickListener { addNewIncome() }
        fab_new_expense.setOnClickListener { addNewExpense() }
    }

    private fun addNewIncome() {
        mFragmentNavigator.showDialogFragment(AddTransactionFragment.newInstance(true))
    }

    private fun addNewExpense() {
        mFragmentNavigator.showDialogFragment(AddTransactionFragment.newInstance(false))
    }

    private fun initCurrenciesRadioGroup() {
        rg_currencies.check(R.id.btn_rub)
        rg_currencies.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId) {

            }
        }
    }

    private fun initWalletViewPager() {
        vp_amount.setPageTransformer(true, ZoomOutPageTransformer())
        pagerAdapter = BalanceVPAdapter()
        vp_amount.adapter = pagerAdapter
    }

    private fun initTransactionsList() {
        transactionsAdapter = TransactionsRVAdapter()
        rv_actions.adapter = transactionsAdapter
        val linearLayoutManager = LinearLayoutManager(context)
        rv_actions.layoutManager = linearLayoutManager

        tl_transaction_types.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position) {
                    0 -> viewModel.refreshCurrentBalance()
                    1 -> viewModel.getIncomeTransactions()
                    2 -> viewModel.getExpenseTransactions()
                }
            }

        })

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

    override fun onStart() {
        super.onStart()
        subscribeToViewModel()
        viewModel.refreshCurrentBalance()
    }

    private fun subscribeToViewModel() {
        viewModel.currentBalance.observe(this, Observer { response ->
            when (response?.status) {
                Status.SUCCESS -> processSuccessBalanceResponse(response.data!!)
                Status.LOADING -> processLoadingState()
                Status.ERROR -> processErrorState()
            }
        })

        viewModel.transactions.observe(this, Observer { response ->
            if (tl_transaction_types.selectedTabPosition == 0)
                processSuccessTransactionsResponse(response!!)
        })

        viewModel.incomeTransactions.observe(this, Observer { response ->
            if (tl_transaction_types.selectedTabPosition == 1)
                processSuccessTransactionsResponse(response!!)
        })

        viewModel.expenseTransactions.observe(this, Observer { response ->
            if (tl_transaction_types.selectedTabPosition == 2)
                processSuccessTransactionsResponse(response!!)
        })

        viewModel.firstFieldExchangeRate.observe(this, Observer { response ->
            when (response!!.status) {
                Status.ERROR -> processErrorState()
                Status.SUCCESS -> processSuccessFirstExchangeRate(response.data!!)
                Status.LOADING -> processLoadingState()
            }
        })

        viewModel.secondFieldExchangeRate.observe(this, Observer { response ->
            when (response!!.status) {
                Status.ERROR -> processErrorState()
                Status.SUCCESS -> processSuccessSecondExchangeRate(response.data!!)
                Status.LOADING -> processLoadingState()
            }
        })
    }

    override fun onStop() {
        super.onStop()
        removeObservers()
    }

    private fun removeObservers() {
        viewModel.firstFieldExchangeRate.removeObservers(this)
        viewModel.secondFieldExchangeRate.removeObservers(this)
        viewModel.currentBalance.removeObservers(this)
        viewModel.transactions.removeObservers(this)
    }

    override fun setUpToolbarTitle(resId: Int) {
        (activity as MainActivity).onUpdateToolbarTitle(resId)
    }

    private fun processErrorState() {
        hideProgressBar()
        showToast(R.string.error_loading_data)
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
        pb_placeholder.visibility = View.GONE
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
        pb_placeholder.visibility = View.VISIBLE
        progressBar.visibility = View.VISIBLE
    }

    private fun processSuccessBalanceResponse(data: List<Wallet>) {
        pagerAdapter.updateDataSet(data)
    }

    private fun processSuccessTransactionsResponse(data: List<Transaction>) {
        transactionsAdapter.updateDataSet(data)
    }

    private fun showToast(msgId: Int) {
        Toast.makeText(context, msgId, Toast.LENGTH_SHORT).show()
    }
}