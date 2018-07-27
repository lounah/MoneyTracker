package com.lounah.moneytracker.ui.wallet

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.lounah.moneytracker.data.entities.Balance
import com.lounah.moneytracker.data.entities.Resource
import com.lounah.moneytracker.data.entities.Status
import com.lounah.moneytracker.data.entities.Transaction
import com.lounah.moneytracker.ui.MainActivity
import com.lounah.moneytracker.ui.common.BaseFragment
import com.lounah.moneytracker.util.ZoomOutPageTransformer
import com.lounah.wallettracker.R
import kotlinx.android.synthetic.main.fragment_wallet.*
import javax.inject.Inject


class WalletFragment : BaseFragment(),
        AddTransactionDialogFragment.AddTransactionCallback {

    override val TAG: String
        get() = "WALLET_FRAGMENT"

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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbarTitle(R.string.wallet)
        initUI()
    }

    override fun onStart() {
        super.onStart()
        subscribeToViewModel()
        viewModel.refreshCurrentBalance()
        viewModel.fetchExchangeRates("USD", "RUB")
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

        viewModel.exchangeRates.observe(this, Observer { response ->
            when (response!!.status) {
                Status.ERROR -> processErrorState()
                Status.SUCCESS -> processSuccessExchangeRate(response.data!!)
                Status.LOADING -> processLoadingState()
            }
        })
    }

    override fun onStop() {
        super.onStop()
        removeObservers()
    }

    private fun removeObservers() {
        viewModel.exchangeRates.removeObservers(this)
        viewModel.currentBalance.removeObservers(this)
        viewModel.transactions.removeObservers(this)
        viewModel.addingTransactionResult.removeObservers(this)
    }

    private fun initUI() {
        initWalletViewPager()
        initTransactionsList()
        fab_add.setOnClickListener { addTransaction() }
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
                if (dy > 0 && fab_add.visibility == View.VISIBLE) fab_add.hide()
                else if (dy < 0 && fab_add.visibility != View.VISIBLE) fab_add.show()
            }
        })
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
    }

    private fun processSuccessExchangeRate(rate: Double) {

    }

    private fun processLoadingState() {

    }

    private fun processSuccessBalanceResponse(data: List<Balance>) {
        pagerAdapter.updateDataSet(data)
    }

    private fun processSuccessTransactionsResponse(data: List<Transaction>) {
        transactionsAdapter.updateDataSet(data)
    }

}