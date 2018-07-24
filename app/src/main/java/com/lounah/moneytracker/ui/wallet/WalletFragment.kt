package com.lounah.moneytracker.ui.wallet

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lounah.moneytracker.data.entities.Balance
import com.lounah.moneytracker.data.entities.Status
import com.lounah.moneytracker.data.entities.Transaction
import com.lounah.moneytracker.ui.MainActivity
import com.lounah.moneytracker.ui.common.BaseFragment
import com.lounah.moneytracker.util.ZoomOutPageTransformer
import com.lounah.wallettracker.R
import kotlinx.android.synthetic.main.fragment_wallet.*
import javax.inject.Inject


class WalletFragment : BaseFragment() {

    override val TAG: String
        get() = "WALLET_FRAGMENT"

    private lateinit var pagerAdapter: BalanceVPAdapter
    private lateinit var transactionsAdapter: TransactionsRVAdapter

    @Inject
    lateinit var viewModel: WalletViewModel

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = inflater.inflate(R.layout.fragment_wallet, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbarTitle(R.string.wallet)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(WalletViewModel::class.java)
        viewModel.refreshCurrentBalance()
        initUI()
        initViewModel()
    }

    private fun initUI() {
        vp_amount.setPageTransformer(true, ZoomOutPageTransformer())
        pagerAdapter = BalanceVPAdapter()
        vp_amount.adapter = pagerAdapter
        tl_dots.setupWithViewPager(vp_amount, true)

        transactionsAdapter = TransactionsRVAdapter()
        rv_actions.adapter = transactionsAdapter
        val linearLayoutManager = LinearLayoutManager(context)
        rv_actions.layoutManager = linearLayoutManager

        fab_add.setOnClickListener { addTransaction() }

        rv_actions.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (dy > 0 && fab_add.visibility == View.VISIBLE) {
                    fab_add.hide()
                } else if (dy < 0 && fab_add.visibility != View.VISIBLE) {
                    fab_add.show()
                }
            }
        })
    }

    private fun initViewModel() {
        viewModel.currentBalance.observe(this, Observer { response ->
            when (response?.status) {
                Status.SUCCESS -> processSuccessBalanceResponse(response.data!!)
            }
        })

        viewModel.transactions.observe(this, Observer { response ->
            processSuccessTransactionsResponse(response!!)
        })

        viewModel.addingTransactionResult.observe(this, Observer {

        })
    }

    private fun addTransaction() {
        AddTransactionDialogFragment().show(fragmentManager, "tag")
    }

    override fun setUpToolbarTitle(resId: Int) {
        (activity as MainActivity).onUpdateToolbarTitle(resId)
    }

    private fun processSuccessBalanceResponse(data: List<Balance>) {
        pagerAdapter.updateDataSet(data)
    }

    private fun processSuccessTransactionsResponse(data: List<Transaction>) {
        transactionsAdapter.updateDataSet(data)
    }
// RemoveAllObservers
}