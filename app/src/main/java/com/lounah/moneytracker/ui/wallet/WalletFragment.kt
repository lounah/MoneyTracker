package com.lounah.moneytracker.ui.wallet

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.lounah.moneytracker.data.entities.Balance
import com.lounah.moneytracker.data.entities.Status
import com.lounah.moneytracker.data.entities.Transaction
import com.lounah.moneytracker.ui.MainActivity
import com.lounah.moneytracker.ui.common.BaseFragment
import com.lounah.moneytracker.util.ZoomOutPageTransformer
import com.lounah.wallettracker.R
import kotlinx.android.synthetic.main.fragment_wallet.*
import java.text.DecimalFormat
import javax.inject.Inject


class WalletFragment : BaseFragment(),
        AddTransactionDialogFragment.AddTransactionCallback {

    override val TAG: String
        get() = "WALLET_FRAGMENT"

    private val formatter = DecimalFormat("#0.00")

    private lateinit var pagerAdapter: BalanceVPAdapter
    private lateinit var transactionsAdapter: TransactionsRVAdapter
    private lateinit var addTransactionDialogFragment: AddTransactionDialogFragment

    @Inject
    lateinit var viewModel: WalletViewModel

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_wallet, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(WalletViewModel::class.java)
        viewModel.fetchFirstFieldExchangeRate("USD", "RUB")
        viewModel.fetchSecondFieldExchangeRate("EUR", "RUB")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbarTitle(R.string.wallet)
        initUI()
    }

    private fun initUI() {
        initCurrenciesRadioGroup()
        initWalletViewPager()
        initTransactionsList()
        initCurrencies()
    }

    private fun initCurrenciesRadioGroup() {
        rg_currencies.check(R.id.btn_rub)
    }

    private fun initWalletViewPager() {
        vp_amount.setPageTransformer(true, ZoomOutPageTransformer())
        pagerAdapter = BalanceVPAdapter()
        vp_amount.adapter = pagerAdapter
        tl_dots.setupWithViewPager(vp_amount, true)
    }

    private fun initTransactionsList() {
        transactionsAdapter = TransactionsRVAdapter()
        rv_actions.adapter = transactionsAdapter
        val linearLayoutManager = LinearLayoutManager(context)
        rv_actions.layoutManager = linearLayoutManager

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
            processSuccessTransactionsResponse(response!!)
        })

        viewModel.addingTransactionResult.observe(this, Observer {

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
        viewModel.addingTransactionResult.removeObservers(this)
    }

    override fun setUpToolbarTitle(resId: Int) {
        (activity as MainActivity).onUpdateToolbarTitle(resId)
    }

    override fun onTransactionCreated(transaction: Transaction) {
        viewModel.addTransaction(transaction)
        addTransactionDialogFragment.dismiss()
    }

    private fun addTransaction() {
        addTransactionDialogFragment = AddTransactionDialogFragment()
        addTransactionDialogFragment.show(childFragmentManager, TAG)
    }

    private fun processErrorState() {
        progressBar.visibility = View.GONE
        pb_placeholder.visibility = View.GONE
        showToast(R.string.error_loading_data)
    }

    private fun processSuccessFirstExchangeRate(rate: Double?) {
        currency_top_exchange_rate.text = formatter.format(rate)
        progressBar.visibility = View.GONE
    }

    private fun processSuccessSecondExchangeRate(rate: Double?) {
        currency_bottom_exchange_rate.text = formatter.format(rate)
        progressBar.visibility = View.GONE
        pb_placeholder.visibility = View.GONE
    }


    private fun processLoadingState() {
        pb_placeholder.visibility = View.VISIBLE
        progressBar.visibility = View.VISIBLE
    }

    private fun processSuccessBalanceResponse(data: List<Balance>) {
        pagerAdapter.updateDataSet(data)
    }

    private fun processSuccessTransactionsResponse(data: List<Transaction>) {
        transactionsAdapter.updateDataSet(data)
    }

    private fun showToast(msgId: Int) {
        Toast.makeText(context, msgId, Toast.LENGTH_SHORT).show()
    }
}