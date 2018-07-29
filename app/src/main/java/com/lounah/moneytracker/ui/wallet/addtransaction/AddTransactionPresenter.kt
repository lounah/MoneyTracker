package com.lounah.moneytracker.ui.wallet.addtransaction

import com.lounah.moneytracker.data.entities.Transaction
import com.lounah.moneytracker.domain.interactors.WalletInteractor
import javax.inject.Inject

class AddTransactionPresenter @Inject constructor(private val interactor: WalletInteractor) {

    private var view: AddTransactionView? = null

    fun onAttach(_view: AddTransactionView) {
        view = _view
    }

    fun createTransaction(transaction: Transaction) {
        interactor.createTransaction(transaction)
        view?.onTransactionCreated()
    }

    fun onDetach() {
        view = null
    }

}